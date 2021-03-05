package com.fishpound.accountservice.controller;

import com.alibaba.fastjson.JSON;
import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultOrder;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.AsyncService;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import com.fishpound.accountservice.service.tools.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Date;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AsyncService asyncService;

    /**
     * 通过申请单id获取申请单内容
     * @param id 申请单id
     * @return
     */
    @GetMapping()
    public JsonResult getOneOrder(@RequestParam(value = "id") String id)
    {
        OrderApply orderApply = orderApplyService.findOne(id);
        if(orderApply == null){
            return ResultTool.fail("找不到申请单");
        }
        ResultOrder order = new ResultOrder(orderApply);
        return ResultTool.success(order);
    }

    /**
     * 添加申请单
     * 申请单id生成规则：年份(4) + 申请部门(2) + 该部门该月第n份申请(2)
     * @param orderApply 对应申请单实体
     * @return
     */
    @PostMapping()
    public JsonResult addOrder(@Valid @RequestBody OrderApply orderApply){
        Date date = new Date();
        orderApply.setApplyDate(date);
        String oid = orderApplyService.addOrder(orderApply);
        asyncService.createNoticeToDeptLead(orderApply.getUid(), oid,
                "新的申请单提交通知",
                "申请人"+orderApply.getApplyUser()+"提交了一份申请单。");
        return ResultTool.success();
    }

    /**
     * 更新申请单
     * @param orderApply 对应申请单实体
     * @return
     */
    @PutMapping()
    public JsonResult updateOrder(HttpServletRequest request,
                                  @Valid @RequestBody OrderApply orderApply)
    {
        OrderApply o = orderApplyService.findOne(orderApply.getId());
        if(o == null){
            return ResultTool.fail("找不到申请单");
        }
        if(o.getStatus() != 0){
            return ResultTool.fail("申请单状态错误，不可修改");
        }
        if(!equal(request, orderApply.getUid())){
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
        if(o.getWithdrawalReason() != null || !"".equals(o.getWithdrawalReason())){
            //被打回的申请单重新提交
            orderApply.setWithdrawalReason("");
            asyncService.createNoticeToDeptLead(orderApply.getUid(), orderApply.getId(),
                    "申请单重新提交通知",
                    "申请人" + orderApply.getApplyUser() + "重新提交了申请单");
        }
        Date date = new Date();
        orderApply.setApplyDate(date);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    /**
     * 撤回申请单(将status改为 0，申请单状态改为已保存(可编辑))
     * @param request
     * @param map
     * @return
     */
    @PutMapping("/recall")
    public JsonResult recallOrder(HttpServletRequest request,
                                  @RequestBody Map<String, String> map)
    {
        String id = map.get("id");
        OrderApply orderApply = orderApplyService.findOne(id);
        if(orderApply == null){
            return ResultTool.fail("找不到申请单");
        }
        if(orderApply.getStatus() != 1){
            return ResultTool.fail("申请单状态错误，撤回失败");
        }
        if(!equal(request, orderApply.getUid())){
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
        orderApply.setStatus(0);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    /**
     * 删除对应id的申请单（将该申请单状态 status 设置为-1）
     * @param id 申请单id
     * @return
     */
    // 取消，只有管理员才能删除申请单
    /*@DeleteMapping("/{id}")
    public JsonResult deleteOrder(HttpServletRequest request,
                                  @PathVariable(value = "id") String id)
    {
        OrderApply orderApply = orderApplyService.findOne(id);
        if(!equal(request, orderApply.getUid())){
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
        orderApplyService.deleteOrder(orderApply);
        return ResultTool.success();
    }*/

    /**
     * 通过用户id获取该用户所有申请单（不包括已删除申请单）
     * @param id 用户id
     * @param page 查询的页码
     * @return
     */
    @GetMapping("/orders")
    public JsonResult getOrderByUser(@RequestParam(value = "id") String id,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        return ResultTool.success(orderApplyService.findByUser(id, page));
    }

    /**
     * 通过申请单id生成excel文件并返回
     * @param response
     * @param id 申请单id
     */
    @GetMapping("/file")
    public void generateFile(HttpServletResponse response,
                             @RequestParam(value = "id") String id)
    {
        OrderApply orderApply = orderApplyService.findOne(id);
        try {
            PrintWriter writer = response.getWriter();
            if(orderApply == null){
                writer.write(JSON.toJSONString(ResultTool.fail("找不到申请单")));
                writer.flush();
                writer.close();
                return;
            }
            FileTools.generateExcel(response, orderApply, true);
        }
        catch(IOException e){
//            return ResultTool.fail();
//            System.out.println(e.getMessage());
            response.setStatus(404);
            e.printStackTrace();
        }
//        return ResultTool.success();
    }

    /**
     * 下载可行性报告文件
     * @param response
     */
    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response){
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment;filename=test.docx");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        try {
            Resource resource = new ClassPathResource("docs/test.docx");   //静态文件位置
            InputStream stream = resource.getInputStream();
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(404);
        }
    }

    /**
     * 上传签名文件
     * @param file
     * @param id
     * @return
     */
    @PostMapping("/file")
    public JsonResult uploadFile(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "id") String id)
    {
        try {
            byte[] data = file.getBytes();
            orderApplyService.uploadFile(id, data);
            return ResultTool.success();
        }catch(Exception e){
            e.printStackTrace();
            return ResultTool.fail("读取上传文件失败");
        }
    }

    /**
     * 下载签名文件
     * @param response
     * @param id
     * @throws IOException
     */
    @GetMapping("/file/download")
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         @RequestParam(value = "id") String id)throws IOException
    {
        OrderApply orderApply = orderApplyService.findOne(id);
        if(!equal(request, orderApply.getUid())){
            response.setStatus(403);
            return;
        }
        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition", "attachment;filename=download.jpg");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        OutputStream outputStream = response.getOutputStream();
        try{
            outputStream.write(orderApply.getFile());
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean equal(HttpServletRequest request, String uid){
        return request.getAttribute("user").toString().equals(uid) ? true : false;
    }
}

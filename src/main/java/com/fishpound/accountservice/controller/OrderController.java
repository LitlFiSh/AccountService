package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import com.fishpound.accountservice.service.tools.FileGenerator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;

    /**
     * 通过申请单id获取申请单内容
     * @param id 申请单id
     * @return
     */
    @GetMapping()
    public JsonResult getOneOrder(@RequestParam(value = "id") String id){
        OrderApply orderApply = orderApplyService.findOne(id);
        return ResultTool.success(orderApply);
    }

    /**
     * 添加申请单
     * 申请单id生成规则：年份(4) + 申请部门(2) + 该部门该月第n份申请(2)
     * @param orderApply 对应申请单实体
     * @return
     */
    @PostMapping()
    public JsonResult addOrder(@Validated @RequestBody OrderApply orderApply){
        Date date = new Date();
        orderApply.setApplyDate(date);
        orderApplyService.addOrder(orderApply);
        return ResultTool.success();
    }

    /**
     * 更新申请单
     * todo 申请单中删除或增加申请列表时对应数据的增加和删除
     * @param orderApply 对应申请单实体
     * @return
     */
    @PutMapping()
    public JsonResult updateOrder(@Validated @RequestBody OrderApply orderApply){
        Date date = new Date();
        orderApply.setApplyDate(date);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    /**
     * 删除对应id的申请单（将该申请单状态 status 设置为-1）
     * @param id 申请单id
     * @return
     */
    @DeleteMapping("/{id}")
    public JsonResult deleteOrder(@PathVariable(value = "id") String id){
        orderApplyService.deleteOrder(id);
        return ResultTool.success();
    }

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
        if(id == null){
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
        return ResultTool.success(orderApplyService.findAllByUser(id, page));
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
        if(id == null){
            throw new RuntimeException("文件生成错误：参数为空");
        }
        OrderApply orderApply = orderApplyService.findOne(id);
        try {
            FileGenerator.generateExcel(response, orderApply, true);
        }
        catch(IOException e){
//            return ResultTool.fail();
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
//        return ResultTool.success();
    }

    @RequestMapping("/download")
    public void downloadFile(HttpServletResponse response){
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment;filename=test.docx");
        try {
            Resource resource = new ClassPathResource("templates/test.docx");
            File file = resource.getFile();
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
}

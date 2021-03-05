package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.AsyncService;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AsyncService asyncService;

    /**
     * 通过部门名称和月份查找部门该月所有申请单
     * @param dept 部门名称
     * @param date 精确到月份的日期，格式yyyy年MM月
     * @param page 查找的页数
     * @return
     */
    @GetMapping("/month")
    public JsonResult getDeptMonth(@RequestParam(value = "department")String dept,
                                  @RequestParam(value = "date")String date,
                                  @RequestParam(value = "page", defaultValue = "1")Integer page)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try{
            Date tragetMonth = format.parse(date);
            return ResultTool.success(orderApplyService.findByDepartmentAndMonth(dept, tragetMonth, page));
        }catch(Exception e){
            e.printStackTrace();
            return ResultTool.fail("日期格式错误");
        }
    }

    @GetMapping("/list")
    public JsonResult getList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        String uid = request.getAttribute("user").toString();
        UserInfo user = userInfoService.findById(uid);
        return ResultTool.success(orderApplyService.findByDepartmentAndStatus(user.getDepartment().getDeptName(), 1, page));
    }

    @PutMapping("/approve")
    public JsonResult approveOrder(HttpServletRequest request,
                                   @RequestBody Map<String, String> map)
    {
        String id = map.get("id");
        if(id == null && id.equals("")){
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
        if(checkAuthentication(request, id)){
            OrderApply orderApply = orderApplyService.findOne(id);
            if(orderApply.getStatus() != 1){
                return ResultTool.fail("不可以对申请单做此操作");
            }
            orderApply.setStatus(2);
            orderApply.setDeptLeaderSign(true);
            orderApply.setDeptLeaderSignDate(new Date());
            orderApplyService.updateOrder(orderApply);
            asyncService.createNoticeByUid(orderApply.getUid(), orderApply.getId(),
                    "申请单通过通知",
                    "编号" + orderApply.getId() + "的申请单已经通过部门审核。");
            return ResultTool.success();
        } else {
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
    }

    @PutMapping("/deny")
    public JsonResult denyOrder(HttpServletRequest request,
                                @RequestBody Map<String, String> map)
    {
        String id = map.get("id"), reason = map.get("reason");
        if(checkAuthentication(request, id)){
            OrderApply orderApply = orderApplyService.findOne(id);
            if(orderApply.getStatus() != 1){
                return ResultTool.fail("不可以对申请单做此操作");
            }
            orderApply.setStatus(0);
            orderApply.setWithdrawalReason(reason);
            orderApplyService.updateOrder(orderApply);
            asyncService.createNoticeByUid(orderApply.getUid(), orderApply.getId(),
                    "申请单打回通知",
                    "编号" + orderApply.getId() + "的申请单已被打回；打回原因：" + orderApply.getWithdrawalReason());
            return ResultTool.success();
        } else {
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
    }

    /**
     * 验证当前请求的用户是否有权限更改对应申请单
     * @param request
     * @param oid 申请单id
     * @return
     */
    private boolean checkAuthentication(HttpServletRequest request, String oid){
        String uid = request.getAttribute("user").toString();
        OrderApply orderApply = orderApplyService.findOne(oid);
        UserInfo user = userInfoService.findById(uid);
        if(user.getDepartment().getDeptName().equals(orderApply.getApplyDepartment())){
            return true;
        }
        return false;
    }
}

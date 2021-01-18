package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserInfoService userInfoService;

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

    @PutMapping("/approve")
    public JsonResult approveOrder(HttpServletRequest request,
                                   @RequestParam(value = "id") String id)
    {
        if(id == null && id.equals("")){
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
        if(checkAuthentication(request, id)){
            OrderApply orderApply = orderApplyService.findOne(id);
            orderApply.setStatus(2);
            orderApplyService.updateOrder(orderApply);
            return ResultTool.success();
        } else {
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
    }

    @PutMapping("/deny")
    public JsonResult denyOrder(HttpServletRequest request,
                                @RequestParam(value = "id") String id,
                                @RequestParam(value = "reason") String reason)
    {
        if(checkAuthentication(request, id)){
            OrderApply orderApply = orderApplyService.findOne(id);
            orderApply.setStatus(0);
            orderApply.setWithdrawalReason(reason);
            orderApplyService.updateOrder(orderApply);
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
        Department department = departmentService.findByDeptName(orderApply.getApplyDepartment());
        List<UserInfo> userInfoList = userInfoService.findByRoleAndDepartment(3, department.getDeptName());
        for(UserInfo item : userInfoList){
            if(item.getId().equals(uid)){
                return true;
            }
        }
        return false;
    }
}

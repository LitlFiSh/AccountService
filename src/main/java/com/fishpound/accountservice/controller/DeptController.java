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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
     * 通过查询条件模糊查找申请单（不包括已删除申请单）
     * @param request
     * @param oid 申请单id
     * @param startDate 申请日期开始范围
     * @param endDate 申请日期结束范围
     * @param user 申请人名称
     * @param fundcode 采购经费代码
     * @param page 查找页数
     * @return
     */
    @GetMapping("/orders")
    public JsonResult getDeptMonth(HttpServletRequest request,
                                   @RequestParam(value = "oid", defaultValue = "%") String oid,
                                   @RequestParam(value = "startDate", defaultValue = "1970-01-01") String startDate,
                                   @RequestParam(value = "endDate", defaultValue = "2038-01-19") String endDate,
                                   @RequestParam(value = "applyUser", defaultValue = "%") String user,
                                   @RequestParam(value = "fundcode", defaultValue = "%") String fundcode,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        String uid = request.getAttribute("user").toString();
        UserInfo userInfo = userInfoService.findById(uid);
        Map<String, Object> params = new HashMap<>();
        if(!"%".equals(oid)){
            oid = "%" + oid + "%";
        }
        if(!"%".equals(user)){
            user = "%" + user + "%";
        }
        if(!"%".equals(fundcode)){
            fundcode = "%" + fundcode + "%";
        }
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(startDate);
            Date end = format.parse(endDate);
        } catch(ParseException pe){
            return ResultTool.fail("日期格式错误，格式应为'yyyy-MM-dd'");
        }
        params.put("id", oid);
        params.put("department", userInfo.getDepartment().getDeptName());
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("user", user);
        params.put("fundcode", fundcode);
        params.put("status", -1);
        return ResultTool.success(orderApplyService.findInCondition(params, page));
    }

    /**
     * 查找部门中待审批的申请单
     * @param request
     * @param page
     * @return
     */
    @GetMapping("/list")
    public JsonResult getList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        String uid = request.getAttribute("user").toString();
        UserInfo user = userInfoService.findById(uid);
        return ResultTool.success(orderApplyService.findByDepartmentAndStatus(user.getDepartment().getDeptName(), 1, page));
    }

    /**
     * 审批通过该申请单
     * @param request
     * @param map
     * @return
     */
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
            if(orderApply == null){
                return ResultTool.fail("找不到该申请单");
            }
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

    /**
     * 打回该申请单
     * @param request
     * @param map
     * @return
     */
    @PutMapping("/deny")
    public JsonResult denyOrder(HttpServletRequest request,
                                @RequestBody Map<String, String> map)
    {
        String id = map.get("id"), reason = map.get("reason");
        if(id.equals("") || id == null || reason.equals("") || reason == null){
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
        if(checkAuthentication(request, id)){
            OrderApply orderApply = orderApplyService.findOne(id);
            if(orderApply == null){
                return ResultTool.fail("找不到该申请单");
            }
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

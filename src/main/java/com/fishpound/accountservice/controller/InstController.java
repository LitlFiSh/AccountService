package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.AsyncService;
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
@RequestMapping("/inst")
public class InstController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AsyncService asyncService;

    /**
     * 通过查询条件模糊查找所有申请单（不包括已删除申请单）
     * @param oid 申请单id
     * @param startDate 申请日期开始范围
     * @param endDate 申请日期结束范围
     * @param user 申请人名称
     * @param fundCode 采购经费代码
     * @param page 查找页数
     * @return
     */
    @GetMapping("/orders")
    public JsonResult getAllMonth(@RequestParam(value = "oid", defaultValue = "%") String oid,
                                  @RequestParam(value = "applyDept", defaultValue = "%") String department,
                                  @RequestParam(value = "startDate", defaultValue = "1970-01-01") String startDate,
                                  @RequestParam(value = "endDate", defaultValue = "2038-01-19") String endDate,
                                  @RequestParam(value = "user", defaultValue = "%") String user,
                                  @RequestParam(value = "fundCode", defaultValue = "%") String fundCode,
                                  @RequestParam(value = "status", defaultValue = "10") Integer status,
                                  @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        Map<String, Object> params = new HashMap<>();
        if(!"%".equals(oid)){
            oid = "%" + oid + "%";
        }
        if(!"%".equals(department)){
            department = "%" + department + "%";
        }
        if(!"%".equals(user)){
            user = "%" + user + "%";
        }
        if(!"%".equals(fundCode)){
            fundCode = "%" + fundCode + "%";
        }
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.parse(startDate);
            format.parse(endDate);
        } catch(ParseException pe){
            return ResultTool.fail("日期格式错误，格式应为'yyyy-MM-dd'");
        }
        params.put("id", oid);
        params.put("department", department);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("user", user);
        params.put("fundCode", fundCode);
        params.put("status", status);
        return ResultTool.success(orderApplyService.findInCondition(params, page));
    }

    /**
     * 查找所有部门待审批的申请单(status=2,部门领导已审批)
     * @param request
     * @param page
     * @return
     */
    @GetMapping("/list")
    public JsonResult getList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        return ResultTool.success(orderApplyService.findByStatus(2, page));
    }

    /**
     * 审批通过
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
        OrderApply orderApply = orderApplyService.findOne(id);
        if(orderApply == null){
            return ResultTool.fail("找不到申请单");
        }
        if(orderApply.getStatus() != 2){
            return ResultTool.fail("不可以申请单做此操作");
        }
        orderApply.setStatus(3);
        for(OrderList list : orderApply.getOrderLists()){
            list.setStatus(3);
        }
        orderApply.setDeptLeaderSign(true);
        orderApply.setDeptLeaderSignDate(new Date());
        orderApplyService.updateOrder(orderApply);
        asyncService.createNoticeByUid(orderApply.getUid(), orderApply.getId(),
                "申请单通过通知",
                "编号" + orderApply.getId() + "的申请单已经通过审核。");
        return ResultTool.success();
    }

    /**
     * 打回申请单
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
        OrderApply orderApply = orderApplyService.findOne(id);
        if(orderApply == null){
            return ResultTool.fail("找不到申请单");
        }
        if(orderApply.getStatus() != 2){
            return ResultTool.fail("不可以申请单做此操作");
        }
        orderApply.setStatus(0);
        orderApply.setWithdrawalReason(reason);
        orderApplyService.updateOrder(orderApply);
        asyncService.createNoticeByUid(orderApply.getUid(), orderApply.getId(),
                "申请单打回通知",
                "编号" + orderApply.getId() + "的申请单已被打回；打回原因：" + orderApply.getWithdrawalReason());
        return ResultTool.success();
    }
}

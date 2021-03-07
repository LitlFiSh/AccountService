package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.AsyncService;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * 通过月份查找当月所有申请单
     * @param date
     * @param page
     * @return
     */
    @GetMapping("/orders")
    public JsonResult getAllMonth(@RequestParam(value = "date")String date,
                                  @RequestParam(value = "page", defaultValue = "1")Integer page)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try{
            Date targetMonth = format.parse(date);
            return ResultTool.success(orderApplyService.findByMonth(targetMonth, page));
        }catch(Exception e){
            e.printStackTrace();
            return ResultTool.fail("日期格式错误");
        }
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
        orderApply.setStatus(2);
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

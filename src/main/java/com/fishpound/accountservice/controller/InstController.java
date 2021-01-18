package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inst")
public class InstController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;

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

    @PutMapping("/approve")
    public JsonResult approveOrder(HttpServletRequest request,
                                   @RequestParam(value = "id") String id)
    {
        if(id == null && id.equals("")){
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
        OrderApply orderApply = orderApplyService.findOne(id);
        orderApply.setStatus(2);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    @PutMapping("/deny")
    public JsonResult denyOrder(HttpServletRequest request,
                                @RequestParam(value = "id") String id,
                                @RequestParam(value = "reason") String reason)
    {
        OrderApply orderApply = orderApplyService.findOne(id);
        orderApply.setStatus(0);
        orderApply.setWithdrawalReason(reason);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }
}

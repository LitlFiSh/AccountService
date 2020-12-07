package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderList;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderListService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderlist")
public class OrderListController {
    @Autowired
    OrderListService orderListService;
    @Autowired
    UserInfoService userInfoService;

    @GetMapping()
    public JsonResult getOrderLists(@RequestParam(value = "id") String id){
        if(id != null){
            List<OrderList> orderLists = orderListService.findAllByUserId(id);
            return ResultTool.success(orderLists);
        } else{
            return ResultTool.fail(ResultCode.PARAM_IS_NULL);
        }
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteOrderList(@PathVariable(value = "id") String id){
        if(orderListService.deleteOrderList(id)){
            return ResultTool.success();
        } else{
            return ResultTool.fail();
        }
    }

    @PostMapping()
    public JsonResult addOrderList(@RequestBody OrderList orderList){
        //通过提交人员所在部门及提交时间查找对应部门的申请单，如果当月该部门还没有申请单，则新建申请单
        UserInfo user = userInfoService.findById(orderList.getId());
        return null;
    }
}

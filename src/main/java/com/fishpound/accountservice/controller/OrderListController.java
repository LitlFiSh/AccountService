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

/**
 * todo 待完善
 */
@RestController
@RequestMapping("/orderlist")
public class OrderListController {
    @Autowired
    OrderListService orderListService;

//    @DeleteMapping("/{id}")
//    public JsonResult deleteOrderList(@PathVariable(value = "id") String id){
//        if(orderListService.deleteOrderList(id)){
//            return ResultTool.success();
//        } else{
//            return ResultTool.fail();
//        }
//    }
}

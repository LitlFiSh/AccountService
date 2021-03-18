package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

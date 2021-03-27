package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/all")
    public JsonResult findAll(@RequestParam(value = "page") Integer page){
        Map<String, Object> resultMap = orderListService.findAll(page);
        return ResultTool.success(resultMap);
    }
}

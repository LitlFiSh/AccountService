package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.OrderApply;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;

    @GetMapping()
    public JsonResult getOneOrder(@RequestParam(value = "id") String id){
        return ResultTool.success(orderApplyService.findOne(id));
    }

    @PostMapping()
    public JsonResult addOrder(@Validated @RequestBody OrderApply orderApply){
        orderApplyService.addOrder(orderApply);
        return ResultTool.success();
    }

    @PutMapping()
    public JsonResult updateOrder(@Validated @RequestBody OrderApply orderApply){
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteOrder(@PathVariable(value = "id") String id){
        orderApplyService.deleteOrder(id);
        return ResultTool.success();
    }
}

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
public class OrderApplyController {
    @Autowired
    OrderApplyService orderApplyService;
    @Autowired
    UserInfoService userInfoService;

    @GetMapping()
    public JsonResult getOneOrder(@RequestParam(value = "id") Integer id){
        return ResultTool.success(orderApplyService.findOne(id));
    }

    @GetMapping("/all")
    public JsonResult getAllOrder(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "page") Integer page)
    {
        Page<OrderApply> orderApplies = orderApplyService.findAll(username, page);
        return ResultTool.success(orderApplies);
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
    public JsonResult deleteOrder(@PathVariable(value = "id") Integer id){
        orderApplyService.deleteOrder(id);
        return ResultTool.success();
    }
}

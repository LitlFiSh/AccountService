package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/hello")
    public JsonResult testUser(){
        return ResultTool.success("user hello success");
    }

    @GetMapping("/admin/hello")
    public JsonResult testAdmin(){
        return ResultTool.success("admin hello success");
    }
}

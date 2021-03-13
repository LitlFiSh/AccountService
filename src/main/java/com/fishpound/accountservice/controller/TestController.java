package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/user/hello")
    public JsonResult testUser() throws Exception{
        return ResultTool.success("user hello success");
    }

    @GetMapping("/dept/hello")
    public JsonResult testDept(){
        return ResultTool.success("dept hello success");
    }

    @GetMapping("/inst/hello")
    public JsonResult testInst(){
        return ResultTool.success("inst hello success");
    }

    @GetMapping("/admin/hello")
    public JsonResult testAdmin(){
        return ResultTool.success("admin hello success");
    }

    @GetMapping("/user/exception")
    public JsonResult exTest() throws Exception{
        userInfoService.throwEx();
        return ResultTool.success();
    }
}

package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.OrderApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    OrderApplyService orderApplyService;

    @GetMapping("/month")
    public JsonResult getAllMonth(@RequestParam(value = "department")String dept,
                                  @RequestParam(value = "date")String date,
                                  @RequestParam(value = "page", defaultValue = "1")Integer page)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try{
            Date tragetMonth = format.parse(date);
            return ResultTool.success(orderApplyService.findByDepartmentAndMonth(dept, tragetMonth, page));
        }catch(Exception e){
            e.printStackTrace();
            return ResultTool.fail("日期参数格式错误");
        }
    }
}

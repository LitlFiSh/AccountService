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

    /**
     * 通过部门名称和月份查找部门该月所有申请单
     * @param dept 部门名称
     * @param date 精确到月份的日期，格式yyyy年MM月
     * @param page 查找的页数
     * @return
     */
    @GetMapping("/month")
    public JsonResult getDeptMonth(@RequestParam(value = "department")String dept,
                                  @RequestParam(value = "date")String date,
                                  @RequestParam(value = "page", defaultValue = "1")Integer page)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        try{
            Date tragetMonth = format.parse(date);
            return ResultTool.success(orderApplyService.findByDepartmentAndMonth(dept, tragetMonth, page));
        }catch(Exception e){
            e.printStackTrace();
            return ResultTool.fail("日期格式错误");
        }
    }
}

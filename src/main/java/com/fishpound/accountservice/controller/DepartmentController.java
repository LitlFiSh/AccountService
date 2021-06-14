package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * 获取数据库中存储的所有部门信息
     * @return
     */
    @GetMapping("/departments")
    public JsonResult getAllDept(){
        return ResultTool.success(departmentService.findAll());
    }

    @PostMapping()
    public JsonResult addDepartment(@RequestBody Department department){
        departmentService.addDept(department);
        return ResultTool.success();
    }

    @PutMapping()
    public JsonResult updateDepartment(@RequestBody Department department){
        departmentService.updateDept(department);
        return ResultTool.success();
    }

    @DeleteMapping("/{id}")
    public JsonResult deleteDepartment(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        if(department.getUserInfoSet() != null && department.getUserInfoSet().size() > 0){
            return ResultTool.fail("要删除的部门中还存在用户，请先将用户转移到其他部门");
        }
        departmentService.deleteDept(id);
        return ResultTool.success();
    }
}

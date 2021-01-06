package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.OrderApplyService;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RoleService roleService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    OrderApplyService orderApplyService;

    /**
     * 添加用户
     * todo 测试可用性
     * @param resultUser
     * @return
     */
    @PostMapping("/user")
    public JsonResult adduser(@Validated @RequestBody ResultUser resultUser){
        if(userInfoService.findById(resultUser.getId()) != null){
            return ResultTool.fail(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
        }
        UserInfo userInfo = new UserInfo();
        Account account = new Account();
        Department department = departmentService.findById(resultUser.getDepartment());
        Role role = roleService.findById(resultUser.getRole());

        account.setId(resultUser.getId());
        account.setPassword(bCryptPasswordEncoder.encode(resultUser.getPassword()));
        account.setActive(true);
        account.setRole(role);

        userInfo.setId(resultUser.getId());
        userInfo.setUsername(resultUser.getUsername());
        userInfo.setDepartment(department);
        userInfo.setAccount(account);

        userInfoService.save(userInfo);

        return ResultTool.success();
    }

    @GetMapping("/deleted")
    public JsonResult getDeletedOrder(@RequestParam(value = "page", defaultValue = "1") Integer page){
        return ResultTool.success(orderApplyService.findDeleted(page));
    }

    @PutMapping("/reduct/{id}")
    public JsonResult reductOrder(@PathVariable(value = "id") String id){
        OrderApply orderApply = orderApplyService.findOne(id);
        orderApply.setStatus(1);
        orderApplyService.updateOrder(orderApply);
        return ResultTool.success();
    }
}

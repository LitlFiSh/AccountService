package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.AccountService;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AccountService accountService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    RoleService roleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 通过用户 id 查找用户信息并返回
     * @param id
     * @return
     */
    @GetMapping("/info")
    public JsonResult getUserInfo(@RequestParam String id){
        UserInfo userInfo = userInfoService.findById(id);
        Account user_account = userInfo.getAccount();
        Role user_role = user_account.getRole();
        Department user_department = userInfo.getDepartment();
        return userInfo == null ? ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST) :
                ResultTool.success(new ResultUser(
                        userInfo.getId(),
                        userInfo.getUsername(),
                        user_department.getDeptName(),
                        user_role.getId()
                        )
                );
    }
}

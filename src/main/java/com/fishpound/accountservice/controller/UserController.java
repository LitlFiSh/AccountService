package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.security.jwt.JWTTokenUtils;
import com.fishpound.accountservice.service.AccountService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    AccountService accountService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/info")
    public JsonResult getUserInfo(@RequestParam String username){
        UserInfo userInfo = userInfoService.findByUsername(username);
        Account user_account = userInfo.getAccount();
        List<Role> roles = user_account.getRoles();
        List<String> user_roles = new ArrayList<>();
        for (Role role : roles) {
            user_roles.add(role.getRoleName());
        }
        Department user_department = userInfo.getDepartment();
        return userInfo == null ? ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST) :
                ResultTool.success(new ResultUser(
                        userInfo.getId(),
                        userInfo.getUsername(),
                        user_department.getDeptName(),
                        user_roles
                        )
                );
    }

    @PostMapping("/signup")
    public JsonResult signup(@RequestParam ResultUser user){
        Account account = new Account();
        UserInfo userInfo = new UserInfo();
        account.setActive(true);
        account.setId(user.getId());
        account.setPassword(user.getPassword());
//        account.setRoles(user.getRoles());
        account.setUserInfo(userInfo);
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setDepartment(departmentRepository.getByDeptName(user.getDepartment()));
        userInfo.setAccount(account);
        //save account, save userInfo
        if(accountService.save(account) && userInfoService.save(userInfo)) {
            return ResultTool.success();
        } else{
            return ResultTool.fail();
        }
    }
}

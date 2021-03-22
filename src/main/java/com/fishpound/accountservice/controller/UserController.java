package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultCode;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    @Autowired
    CacheService cacheService;

    /**
     * 通过用户 id 查找用户信息并返回
     * @param id 用户id
     * @return
     */
    @GetMapping("/info")
    public JsonResult getUserInfo(@RequestParam String id){
        UserInfo userInfo = userInfoService.findById(id);
        if(userInfo == null){
            return ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        Account user_account = userInfo.getAccount();
        Role user_role = user_account.getRole();
        Department user_department = userInfo.getDepartment();
        return ResultTool.success(new ResultUser(
                        userInfo.getId(),
                        userInfo.getUsername(),
                        user_department.getDeptName(),
                        user_role.getRoleDescription()
                        )
                );
    }

    /**
     * 退出登录，销毁缓存中的 token
     * @param request
     * @param uid
     * @return
     */
    @GetMapping("/logout")
    public JsonResult logout(HttpServletRequest request,
                             @RequestParam(value = "uid") String uid)
    {
        if(request.getAttribute("user").equals(uid)){
            System.out.println(uid);
            cacheService.invalidateCache("token", uid);
            return ResultTool.success();
        } else{
            return ResultTool.fail("注销用户非当前登录用户");
        }
    }

    /**
     * 修改密码
     * @param formMap
     * @return
     */
    @PutMapping("/pwd")
    public JsonResult alteruserPassword(@RequestBody Map<String, String> formMap)
    {
        String uid = formMap.get("uid");
        String newPassword = formMap.get("newPassword");
        String oldPassword = formMap.get("oldPassword");
        if(uid.isEmpty() || newPassword.isEmpty() || oldPassword.isEmpty()){
            return ResultTool.fail("传入参数为空");
        }
        if(accountService.alterPassword(uid, newPassword, oldPassword)){
            cacheService.invalidateCache("token", uid);
            return ResultTool.success();
        } else{
            return ResultTool.fail();
        }
    }

    /**
     * 模糊搜索用户名
     * @param name 用户名
     * @return
     */
    @GetMapping("/search")
    public JsonResult searchUsername(@RequestParam(value = "name") String name){
        name = "%" + name + "%";
        List<String> list = userInfoService.findUsername(name);
        return ResultTool.success(list);
    }
}

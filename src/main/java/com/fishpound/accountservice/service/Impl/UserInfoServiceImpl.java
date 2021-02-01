package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }

    @Override
    public UserInfo findById(String id) {
        return userInfoRepository.getById(id);
    }

    @Override
    public boolean save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
        return true;
    }

    @Override
    public boolean delete(UserInfo userInfo) {
        userInfoRepository.delete(userInfo);
        return true;
    }

    @Override
    public List<UserInfo> findByRoleAndDepartment(Integer rid, String deptName) {
        return userInfoRepository.findAllByAccount_Role_IdAndDepartment_DeptName(rid, deptName);
    }

    @Override
    public Map<String, Object> batchAddUser(List<Map> userList) {
        int n = 0, no = 1;
        Map<String, Object> res = new HashMap<>();
        res.put("count", userList.size());
        List<Map> resultList = new ArrayList<>();
        UserInfo userInfo;
        Account account;
        Department department;
        Role role;
        for(Map user : userList){
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("no", String.valueOf(no++));
            String id = user.get("uid").toString();
            String username = user.get("username").toString();
            String deptName = user.get("department").toString();
            String roleName = user.get("role").toString();
            //判断数据是否为空及合法性
            if(id == null || id.equals("")){
                itemMap.put("message", "用户id为空");
                continue;
            } else if(username == null || username.equals("")){
                itemMap.put("message", "用户名为空");
                continue;
            } else if(deptName == null || deptName.equals("")){
                itemMap.put("message", "部门名称为空");
                continue;
            } else if(roleName == null || roleName.equals("")){
                roleName = "普通用户";
            }
            if(userInfoRepository.getById(id) != null){
                itemMap.put("message", "用户已存在");
                continue;
            } else {
                department = departmentService.findByDeptName(deptName);
                if(department == null){
                    itemMap.put("message", "部门不存在");
                    continue;
                }
                role = roleService.findByDescription(roleName);
                if(role == null){
                    itemMap.put("message", "角色不存在");
                    continue;
                }
                account = new Account(id, bCryptPasswordEncoder.encode("123456"), role);
                userInfo = new UserInfo(id, username, account, department);
                userInfoRepository.save(userInfo);
                itemMap.put("success", "成功");
                n++;
            }
            resultList.add(itemMap);
        }
        res.put("success", n);
        res.put("result", resultList);
        return res;
    }
}

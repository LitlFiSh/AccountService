package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.*;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.result.ResultUser;
import com.fishpound.accountservice.service.DepartmentService;
import com.fishpound.accountservice.service.RoleService;
import com.fishpound.accountservice.service.SettingsService;
import com.fishpound.accountservice.service.UserInfoService;
import com.fishpound.accountservice.service.tools.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

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
    @Autowired
    private SettingsService settingsService;

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
    public Map<String, Object> findAllExcept(String uid, Integer page) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ResultUser> resultUserList = new ArrayList<>();
        ResultUser resultUser;
        PageTools pageTools = new PageTools("id", Sort.Direction.DESC, page);
        Page<UserInfo> users = userInfoRepository.findAllByIdNot(uid, pageTools.sortSingle());
        List<UserInfo> userInfoList = users.getContent();
        for(UserInfo user : userInfoList){
            Settings userSetting = settingsService.findByDescription(user.getId());
            if(userSetting != null) {
                resultUser = new ResultUser(user.getId(),
                        user.getUsername(),
                        user.getDepartment().getDeptName(),
                        user.getAccount().getRole().getRoleDescription(),
                        userSetting.getValue().equals("1") ? true : false);
            } else{
                resultUser = new ResultUser(user.getId(),
                        user.getUsername(),
                        user.getDepartment().getDeptName(),
                        user.getAccount().getRole().getRoleDescription(),
                        false);
            }
            resultUserList.add(resultUser);
        }
        resultMap.put("totalPages", users.getTotalPages());
        resultMap.put("totalElements", users.getTotalElements());
        resultMap.put("size", users.getSize());
        resultMap.put("number", users.getNumber());
        resultMap.put("content", resultUserList);
        return resultMap;
    }

    @Override
    public List<UserInfo> findByRoleAndDepartment(Integer rid, String deptName) {
        return userInfoRepository.findAllByAccount_Role_Id(rid);
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
                itemMap.put("message", "成功");
                n++;
            }
            resultList.add(itemMap);
        }
        res.put("success", n);
        res.put("result", resultList);
        return res;
    }

    @Override
    public List<UserInfo> findUserByRole(Integer rid) {
        return userInfoRepository.findAllByAccount_Role_Id(rid);
    }

    @Override
    public List<UserInfo> findUsername(String username) {
        return userInfoRepository.findUsername(username);
    }

    @Override
    public void throwEx() throws Exception {
        throw new MissingServletRequestParameterException("12", "123");
    }
}

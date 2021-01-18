package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo findByUsername(String username);
    UserInfo findById(String id);
    boolean save(UserInfo userInfo);
    boolean delete(UserInfo userInfo);
    List<UserInfo> findByRoleAndDepartment(Integer rid, String deptName);
}

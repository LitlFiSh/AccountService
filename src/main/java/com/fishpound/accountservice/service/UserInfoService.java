package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    UserInfo findByUsername(String username);
    UserInfo findById(String id);
    boolean save(UserInfo userInfo);
    boolean delete(UserInfo userInfo);
    Map<String, Object> findAllExcept(String uid, Integer page);
    List<UserInfo> findByRoleAndDepartment(Integer rid, String deptName);
    Map<String, Object> batchAddUser(List<Map> userList);
    List<UserInfo> findUserByRole(Integer rid);
    List<UserInfo> findUsername(String username);

    void throwEx() throws Exception;
}

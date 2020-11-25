package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.UserInfo;

public interface UserInfoService {
    UserInfo findByUsername(String username);
    UserInfo findById(String id);
    boolean save(UserInfo userInfo);
    boolean delete(UserInfo userInfo);
}

package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.repository.UserInfoRepository;
import com.fishpound.accountservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
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


}

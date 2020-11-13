package com.fishpound.accountservice;

import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.entity.UserInfo;
import com.fishpound.accountservice.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AccountserviceApplicationTests {
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void passwordEncoded(){
        System.out.println(bCryptPasswordEncoder.encode("111111"));
    }

    @Test
    void databaseTest(){
        //用用户名查询出用户的全部信息 account表 userinfo表 角色信息以及部门信息
        UserInfo user01 = userInfoService.findByUsername("admin01");
        System.out.println(user01.getUsername());
        System.out.println(user01.getAccount().getPassword());
        System.out.println(user01.getDepartment().getDeptName());
        List<Role> roles = user01.getAccount().getRoles();
        for(Role r : roles){
            System.out.println(r.getRoleName());
        }
    }

}

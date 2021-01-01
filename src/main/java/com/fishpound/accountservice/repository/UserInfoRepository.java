package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo getById(String id);
    UserInfo findByUsername(String username);
    List<UserInfo> findByAccount_Role_IdAndDepartment_DeptName(Integer rid, String deptName);
}

package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo getById(String id);
    UserInfo findByUsername(String username);
    Page<UserInfo> findAllByIdNot(String uid, Pageable pageable);
    List<UserInfo> findAllByAccount_Role_IdAndDepartment_DeptName(Integer rid, String deptName);
}

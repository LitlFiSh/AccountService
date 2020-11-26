package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    Department getById(String id);
    Department findByDeptName(String deptName);
}

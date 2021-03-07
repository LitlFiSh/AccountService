package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Department;

import java.util.List;

public interface DepartmentService {
    void addDept(Department department);
    void updateDept(Department department);
    void deleteDept(String id);
    Department findByDeptName(String name);
    Department findById(String id);
    List<Department> findAll();
}

package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Department;

public interface DepartmentService {
    Department findByDeptName(String name);
    Department findById(String id);
}

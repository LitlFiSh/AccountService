package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department findByDeptName(String name) {
        return departmentRepository.findByDeptName(name);
    }

    @Override
    public Department findById(String id) {
        return departmentRepository.getById(id);
    }
}

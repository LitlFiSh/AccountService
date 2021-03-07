package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Department;
import com.fishpound.accountservice.repository.DepartmentRepository;
import com.fishpound.accountservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void addDept(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void updateDept(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public void deleteDept(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department findByDeptName(String name) {
        return departmentRepository.findByDeptName(name);
    }

    @Override
    public Department findById(String id) {
        return departmentRepository.getById(id);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}

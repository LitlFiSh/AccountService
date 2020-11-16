package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Role;
import com.fishpound.accountservice.repository.RoleRepository;
import com.fishpound.accountservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}

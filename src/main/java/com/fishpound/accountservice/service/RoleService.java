package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Role;

public interface RoleService {
    Role findById(Integer id);
    Role findByRoleName(String roleName);
    Role findByDescription(String roleDescription);
}

package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}

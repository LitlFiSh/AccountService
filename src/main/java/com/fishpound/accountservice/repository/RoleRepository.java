package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getById(Integer id);
    Role findByRoleName(String roleName);
}

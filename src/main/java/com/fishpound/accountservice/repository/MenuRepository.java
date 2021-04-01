package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Menu getById(Integer id);
    Menu findByName(String name);
}

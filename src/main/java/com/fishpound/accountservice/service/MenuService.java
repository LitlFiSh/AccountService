package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Menu;

import java.util.List;

public interface MenuService {
    void addMenu(Menu menu);
    void updateMenu(Menu menu);
    void deleteMenu(Integer id);
    List<Menu> findAll();
}

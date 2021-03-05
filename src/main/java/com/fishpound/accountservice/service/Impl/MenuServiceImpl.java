package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Menu;
import com.fishpound.accountservice.repository.MenuRepository;
import com.fishpound.accountservice.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void addMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }
}

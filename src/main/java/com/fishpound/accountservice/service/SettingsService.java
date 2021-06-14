package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Settings;

import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/3/21 10:15
 */
public interface SettingsService {
    void addSetting(Settings settings);
    void updateSettings(Settings settings);
    void deleteSettings(Integer id);
    Settings findByDescription(String desc);
    List<Settings> fingAllByDescription(String desc);
    List<Settings> findAll();
}

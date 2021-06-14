package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Settings;
import com.fishpound.accountservice.repository.SettingsRepository;
import com.fishpound.accountservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Litl_FiSh
 * @Date 2021/3/21 10:17
 */
@Service
public class SettingsServiceImpl implements SettingsService {
    @Autowired
    SettingsRepository settingsRepository;

    @Override
    public void addSetting(Settings settings) {
        settingsRepository.save(settings);
    }

    @Override
    public void updateSettings(Settings settings) {
        settingsRepository.save(settings);
    }

    @Override
    public void deleteSettings(Integer id) {
        settingsRepository.deleteById(id);
    }

    @Override
    public Settings findByDescription(String desc) {
        return settingsRepository.findByDescription(desc);
    }

    @Override
    public List<Settings> fingAllByDescription(String desc) {
        return settingsRepository.findAllByDescription(desc);
    }

    @Override
    public List<Settings> findAll() {
        return settingsRepository.findAll();
    }
}

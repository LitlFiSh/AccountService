package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Litl_FiSh
 * @Date 2021/3/21 10:12
 */
public interface SettingsRepository extends JpaRepository<Settings, Integer> {
    Settings findByDescription(String desc);
}

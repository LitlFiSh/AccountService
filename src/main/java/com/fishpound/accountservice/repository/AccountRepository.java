package com.fishpound.accountservice.repository;

import com.fishpound.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account getById(String id);
}

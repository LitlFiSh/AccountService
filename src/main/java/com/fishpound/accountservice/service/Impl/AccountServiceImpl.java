package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.repository.AccountRepository;
import com.fishpound.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public boolean save(Account account) {
        accountRepository.save(account);
        return true;
    }
}

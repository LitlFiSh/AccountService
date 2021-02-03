package com.fishpound.accountservice.service.Impl;

import com.fishpound.accountservice.entity.Account;
import com.fishpound.accountservice.repository.AccountRepository;
import com.fishpound.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean save(Account account) {
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean alterPassword(String uid, String newPwd, String oldPwd) {
        Account account = accountRepository.getById(uid);
        if(account != null && bCryptPasswordEncoder.matches(oldPwd, account.getPassword())){
            account.setPassword(bCryptPasswordEncoder.encode(newPwd));
            accountRepository.save(account);
            return true;
        } else{
            return false;
        }
    }
}

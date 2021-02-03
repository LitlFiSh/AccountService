package com.fishpound.accountservice.service;

import com.fishpound.accountservice.entity.Account;

public interface AccountService {
    boolean save(Account account);
    boolean alterPassword(String uid, String newPwd, String oldPwd);
}

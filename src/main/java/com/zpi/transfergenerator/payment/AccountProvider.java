package com.zpi.transfergenerator.payment;

import com.zpi.transfergenerator.model.Account;

public class AccountProvider {
    private final Account account;

    public AccountProvider(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}

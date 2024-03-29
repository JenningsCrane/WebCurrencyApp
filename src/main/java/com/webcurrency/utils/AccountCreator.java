package com.webcurrency.utils;

import com.webcurrency.models.account.Account;
import com.webcurrency.models.account.CurrencyType;
import com.webcurrency.models.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountCreator {
    public static List<Account> createAccountsForNewUser(User user) {
        List<Account> accounts = new ArrayList<>();

        accounts.add(createAccount(user, CurrencyType.RUB, new BigDecimal("5000")));
        accounts.add(createAccount(user, CurrencyType.AED, BigDecimal.ZERO));
        accounts.add(createAccount(user, CurrencyType.CNY, BigDecimal.ZERO));

        return accounts;
    }

    private static Account createAccount(User user, CurrencyType currency, BigDecimal initialBalance) {
        return Account.builder()
                .user(user)
                .currency(currency)
                .balance(initialBalance)
                .build();
    }
}

package com.webcurrency.repositories;

import com.webcurrency.models.account.Account;
import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByUserId(Long userId);

    Optional<Account> findByUserAndCurrencyType(User user, CurrencyType currencyType);
}

package com.webcurrency.repositories;

import com.webcurrency.models.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountByUserId(Long userId);
}

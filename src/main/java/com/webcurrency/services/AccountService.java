package com.webcurrency.services;

import com.webcurrency.exceptions.AccountNotFoundException;
import com.webcurrency.models.account.Account;
import com.webcurrency.repositories.AccountRepository;
import com.webcurrency.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    public AccountRepository accountRepository;
    public UserRepository userRepository;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getByUserId(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return accountRepository.findAccountByUserId(userId);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));
    }

    @Transactional
    public Account increaseBalance(Long accountId, BigDecimal amount) {
        Account account = getById(accountId);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account decreaseBalance(Long accountId, BigDecimal amount) {
        Account account = getById(accountId);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

}

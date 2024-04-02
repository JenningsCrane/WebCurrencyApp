package com.webcurrency.services;

import com.webcurrency.exceptions.AccountNotFoundException;
import com.webcurrency.exceptions.LowBalanceException;
import com.webcurrency.models.account.Account;
import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getByUserId(UUID userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));
    }

    @Transactional
    public void increaseBalance(Long accountId, BigDecimal amount) {
        Account account = getById(accountId);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Transactional
    public void decreaseBalance(Long accountId, BigDecimal amount) {
        Account account = getById(accountId);
        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowBalanceException("Недостаточно средств на счете");
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    public Account getByUserIdAndCurrency(UUID userId, CurrencyType fromCurrency) {
        return accountRepository.findByUserAndCurrencyType(userService.getById(userId), fromCurrency)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));
    }
}

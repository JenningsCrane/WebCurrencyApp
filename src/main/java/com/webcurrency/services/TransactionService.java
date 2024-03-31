package com.webcurrency.services;

import com.webcurrency.dto.TransactionResponse;
import com.webcurrency.models.account.Account;
import com.webcurrency.models.account.CurrencyType;
import com.webcurrency.models.account.Transaction;
import com.webcurrency.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CurrencyRateService currencyRateService;
    private final UserService userService;

    public List<Transaction> findAllByUserId(Long id) {
        return transactionRepository.findAllByUser(userService.getById(id));
    }

    @Transactional
    public void sellCurrency(Long userId, CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) {
        Account senderAccount = accountService.getByUserIdAndCurrency(userId, fromCurrency);
        Account receiverAccount = accountService.getByUserIdAndCurrency(userId, toCurrency);

        BigDecimal currencyValue = currencyRateService.getCurrencyValue(fromCurrency);
        BigDecimal totalPrice = amount.multiply(currencyValue);

        processCurrencyTransaction(userId, senderAccount, receiverAccount, amount, totalPrice);
    }

    @Transactional
    public void buyCurrency(Long userId, CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) {
        Account senderAccount = accountService.getByUserIdAndCurrency(userId, fromCurrency);
        Account receiverAccount = accountService.getByUserIdAndCurrency(userId, toCurrency);

        BigDecimal currencyValue = currencyRateService.getCurrencyValue(toCurrency);
        BigDecimal totalPrice = amount.multiply(currencyValue);

        processCurrencyTransaction(userId, senderAccount, receiverAccount, totalPrice, amount);
    }

    private void processCurrencyTransaction(Long userId, Account senderAccount, Account receiverAccount,
                                            BigDecimal debitAmount, BigDecimal creditAmount) {
        accountService.decreaseBalance(senderAccount.getId(), debitAmount);
        accountService.increaseBalance(receiverAccount.getId(), creditAmount);

        Transaction transaction = Transaction.builder()
                .user(userService.getById(userId))
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .amount(creditAmount)
                .date(new Date())
                .build();

        transactionRepository.save(transaction);
    }

}


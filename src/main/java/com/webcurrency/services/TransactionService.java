package com.webcurrency.services;

import com.webcurrency.models.account.Account;
import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.models.account.Transaction;
import com.webcurrency.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final GraphService graphService;
    private final UserService userService;

    public List<Transaction> findAllByUserId(UUID id) {
        return transactionRepository.findAllByUser(userService.getById(id));
    }

    @Transactional
    public void sellCurrency(UUID userId, CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) {
        Account senderAccount = accountService.getByUserIdAndCurrency(userId, fromCurrency);
        Account receiverAccount = accountService.getByUserIdAndCurrency(userId, toCurrency);

        BigDecimal currencyValue = graphService.getCurrencyValue(fromCurrency);
        BigDecimal totalPrice = amount.multiply(currencyValue);

        processCurrencyTransaction(userId, senderAccount, receiverAccount, amount, totalPrice);
    }

    @Transactional
    public void buyCurrency(UUID userId, CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) {
        Account senderAccount = accountService.getByUserIdAndCurrency(userId, fromCurrency);
        Account receiverAccount = accountService.getByUserIdAndCurrency(userId, toCurrency);

        BigDecimal currencyValue = graphService.getCurrencyValue(toCurrency);
        BigDecimal totalPrice = amount.multiply(currencyValue);

        processCurrencyTransaction(userId, senderAccount, receiverAccount, totalPrice, amount);
    }

    private void processCurrencyTransaction(UUID userId, Account senderAccount, Account receiverAccount,
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


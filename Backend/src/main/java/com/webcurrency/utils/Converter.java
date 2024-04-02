package com.webcurrency.utils;

import com.webcurrency.dto.AccountResponse;
import com.webcurrency.dto.CurrencyRateResponse;
import com.webcurrency.dto.TransactionResponse;
import com.webcurrency.dto.UserResponse;
import com.webcurrency.models.account.Account;
import com.webcurrency.models.currency.CurrencyRate;
import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.models.account.Transaction;
import com.webcurrency.models.user.User;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Converter {
    private ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        List<AccountResponse> accounts = user.getAccounts().stream()
                .map(this::convertToAccountResponse)
                .toList();
        userResponse.setAccounts(accounts);
        return userResponse;
    }

    public AccountResponse convertToAccountResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    public CurrencyRateResponse convertToCurrencyRateResponse(CurrencyRate currencyRate) {
        return modelMapper.map(currencyRate, CurrencyRateResponse.class);
    }

    public TransactionResponse convertToTransactionResponse(Transaction transaction) {
        TransactionResponse transactionResponse = modelMapper.map(transaction, TransactionResponse.class);

        CurrencyType receiverCurrencyType = transaction.getReceiverAccount().getCurrencyType();
        CurrencyType senderCurrencyType = transaction.getSenderAccount().getCurrencyType();

        String currencyType = determineCurrencyType(receiverCurrencyType, senderCurrencyType);
        transactionResponse.setCurrencyType(currencyType);
        transactionResponse.setOperationType(determineOperationType(transaction));

        return transactionResponse;
    }

    private String determineCurrencyType(CurrencyType receiverCurrencyType, CurrencyType senderCurrencyType) {
        if (receiverCurrencyType.equals(CurrencyType.RUB)) {
            return senderCurrencyType.toString();
        } else {
            return receiverCurrencyType.toString();
        }
    }

    private String determineOperationType(Transaction transaction) {
        CurrencyType receiverCurrencyType = transaction.getReceiverAccount().getCurrencyType();

        if (!receiverCurrencyType.equals(CurrencyType.RUB)) {
            return "BUY";
        } else {
            return "SELL";
        }
    }

}

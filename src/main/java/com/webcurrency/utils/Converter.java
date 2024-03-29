package com.webcurrency.utils;

import com.webcurrency.dto.AccountResponse;
import com.webcurrency.dto.CurrencyRateResponse;
import com.webcurrency.dto.UserResponse;
import com.webcurrency.models.account.Account;
import com.webcurrency.models.account.CurrencyRate;
import com.webcurrency.models.user.User;
import com.webcurrency.services.UserService;
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
    public Converter(ModelMapper modelMapper, UserService userService) {
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

}

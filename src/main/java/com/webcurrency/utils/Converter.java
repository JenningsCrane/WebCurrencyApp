package com.webcurrency.utils;

import com.webcurrency.dto.AccountResponse;
import com.webcurrency.dto.UserResponse;
import com.webcurrency.model.Account;
import com.webcurrency.model.User;
import com.webcurrency.service.UserService;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Converter {
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public Converter(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
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

}

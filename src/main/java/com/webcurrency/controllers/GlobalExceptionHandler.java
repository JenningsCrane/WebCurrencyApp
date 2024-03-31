package com.webcurrency.controllers;

import com.webcurrency.exceptions.AccountNotFoundException;
import com.webcurrency.exceptions.LowBalanceException;
import com.webcurrency.exceptions.UnsupportedCurrencyType;
import com.webcurrency.exceptions.UserNotCreatedException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Tag(name = "Обработка ошибок")
public class GlobalExceptionHandler {
    @ExceptionHandler({UsernameNotFoundException.class, AccountNotFoundException.class})
    private ResponseEntity<HttpStatus> handleException(UsernameNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler({UserNotCreatedException.class, UnsupportedCurrencyType.class})
    private ResponseEntity<HttpStatus> handleException() {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY); // 422
    }

    @ExceptionHandler(LowBalanceException.class)
    public ResponseEntity<HttpStatus> handleLowBalanceException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
    }
}

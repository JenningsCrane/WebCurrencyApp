package com.webcurrency.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO дописать исключения и отловить все
    @ExceptionHandler(UsernameNotFoundException.class)
    private HttpStatus handleException(UsernameNotFoundException e) {
        return HttpStatus.NOT_FOUND;
    }
}

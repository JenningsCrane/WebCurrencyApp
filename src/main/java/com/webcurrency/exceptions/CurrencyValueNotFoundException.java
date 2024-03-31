package com.webcurrency.exceptions;

public class CurrencyValueNotFoundException extends RuntimeException {
    public CurrencyValueNotFoundException(String message) {
        super(message);
    }
}

package com.webcurrency.exceptions;

public class UnsupportedCurrencyType extends RuntimeException{
    public UnsupportedCurrencyType(String message) {
        super(message);
    }
}

package com.webcurrency.model.account;

public enum CurrencyType {
    DIRHAM,
    RUBLES,
    YUAN;

    public static CurrencyType fromString(String currencyString) {
        return switch (currencyString) {
            case "R01230" -> DIRHAM;
            case "R01375" -> YUAN;
            default -> throw new IllegalArgumentException("Неверное значение для типа валюты");
        };
    }
}

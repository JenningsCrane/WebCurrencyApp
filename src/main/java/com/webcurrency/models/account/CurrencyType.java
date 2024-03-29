package com.webcurrency.models.account;

import com.webcurrency.exceptions.UnsupportedCurrencyType;

public enum CurrencyType {
    AED("R01230"),
    CNY("R01375"),
    RUB("R00000");

    private final String code;

    CurrencyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CurrencyType fromString(String currencyString) {
        return switch (currencyString) {
            case "R01230" -> AED;
            case "R01375" -> CNY;
            default -> throw new UnsupportedCurrencyType("Неподдерживаемый тип валюты");
        };
    }
}

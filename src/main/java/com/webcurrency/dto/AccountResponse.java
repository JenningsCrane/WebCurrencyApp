package com.webcurrency.dto;

import com.webcurrency.model.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private CurrencyType currency;
    private double balance;
}

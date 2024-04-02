package com.webcurrency.dto;

import com.webcurrency.models.currency.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private CurrencyType currency;
    private BigDecimal balance;
}

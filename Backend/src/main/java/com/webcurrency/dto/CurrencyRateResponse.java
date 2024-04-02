package com.webcurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateResponse {
    private boolean isAI;
    private Date date;
    private BigDecimal value;
}

package com.webcurrency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Date date;
    private String currencyType;
    private BigDecimal amount;
    private String operationType;
}

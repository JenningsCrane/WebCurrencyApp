package com.webcurrency.controllers;

import com.webcurrency.models.account.CurrencyType;
import com.webcurrency.services.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Транзакции")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/buy")
    public ResponseEntity<HttpStatus> buyCurrency(@RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                  @RequestParam("toCurrency") CurrencyType toCurrency,
                                                  @RequestParam("amount") BigDecimal amount) {
        // Логика покупки валюты
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<HttpStatus> sellCurrency(@RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                   @RequestParam("toCurrency") CurrencyType toCurrency,
                                                   @RequestParam("amount") BigDecimal amount) {
        // Логика продажи валюты
        return ResponseEntity.ok(HttpStatus.OK);
    }
}




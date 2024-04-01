package com.webcurrency.controllers;

import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.services.TransactionService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Покупка и продажа валюты")
public class CurrencyPurchaseController {
    private final TransactionService transactionService;
    @PostMapping("/buy")
    @Operation(summary = "Покупка валюты")
    public ResponseEntity<HttpStatus> buyCurrency(@RequestParam("userId") UUID userId,
                                                  @RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                  @RequestParam("toCurrency") CurrencyType toCurrency,
                                                  @RequestParam("amount") BigDecimal amount) {
        transactionService.buyCurrency(userId, fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продажа валюты")
    public ResponseEntity<HttpStatus> sellCurrency(@RequestParam("userId") UUID userId,
                                                   @RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                   @RequestParam("toCurrency") CurrencyType toCurrency,
                                                   @RequestParam("amount") BigDecimal amount) {
        transactionService.sellCurrency(userId, fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

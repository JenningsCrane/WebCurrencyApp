package com.webcurrency.controllers;

import com.webcurrency.dto.TransactionResponse;
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
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Транзакции")
public class TransactionController {
    private final TransactionService transactionService;
    private final Converter converter;

    @GetMapping("/{userId}")
    @Operation(summary = "Получение всех транзакций пользователя")
    public List<TransactionResponse> getAllTransactions(@PathVariable Long userId) {
        return transactionService.findAllByUserId(userId).stream()
                .map(converter::convertToTransactionResponse)
                .toList();
    }

    @PostMapping("/buy")
    @Operation(summary = "Покупка валюты")
    public ResponseEntity<HttpStatus> buyCurrency(@RequestParam("userId") Long userId,
                                                  @RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                  @RequestParam("toCurrency") CurrencyType toCurrency,
                                                  @RequestParam("amount") BigDecimal amount) {
        transactionService.buyCurrency(userId, fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/sell")
    @Operation(summary = "Продажа валюты")
    public ResponseEntity<HttpStatus> sellCurrency(@RequestParam("userId") Long userId,
                                                   @RequestParam("fromCurrency") CurrencyType fromCurrency,
                                                   @RequestParam("toCurrency") CurrencyType toCurrency,
                                                   @RequestParam("amount") BigDecimal amount) {
        transactionService.sellCurrency(userId, fromCurrency, toCurrency, amount);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}




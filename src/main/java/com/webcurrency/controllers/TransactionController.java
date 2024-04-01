package com.webcurrency.controllers;

import com.webcurrency.dto.TransactionResponse;
import com.webcurrency.services.TransactionService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(name = "Транзакции")
public class TransactionController {
    private final TransactionService transactionService;
    private final Converter converter;

    @GetMapping("/{userId}")
    @Operation(summary = "Получение всех транзакций пользователя")
    public List<TransactionResponse> getAllTransactions(@PathVariable UUID userId) {
        return transactionService.findAllByUserId(userId).stream()
                .map(converter::convertToTransactionResponse)
                .toList();
    }
}




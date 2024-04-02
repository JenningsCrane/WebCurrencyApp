package com.webcurrency.controllers;

import com.webcurrency.dto.CurrencyRateResponse;
import com.webcurrency.services.GraphService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.webcurrency.models.currency.CurrencyType.AED;
import static com.webcurrency.models.currency.CurrencyType.CNY;

@RestController
@RequestMapping("graph")
@RequiredArgsConstructor
@Tag(name = "Графики курса валют")
public class GraphController {
    private final GraphService graphService;
    private final Converter converter;

    @Operation(summary = "Данные для отрисовки графика по юаню")
    @GetMapping("/rub-cny")
    public List<CurrencyRateResponse> getCnyData(@RequestParam(value = "start_date", required = false) String startDate,
                                                 @RequestParam(value = "end_date", required = false) String endDate) {
        return graphService.updateCurrencyRatesIfNeeded(startDate, endDate, CNY).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }

    @Operation(summary = "Данные для отрисовки графика по дирхаму")
    @GetMapping("/rub-aed")
    public List<CurrencyRateResponse> getAedData(@RequestParam(value = "start_date", required = false) String startDate,
                                                 @RequestParam(value = "end_date", required = false) String endDate) {
        return graphService.updateCurrencyRatesIfNeeded(startDate, endDate, AED).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }
}

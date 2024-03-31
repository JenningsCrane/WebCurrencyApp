package com.webcurrency.controllers;

import com.webcurrency.dto.CurrencyRateResponse;
import com.webcurrency.services.CurrencyRateService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.webcurrency.models.account.CurrencyType.AED;
import static com.webcurrency.models.account.CurrencyType.CNY;

@RestController
@RequestMapping("graph")
@RequiredArgsConstructor
@Tag(name = "Графики курса валют")
public class GraphController {
    private final CurrencyRateService rateService;
    private final Converter converter;

    @GetMapping("/rub-cny")
    public List<CurrencyRateResponse> getCnyData(@RequestParam(value = "start_date", required = false) String startDate,
                                                 @RequestParam(value = "end_date", required = false) String endDate) {
        return rateService.updateCurrencyRatesIfNeeded(startDate, endDate, CNY).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }


    @GetMapping("/rub-aed")
    public List<CurrencyRateResponse> getAedData(@RequestParam(value = "start_date", required = false) String startDate,
                                                 @RequestParam(value = "end_date", required = false) String endDate) {
        return rateService.updateCurrencyRatesIfNeeded(startDate, endDate, AED).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }


    // TODO добавь пост запросы на создание заявки
    // TODO добавь контроллер для отображения истории операций
}

package com.webcurrency.controllers;

import com.webcurrency.dto.CurrencyRateResponse;
import com.webcurrency.services.CurrencyRateService;
import com.webcurrency.utils.Converter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static com.webcurrency.models.account.CurrencyType.AED;
import static com.webcurrency.models.account.CurrencyType.CNY;

@RestController
@RequestMapping("currencies")
@RequiredArgsConstructor
@Tag(name = "Курс валют")
public class CurrencyController {
    private final CurrencyRateService rateService;
    private final Converter converter;

    @GetMapping("/rub-cny")
    public List<CurrencyRateResponse> getCnyData(@RequestParam("start_date") String startDate,
                                                 @RequestParam("end_date") String endDate) throws ParseException {
        return rateService.updateCurrencyRatesIfNeeded(startDate, endDate, CNY.getCode()).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }


    @GetMapping("/rub-aed")
    public List<CurrencyRateResponse> getAedData(@RequestParam("start_date") String startDate,
                                                 @RequestParam("end_date") String endDate) throws ParseException {
        return rateService.updateCurrencyRatesIfNeeded(startDate, endDate, AED.getCode()).stream()
                .map(converter::convertToCurrencyRateResponse)
                .toList();
    }

    // TODO добавь пост запросы на создание заявки
    // TODO добавь контроллер для отображения истории операций
}

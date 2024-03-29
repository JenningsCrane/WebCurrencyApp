package com.webcurrency.service;

import com.webcurrency.model.account.CurrencyRate;
import com.webcurrency.parser.XMLParser;
import com.webcurrency.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final XMLParser xmlParser;
    private final CurrencyRateRepository currencyRateRepository;

    public void updateCurrencyRatesIfNeeded(String date1, String date2, String val_nm_rq) throws ParseException {

        String url;

        Date startDate = getParse(date1);
        Date endDate = getParse(date2);

        boolean dataExists = currencyRateRepository.existsByDateBetween(startDate, endDate);

        if (!dataExists) {
            url = buildURL(date1, date2, val_nm_rq);

            // Парсинг данных по полученному URL
            List<CurrencyRate> currencyRates = xmlParser.parseCurrencyRates(url);

            // Ваш код для сохранения или обновления данных в базе данных
            saveOrUpdateCurrencyRates(currencyRates);
        } else {
            List<CurrencyRate> existingRates = currencyRateRepository.findByDateBetween(startDate, endDate);
            String lastExistingDate = String.valueOf(existingRates.get(existingRates.size() - 1).getDate());

            url = buildURL(lastExistingDate, date2, val_nm_rq);

            List<CurrencyRate> newRates = xmlParser.parseCurrencyRates(url);

            saveOrUpdateCurrencyRates(newRates);
        }

    }

    private static String buildURL(String date1, String date2, String val_nm_rq) {
        return String.format("https://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", date1, date2, val_nm_rq);
    }

    private Date getParse(String data) throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(data);
    }

    private void saveOrUpdateCurrencyRates(List<CurrencyRate> currencyRates) {
        for (CurrencyRate currencyRate : currencyRates) {
            currencyRateRepository.saveAndFlush(currencyRate);
        }
    }
}

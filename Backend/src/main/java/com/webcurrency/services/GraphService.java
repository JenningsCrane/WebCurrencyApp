package com.webcurrency.services;

import com.webcurrency.exceptions.CurrencyValueNotFoundException;
import com.webcurrency.models.currency.CurrencyRate;
import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.utils.XmlParser;
import com.webcurrency.repositories.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphService {
    private final XmlParser xmlParser;
    private final CurrencyRateRepository currencyRateRepository;

    public BigDecimal getCurrencyValue(CurrencyType currencyType) {
        return getLatestCurrencyRateByType(currencyType).getValue();
    }

    @Cacheable(value = "listCurrencyRate")
    public List<CurrencyRate> updateCurrencyRatesIfNeeded(String date1, String date2, CurrencyType currencyType) {
        try {
            String url;
            List<CurrencyRate> rates;
            Date startDate = getParse(date1);
            Date endDate = getParse(date2);

            boolean dataExists = currencyRateRepository.existsByDateBetweenAndCurrency(startDate, endDate, currencyType);

            if (!dataExists) {
                url = buildURL(date1, date2, currencyType.getCode());
                rates = xmlParser.parseCurrencyRates(url);
                saveOrUpdateCurrencyRates(rates);
            } else {
                rates = currencyRateRepository.findByDateBetweenAndCurrency(startDate, endDate, currencyType);
                Date lastDate = rates.get(rates.size() - 1).getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String lastExistingDate = dateFormat.format(lastDate);
                url = buildURL(lastExistingDate, date2, currencyType.getCode());
                List<CurrencyRate> newRates = xmlParser.parseCurrencyRates(url);
                saveOrUpdateCurrencyRates(newRates);
            }
            return rates;
        } catch (ParseException e) {
            throw new CurrencyValueNotFoundException("В ЦБ нет релевантных данных по курсу данной валюты");
        }


    }

    private static String buildURL(String date1, String date2, String currencyValue) {
        return String.format("https://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", date1, date2, currencyValue);
    }

    private Date getParse(String data) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(data);
    }

    private void saveOrUpdateCurrencyRates(List<CurrencyRate> currencyRates) {
        for (CurrencyRate currencyRate : currencyRates) {
            currencyRateRepository.saveAndFlush(currencyRate);
        }
    }

    public CurrencyRate getLatestCurrencyRateByType(CurrencyType currencyType) {
        List<CurrencyRate> currencyRates = currencyRateRepository.findLatestByCurrency(currencyType);
        if (currencyRates.isEmpty()) {
            throw new CurrencyValueNotFoundException("В ЦБ нет релевантных данных по курсу данной валюты");
        } else {
            return currencyRates.get(0);
        }
    }
}

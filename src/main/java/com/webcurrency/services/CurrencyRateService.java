package com.webcurrency.services;

import com.webcurrency.models.account.CurrencyRate;
import com.webcurrency.models.account.CurrencyType;
import com.webcurrency.utils.XmlParser;
import com.webcurrency.repositories.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final XmlParser xmlParser;
    private final CurrencyRateRepository currencyRateRepository;

    public List<CurrencyRate> updateCurrencyRatesIfNeeded(String date1, String date2, String currencyValue) throws ParseException {
        String url;
        List<CurrencyRate> rates;

        Date startDate = getParse(date1);
        Date endDate = getParse(date2);

        boolean dataExists = currencyRateRepository.existsByDateBetweenAndCurrency(startDate, endDate, CurrencyType.fromString(currencyValue));

        if (!dataExists) {
            url = buildURL(date1, date2, currencyValue);
            rates = xmlParser.parseCurrencyRates(url);
            saveOrUpdateCurrencyRates(rates);
        } else {
            rates = currencyRateRepository.findByDateBetweenAndCurrency(startDate, endDate, CurrencyType.fromString(currencyValue));

            Date lastDate = rates.get(rates.size() - 1).getDate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String lastExistingDate = dateFormat.format(lastDate);

            url = buildURL(lastExistingDate, date2, currencyValue);

            List<CurrencyRate> newRates = xmlParser.parseCurrencyRates(url);

            saveOrUpdateCurrencyRates(newRates);
        }

        return rates;
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
}

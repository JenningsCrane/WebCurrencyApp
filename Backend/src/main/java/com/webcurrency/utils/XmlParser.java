package com.webcurrency.utils;

import com.webcurrency.models.currency.CurrencyRate;
import com.webcurrency.models.currency.CurrencyType;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class XmlParser {

    public List<CurrencyRate> parseCurrencyRates(String url) throws ParseException {
        List<CurrencyRate> currencyRates = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            Element root = doc.getDocumentElement();
            NodeList recordList = root.getElementsByTagName("Record");

            for (int i = 0; i < recordList.getLength(); i++) {
                Element record = (Element) recordList.item(i);
                String dateString = record.getAttribute("Date").replace(".", "-");
                String currencyType = record.getAttribute("Id");
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                BigDecimal value = new BigDecimal(record.getElementsByTagName("Value").item(0).getTextContent().replace(",", "."));
                CurrencyRate currencyRate = CurrencyRate.builder()
                        .isAI(false)
                        .currency(CurrencyType.fromString(currencyType))
                        .date(date)
                        .value(value)
                        .build();
                currencyRates.add(currencyRate);
            }
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
        return currencyRates;
    }
}
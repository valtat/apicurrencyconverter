package com.example.apicurrencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyDataService {

    @Value("${EXCHANGE_RATES_API_KEY:default-api-key}")
    private String apiKey;

    private static final String BASE_URL = "http://api.exchangeratesapi.io/v1/";

    private final DatabaseService databaseService;
    private final boolean initEnabled;

    @Autowired
    public CurrencyDataService(DatabaseService databaseService) {
        this(databaseService, true);
    }

    public CurrencyDataService(DatabaseService databaseService, boolean initEnabled) {
        this.databaseService = databaseService;
        this.initEnabled = initEnabled;
    }

    @PostConstruct
    public void init() {
        if (initEnabled && databaseService.getCurrencyDataEntityCount() == 0) {
            fetchAndStoreCurrencyData();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchAndStoreCurrencyData() {
        String endpoint = "latest";
        String url = BASE_URL + endpoint + "?access_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        CurrencyData currencyData = restTemplate.getForObject(url, CurrencyData.class);
        if (currencyData != null) {
            databaseService.saveCurrencyData(currencyData);
        }
    }

    double calculateConversion(String baseCurrency, double amount, String fromCurrency, String toCurrency,
            double fromCurrencyRate, double toCurrencyRate) {

        BigDecimal result;

        if (fromCurrency.equals(baseCurrency)) {
            result = BigDecimal.valueOf(amount * toCurrencyRate);
            return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        if (toCurrency.equals(baseCurrency)) {
            result = BigDecimal.valueOf(amount / fromCurrencyRate);
            return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }

        result = BigDecimal.valueOf(amount * (toCurrencyRate / fromCurrencyRate));
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        CurrencyData data = databaseService.getLatestCurrencyData();
        String baseCurrency = data.getBase();
        Double fromCurrencyRate = data.getRates().get(fromCurrency);
        Double toCurrencyRate = data.getRates().get(toCurrency);

        if (fromCurrencyRate == null || toCurrencyRate == null) {
            throw new IllegalArgumentException("Invalid currency code");
        }

        return calculateConversion(baseCurrency, amount, fromCurrency, toCurrency, fromCurrencyRate,
                toCurrencyRate);
    }
}
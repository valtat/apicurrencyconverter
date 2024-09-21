package com.example.apicurrencyconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import javax.annotation.PostConstruct;

@Service
public class CurrencyDataService {

    @Value("${EXCHANGE_RATES_API_KEY}")
    private String apiKey;

    private static final String BASE_URL = "http://api.exchangeratesapi.io/v1/";

    private final CurrencyDataRepository repository;

    public CurrencyDataService(CurrencyDataRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            fetchAndStoreCurrencyData();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run once a day at midnight
    public void fetchAndStoreCurrencyData() {
        String endpoint = "latest";
        String url = BASE_URL + endpoint + "?access_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        CurrencyData currencyData = restTemplate.getForObject(url, CurrencyData.class);
        if (currencyData != null) {
            repository.save(currencyData);
        }
    }

    public CurrencyData getLatestCurrencyData() {
        return repository.findTopByOrderByTimestampDesc();
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        CurrencyData latestData = getLatestCurrencyData();
        Map<String, Double> rates = latestData.getRates();
        String baseCurrency = latestData.getBase();

        if (rates.containsKey(fromCurrency) && rates.containsKey(toCurrency)) {
            if (fromCurrency.equals(baseCurrency)) {
                return amount * rates.get(toCurrency);
            } else if (toCurrency.equals(baseCurrency)) {
                return amount / rates.get(fromCurrency);
            } else {
                return amount * (rates.get(toCurrency) / rates.get(fromCurrency));
            }
        } else {
            throw new IllegalArgumentException("Invalid currency code");
        }
    }
}
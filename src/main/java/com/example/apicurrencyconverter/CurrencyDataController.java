package com.example.apicurrencyconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://valeriot.z16.web.core.windows.net/"})
public class CurrencyDataController {

    @Value("${EXCHANGE_RATES_API_KEY:defaultApiKey}")
    private String apiKey;

    private static final String BASE_URL = "http://api.exchangeratesapi.io/v1/";

    @GetMapping("/api/currency")
    public CurrencyData getCurrencyData() {
        String endpoint = "latest";
        String url = BASE_URL + endpoint + "?access_key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        CurrencyData currencyData = restTemplate.getForObject(url, CurrencyData.class);
        return currencyData != null ? currencyData : new CurrencyData();
    }
}
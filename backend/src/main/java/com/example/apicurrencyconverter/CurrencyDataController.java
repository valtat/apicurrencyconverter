package com.example.apicurrencyconverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "https://valeriot.z16.web.core.windows.net/", "https://kind-island-02bf6e003.6.azurestaticapps.net/" })
public class CurrencyDataController {

    private final CurrencyDataService currencyDataService;
    private final DatabaseService databaseService;

    public CurrencyDataController(CurrencyDataService currencyDataService, DatabaseService databaseService) {
        this.currencyDataService = currencyDataService;
        this.databaseService = databaseService;
    }

    @GetMapping("/api/currency")
    public CurrencyData getCurrencyData() {
        return databaseService.getLatestCurrencyData();
    }

    @GetMapping("/api/change-currency")
    public double changeCurrency(@RequestParam double amount, @RequestParam String from, @RequestParam String to) {
        return currencyDataService.convertCurrency(amount, from, to);
    }
}
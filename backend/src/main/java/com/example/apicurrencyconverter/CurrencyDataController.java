package com.example.apicurrencyconverter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "https://valeriot.z16.web.core.windows.net/"})
public class CurrencyDataController {

    private final CurrencyDataService currencyDataService;

    public CurrencyDataController(CurrencyDataService currencyDataService) {
        this.currencyDataService = currencyDataService;
    }

    @GetMapping("/api/currency")
    public CurrencyData getCurrencyData() {
        return currencyDataService.getLatestCurrencyData();
    }

    @GetMapping("/api/change-currency")
    public double changeCurrency(@RequestParam double amount, @RequestParam String from, @RequestParam String to) {
        return currencyDataService.convertCurrency(amount, from, to);
    }
}
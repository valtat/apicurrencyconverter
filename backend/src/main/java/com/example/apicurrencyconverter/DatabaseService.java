package com.example.apicurrencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final CurrencyDataRepository repository;

    @Autowired
    public DatabaseService(CurrencyDataRepository repository) {
        this.repository = repository;
    }

    public CurrencyData getLatestCurrencyData() {
        return repository.findTopByOrderByTimestampDesc();
    }

    public long getCurrencyDataEntityCount() {
        return repository.count();
    }

    public void saveCurrencyData(CurrencyData currencyData) {
        repository.save(currencyData);
    }

}

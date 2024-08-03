package com.example.apicurrencyconverter;

import java.util.Map;

public record CurrencyData(boolean success, long timestamp, String base, String date, Map<String, Double> rates) {

    public CurrencyData() {
        this(false, 0, "", "", Map.of());
    }

}
package com.example.apicurrencyconverter;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyDataRepository extends MongoRepository<CurrencyData, String> {
    CurrencyData findTopByOrderByTimestampDesc();
}
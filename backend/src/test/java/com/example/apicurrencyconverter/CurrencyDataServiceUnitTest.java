package com.example.apicurrencyconverter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ApicurrencyconverterApplication.class)
@AutoConfigureMockMvc

public class CurrencyDataServiceUnitTest {

    @Autowired
    private CurrencyDataService currencyDataService;

    @MockBean
    private CurrencyDataRepository currencyRepository;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private DatabaseService databaseService;

    @Test
    public void testConvertCurrency() {
        CurrencyData currencyData = new CurrencyData();
        currencyData.setBase("EUR");
        currencyData.setRates(new HashMap<>() {
            {
                put("USD", 1.116814);
                put("JPY", 158.829409);
                put("EUR", 1.0);
            }
        });

        when(databaseService.getLatestCurrencyData()).thenReturn(currencyData);

        double result = currencyDataService.convertCurrency(100, "EUR", "USD");
        assertEquals(111.68, result, 0.001);

        result = currencyDataService.convertCurrency(100, "USD", "JPY");
        assertEquals(14221.65, result, 0.001);

        result = currencyDataService.convertCurrency(100, "JPY", "EUR");
        assertEquals(0.63, result, 0.001);
    }

    @Test
    public void testConvertCurrencyInvalidCurrency() {
        CurrencyData currencyData = new CurrencyData();
        currencyData.setBase("EUR");
        currencyData.setRates(new HashMap<>() {
            {
                put("USD", 1.116814);
                put("JPY", 158.829409);
                put("EUR", 1.0);
            }
        });

        when(databaseService.getLatestCurrencyData()).thenReturn(currencyData);

        assertThrows(IllegalArgumentException.class, () -> {
            currencyDataService.convertCurrency(100, "FOO", "USD");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            currencyDataService.convertCurrency(100, "USD", "BAR");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            currencyDataService.convertCurrency(100, "", "USD");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            currencyDataService.convertCurrency(100, "USD", null);
        });
    }

    @Test
    public void testCalculateConversion() {
        double result = currencyDataService.calculateConversion("EUR", 10, "EUR",
                "USD", 1.0, 1.112);
        assertEquals(11.12, result, 0.001);

        result = currencyDataService.calculateConversion("EUR", 100, "USD", "JPY",
                1.112, 159.459);
        assertEquals(14339.84, result, 0.001);

        result = currencyDataService.calculateConversion("EUR", 1000, "JPY", "EUR",
                159.459, 1.0);
        assertEquals(6.27, result, 0.001);
    }

}

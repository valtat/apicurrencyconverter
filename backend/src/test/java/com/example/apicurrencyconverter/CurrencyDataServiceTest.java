package com.example.apicurrencyconverter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;
import java.util.Map;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ApicurrencyconverterApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")

public class CurrencyDataServiceTest {

    @Autowired
    private CurrencyDataService currencyDataService;

    @MockBean 
    private CurrencyDataRepository currencyRepository;
    
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void fetchAndStoreCurrencyData() {
        CurrencyData currencyData = new CurrencyData();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.112);
        rates.put("JPY", 159.459);
        currencyData.setRates(rates);
        currencyData.setDate("2024-09-23");
        currencyData.setBase("EUR");
        currencyData.setSuccess(true);
        currencyData.setTimestamp(1727113744);
        currencyData.setId("55f1ab27e5f62c3d30fbd4f6");

        when(restTemplate.getForObject(anyString(), eq(CurrencyData.class))).thenReturn(currencyData);
        currencyDataService.fetchAndStoreCurrencyData();
        
        org.junit.Assert.assertNotNull(currencyData);
        System.out.println("Date: " + currencyData.getDate());
        org.junit.Assert.assertTrue(currencyData.getRates().size() > 0);

        org.junit.Assert.assertEquals("EUR", currencyData.getBase()); 
        org.junit.Assert.assertEquals("2024-09-23", currencyData.getDate());
        org.junit.Assert.assertEquals(1727113744, currencyData.getTimestamp());
        org.junit.Assert.assertTrue(currencyData.isSuccess());
        org.junit.Assert.assertNotNull(currencyData.getId());

    }

    // TODO: Test currency convert logic and database operations

    
}

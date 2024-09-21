package com.example.apicurrencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ApicurrencyconverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApicurrencyconverterApplication.class, args);
	}

}

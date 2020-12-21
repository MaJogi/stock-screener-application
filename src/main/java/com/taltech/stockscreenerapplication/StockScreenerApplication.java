package com.taltech.stockscreenerapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class StockScreenerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StockScreenerApplication.class, args);
    }
}

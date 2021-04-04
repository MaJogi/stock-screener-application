package com.taltech.stockscreenerapplication;

import com.taltech.stockscreenerapplication.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class StockScreenerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StockScreenerApplication.class, args);
    }
}

package com.taltech.stockscreenerapplication;

import com.taltech.stockscreenerapplication.controller.CompanyDimensionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockScreenerApplicationTests {

    @Autowired
    private CompanyDimensionController companyDimensionController;

    @Test
    void contextLoads() throws Exception {
        assertThat(companyDimensionController).isNotNull();
    }

}

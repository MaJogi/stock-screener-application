package com.taltech.stockscreenerapplication;

import com.taltech.stockscreenerapplication.controller.CompanyDimensionController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockScreenerApplicationTests {

    @Autowired
    private CompanyDimensionController companyDimensionController;

    @Test
    void contextLoads() {
        Assert.assertNotNull(companyDimensionController);
    }

}

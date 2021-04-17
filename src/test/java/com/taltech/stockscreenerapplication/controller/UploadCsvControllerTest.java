package com.taltech.stockscreenerapplication.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

// https://www.baeldung.com/junit-5

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UploadCsvControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    /* Exception testing example (remove later, when already used)
    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @Test
    void assertThrowsException() {
        String str = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Integer.valueOf(str);
        });
    }

     */

    @Test
    void saveFormDataToDb() {
    }

    @Test
    void getDefaultPage() {
    }

    @Test
    void testing() {
    }

    @Test
    void getCompanyRawIncomeStats() {
    }

    @Test
    void getCompanySpecificTimeRawIncomeStats() {
    }

    @Test
    void getCompanyRawCashflowStats() {
    }

    @Test
    void getCompanySpecificTimeRawBalanceStat() {
    }

    @Test
    void getCompanyRawBalanceStats() {
    }

    @Test
    void getCompanySpecificTimeRawCashflowStat() {
    }
}
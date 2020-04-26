package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyDimensionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getCompaniesTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/company", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertNotNull(response.getBody());
    }

    @Test
    void getCompanyTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "company/TAL1T", CompanyDimension.class);
        assertNotNull(companyDimension);
    }

    @Test
    void getCompanyIsNullTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "company/AAAAA", CompanyDimension.class);
        assertNull(companyDimension);
    }
}
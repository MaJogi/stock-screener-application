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
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/companies", String.class);
        assertThat("The response status to query company by id is not 200.", response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getCompanyTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "companies/TAL1T", CompanyDimension.class);
        assertNotNull(companyDimension.getTicker_id(), "The request does not return company by id.");
    }

    @Test
    void getCompanyIsNullTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "companies/AAAAA", CompanyDimension.class);
        assertNull(companyDimension.getTicker_id(), "The response for non-existing company should be null.");
    }
}
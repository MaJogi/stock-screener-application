package com.taltech.stockscreenerapplication.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// https://www.baeldung.com/junit-5

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CsvControllerTest {

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

    /* Example of existing tests for CompanyDimensionController
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

     */


    @Test
    void getDefaultPage() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/csv/", String.class);
        assertThat("Insert reason here.", response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void readAndSaveToDbStatusOK() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/csv/readAndSave/TKM1T/tkm-2017_q2_CSV_modified_by_frontend", String.class);
        assertThat("Insert reason here", response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void readAndSaveToDbBadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/csv/readAndSave/TKM1T/nonExisting", String.class);
        assertThat("Insert reason here", response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

    }

}
package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void setup() {
        //populateDatabaseWithRealData();
    }

    @Test
    void getCompanySpecificTimeRawIncomeStats() {

        // Pre population of database before.
        restTemplate.getForEntity(getRootUrl() + "csv/readAndSave/TKM1T/tkm-2017_q2_CSV_modified_by_frontend",
                String.class);


        IncomeStatRaw incomeStatRaw = restTemplate
                .getForObject(getRootUrl() + "companies/TKM1T/income/Q2 2016", IncomeStatRaw.class);
        assertNotNull(incomeStatRaw.getIncome_stat_raw_id(),
                "The request does not return income statement by id.");
    }

    @Test
    void getCompanySpecificTimeRawIncomeStats2() {

        // Pre population of database before.
        restTemplate.getForEntity(getRootUrl() + "csv/readAndSave/TKM1T/tkm-2017_q2_CSV_modified_by_frontend",
                String.class);

        IncomeStatRaw incomeStatRaw = restTemplate
                .getForObject(getRootUrl() + "companies/TKM1T/income/Q2 2017", IncomeStatRaw.class);
        assertNotNull(incomeStatRaw.getIncome_stat_raw_id(),
                "The request does not return income statement by id.");
    }


    @Test
    void getCompaniesTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/companies", String.class);
        assertThat("The response status to query company by id is not 200.",
                response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getCompanyTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "companies/TAL1T",
                CompanyDimension.class);
        assertNotNull(companyDimension.getTicker_id(), "The request does not return company by id.");
    }

    @Test
    void getCompanyIsNullTest() {
        CompanyDimension companyDimension = restTemplate.getForObject(getRootUrl() + "companies/AAAAA",
                CompanyDimension.class);
        assertNull(companyDimension.getTicker_id(), "The response for non-existing company should be null.");
    }

    @Test
    void getCompanyRawIncomeStatsOK() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "companies/TAL1T/incomeStatements",
                String.class);
        assertThat("Insert reason here",
                response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getCompanyRawCashflowStatsOK() {
        ResponseEntity<String> response = restTemplate
                .getForEntity(getRootUrl() + "companies/TAL1T/cashflowStatements", String.class);
        assertThat("Insert reason here",
                response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getCompanySpecificTimeRawCashflowStat() {
        // Pre population of database before.
        restTemplate.getForEntity(getRootUrl() + "csv/readAndSave/TKM1T/tkm-2017_q2_CSV_modified_by_frontend",
                String.class);

        CashflowStatRaw cashflowStatRaw = restTemplate
                .getForObject(getRootUrl() + "companies/TKM1T/cashflow/6 months 2017", CashflowStatRaw.class);
        assertNotNull(cashflowStatRaw.getCashflow_stat_raw_id(),
                "The request does not return cashflow statement by id.");
    }

    @Test
    void getCompanyRawBalanceStatsOK() {
        ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "companies/TAL1T/balanceStatements",
                String.class);
        assertThat("Insert reason here",
                response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    void getCompanySpecificTimeRawBalanceStat() {
        // Pre population of database before.
        restTemplate.getForEntity(getRootUrl() + "csv/readAndSave/TKM1T/tkm-2017_q2_CSV_modified_by_frontend",
                String.class);

        BalanceStatRaw balanceStatRaw = restTemplate
                .getForObject(getRootUrl() + "companies/TKM1T/balance/30.06.2017/", BalanceStatRaw.class);
        assertNotNull(balanceStatRaw.getBalance_stat_raw_id(),
                "The request does not return balance statement by id.");
    }
}
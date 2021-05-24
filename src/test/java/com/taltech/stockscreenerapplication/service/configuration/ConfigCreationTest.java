package com.taltech.stockscreenerapplication.service.configuration;

import com.taltech.stockscreenerapplication.model.statement.configuration.BalanceStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.CashflowStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.BalanceMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.CashflowMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.IncomeMappingRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfigCreationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigCreationTest.class);
    private static ConfigCreation configCreation;

    @BeforeEach
    void init() {
        LOGGER.info("@BeforeEach - executes before each test method in this class");
        configCreation = new ConfigCreation();
    }

    @Test
    void setIncomeConfigObjectFields() {
        IncomeStatConfig testIncomeConfig = new IncomeStatConfig();
        IncomeMappingRequest incomeMappingRequest = new IncomeMappingRequest();
        incomeMappingRequest.setCompanyConfigCollectionId(1L);
        incomeMappingRequest.setDateFrom("Q1 2014");
        incomeMappingRequest.setDateTo("Q4 2018");
        incomeMappingRequest.setCostAndExpenses("35000");

        ConfigCreation.setIncomeConfigObjectFields(testIncomeConfig, incomeMappingRequest);

        Assert.assertEquals(testIncomeConfig.getCompany_config_collection_id(), incomeMappingRequest.getCompanyConfigCollectionId());
        Assert.assertEquals(testIncomeConfig.getDateFrom(), incomeMappingRequest.getDateFrom());
        Assert.assertEquals(testIncomeConfig.getDateTo(), incomeMappingRequest.getDateTo());
        Assert.assertEquals(testIncomeConfig.getCostAndExpenses(), incomeMappingRequest.getCostAndExpenses());

    }

    @Test
    void setCashflowConfigObjectFields() {
        CashflowStatConfig testCashflowConfig = new CashflowStatConfig();
        CashflowMappingRequest cashflowMappingRequest = new CashflowMappingRequest();
        cashflowMappingRequest.setCompanyConfigCollectionId(1L);
        cashflowMappingRequest.setDateFrom("Q1 2014");
        cashflowMappingRequest.setDateTo("Q4 2018");
        cashflowMappingRequest.setAccountsReceivables("7800");

        ConfigCreation.setCashflowConfigObjectFields(testCashflowConfig, cashflowMappingRequest);

        Assert.assertEquals(testCashflowConfig.getCompany_config_collection_id(), cashflowMappingRequest.getCompanyConfigCollectionId());
        Assert.assertEquals(testCashflowConfig.getDateFrom(), cashflowMappingRequest.getDateFrom());
        Assert.assertEquals(testCashflowConfig.getDateTo(), cashflowMappingRequest.getDateTo());
        Assert.assertEquals(testCashflowConfig.getAccountsReceivables(), cashflowMappingRequest.getAccountsReceivables());

    }

    @Test
    void setBalanceConfigObjectFields() {
        BalanceStatConfig testBalanceConfig = new BalanceStatConfig();
        BalanceMappingRequest balanceMappingRequest = new BalanceMappingRequest();
        balanceMappingRequest.setCompanyConfigCollectionId(1L);
        balanceMappingRequest.setDateFrom("Q1 2014");
        balanceMappingRequest.setDateTo("Q4 2018");
        balanceMappingRequest.setCommonStock("9500");

        ConfigCreation.setBalanceConfigObjectFields(testBalanceConfig, balanceMappingRequest);

        Assert.assertEquals(testBalanceConfig.getCompany_config_collection_id(), balanceMappingRequest.getCompanyConfigCollectionId());
        Assert.assertEquals(testBalanceConfig.getDateFrom(), balanceMappingRequest.getDateFrom());
        Assert.assertEquals(testBalanceConfig.getDateTo(), balanceMappingRequest.getDateTo());
        Assert.assertEquals(testBalanceConfig.getCommonStock(), balanceMappingRequest.getCommonStock());

    }
}
package com.taltech.stockscreenerapplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.service.formulaCreation.FormulaObjCreationHelper;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.BalanceMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.CashflowMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.IncomeMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/formulaCreation")
public class FormulaObjCreationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaObjCreationController.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    // Purely used for testing purposes.
    @GetMapping("/createMappingFor/{ticker}/forTestingPurposes")
    public ResponseEntity<MessageResponse> test(@PathVariable String ticker) {

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long firstEvenConfigurationId = 1;
        testIncomeConfig.setCompany_config_collection_id(firstEvenConfigurationId);

        testIncomeConfig.setDateFrom("2016");
        testIncomeConfig.setDateTo(null);
        testIncomeConfig.setRevenue("#Revenue + #Other operating income");
        testIncomeConfig.setCostOfRevenue("#Cost of sales");
        testIncomeConfig.setGrossProfit("#Revenue - #Cost of sales");
        testIncomeConfig.setGrossProfitRatio("(#Revenue - #Cost of sales) / (#Revenue + #Other operating income)");
        testIncomeConfig.setRAndDexpenses(null);
        testIncomeConfig.setGeneralAndAdminExpenses("#Other Operating Expenses + #Staff costs");
        testIncomeConfig.setSellingAndMarketingExpenses(null);
        testIncomeConfig.setOtherExpenses("#Other expenses");
        testIncomeConfig.setOperatingExpenses("#Other Operating Expenses + #Staff costs + #Other expenses + #Depreciation");
        testIncomeConfig.setCostAndExpenses("#Cost of sales + (#Other Operating Expenses + #Staff costs + #Other expenses + #Depriciation, amortization and impairment losses)");
        testIncomeConfig.setInterestExpense("#Finance costs");
        testIncomeConfig.setDepricationAndAmortization("#Depriciation, amortization and impairment losses");
        testIncomeConfig.setEbitda("#Operating profit + #Depriciation, amortization and impairment losses");
        testIncomeConfig.setEbitdaRatio("(#Operating profit + #Depreciation …) / (#Revenue + #Other operating income)");
        testIncomeConfig.setOperatingIncome("#Operating profit");
        testIncomeConfig.setOperatingIncomeRatio("#Operating profit / (#Revenue + #Other operating income)");
        testIncomeConfig.setTotalOtherIncomeExpensesNet(null);
        testIncomeConfig.setIncomeBeforeTax("#NET PROFIT FOR THE FINANCIAL YEAR");
        testIncomeConfig.setIncomeBeforeTaxRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
        testIncomeConfig.setIncomeTaxExpense(null);
        testIncomeConfig.setNetIncome("#NET PROFIT FOR THE FINANCIAL YEAR");
        testIncomeConfig.setNetIncomeRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
        testIncomeConfig.setEps("#Basic and diluted earnings per share");
        testIncomeConfig.setEpsDiluted("#Basic and diluted earnings per share");
        testIncomeConfig.setWeightedAverageShsOut(null);
        testIncomeConfig.setWeightedAverageShsOutDil(null);

        company.getIncomeConfigurations().add(testIncomeConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas for income, balance, cashflow statements are done"));
    }

    @PostMapping(value = "/createMappingFor/{ticker}/income",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleIncomeStatementMapping(@PathVariable String ticker,
//                                                           @RequestBody final BalanceMappingRequest balanceRequest,
//                                                           @RequestBody final CashflowMappingRequest cashflowRequest,
                                                                        @RequestBody final IncomeMappingRequest incomeRequest) {

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        //CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        //CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long id = 1;
        testIncomeConfig.setCompany_config_collection_id(id);

        FormulaObjCreationHelper.setIncomeConfigObjectFields(testIncomeConfig, incomeRequest);

        company.getIncomeConfigurations().add(testIncomeConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas for income statement are done"));
    }

    @PostMapping(value = "/createMappingFor/{ticker}/cashflow",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleCashflowStatementMapping(@PathVariable String ticker,
                                                                          @RequestBody final CashflowMappingRequest cashflowRequest) {

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();

        long id = 1;
        testCashflowConfig.setCompany_config_collection_id(id);

        FormulaObjCreationHelper.setCashflowConfigObjectFields(testCashflowConfig, cashflowRequest);

        company.getCashflowConfigurations().add(testCashflowConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas cashflow statement are done"));
    }

    @PostMapping(value = "/createMappingFor/{ticker}/balance",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleBalanceStatementMapping(@PathVariable String ticker,
                                                                          @RequestBody final BalanceMappingRequest balanceRequest) {

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long id = 1;
        testBalanceConfig.setCompany_config_collection_id(id);

        FormulaObjCreationHelper.setBalanceConfigObjectFields(testBalanceConfig, balanceRequest);

        company.getBalanceConfigurations().add(testBalanceConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas balance statement are done"));
    }

    @PostMapping(value = "/createMappingFor/{ticker}",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> allStatementsAsOneMapping(@PathVariable String ticker,
                                                                     @RequestBody final ObjectNode json) {
        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long id = 1;
        testIncomeConfig.setCompany_config_collection_id(id);

        JsonNode incomeNode = json.get("incomeRequest");
        JsonNode cashflowNode = json.get("cashflowRequest");
        JsonNode balanceNode = json.get("balanceRequest");

        FormulaObjCreationHelper.setJsonValuesToIncomeConfig(testIncomeConfig, incomeNode);
        FormulaObjCreationHelper.setJsonValuesToCashflowConfig(testCashflowConfig, cashflowNode);
        FormulaObjCreationHelper.setJsonValuesToBalanceConfig(testBalanceConfig, balanceNode);

        company.getIncomeConfigurations().add(testIncomeConfig);
        company.getCashflowConfigurations().add(testCashflowConfig);
        company.getBalanceConfigurations().add(testBalanceConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas for income, balance, cashflow statements are done"));
    }
}


package com.taltech.stockscreenerapplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
import com.taltech.stockscreenerapplication.util.payload.request.IncomeMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/map")
public class RawToStandardMappingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @GetMapping("/createMappingFor/{ticker}/test")
    public ResponseEntity<MessageResponse> test(@PathVariable String ticker) {

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long firstEvenConfigurationId = 1;
        testIncomeConfig.setCompany_config_collection_id(firstEvenConfigurationId);

        testIncomeConfig.setDateFrom("2016");
        testIncomeConfig.setDateTo(null);
        testIncomeConfig.setRevenue("#Revenue + #Other_operating_income");
        testIncomeConfig.setCostOfRevenue("#Cost of sales");
        testIncomeConfig.setGrossProfit("#Revenue - #Cost_of_sales");
        testIncomeConfig.setGrossProfitRatio("(#Revenue - #Cost_of_sales) / (#Revenue + #Other_operating_income)");
        testIncomeConfig.setRAndDexpenses(null);
        testIncomeConfig.setGeneralAndAdminExpenses("#Other_Operating_Expenses + #Staff_costs");
        testIncomeConfig.setSellingAndMarketingExpenses(null);
        testIncomeConfig.setOtherExpenses("#Other_expenses");
        testIncomeConfig.setOperatingExpenses("#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depreciation");
        testIncomeConfig.setCostAndExpenses("#Cost_of_sales + (#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depriciation,_amortization_and_impairment_losses)");
        testIncomeConfig.setInterestExpense("#Finance_costs");
        testIncomeConfig.setDepricationAndAmortization("#Depriciation,_amortization_and_impairment losses");
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
    public ResponseEntity<MessageResponse> singleStatementMapping(@PathVariable String ticker,
//                                                           @RequestBody final BalanceMappingRequest balanceRequest,
//                                                           @RequestBody final CashflowMappingRequest cashflowRequest,
                                                           @RequestBody final IncomeMappingRequest incomeRequest) {

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long id = 1;
        testIncomeConfig.setCompany_config_collection_id(id);

//        testIncomeConfig.setDateFrom("2016");
//        testIncomeConfig.setDateTo(null);
//        testIncomeConfig.setRevenue("#Revenue + #Other_operating_income");
//        testIncomeConfig.setCostOfRevenue("#Cost of sales");
//        testIncomeConfig.setGrossProfit("#Revenue - #Cost_of_sales");
//        testIncomeConfig.setGrossProfitRatio("(#Revenue - #Cost_of_sales) / (#Revenue + #Other_operating_income)");
//        testIncomeConfig.setRAndDexpenses(null);
//        testIncomeConfig.setGeneralAndAdminExpenses("#Other_Operating_Expenses + #Staff_costs");
//        testIncomeConfig.setSellingAndMarketingExpenses(null);
//        testIncomeConfig.setOtherExpenses("#Other_expenses");
//        testIncomeConfig.setOperatingExpenses("#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depreciation");
//        testIncomeConfig.setCostAndExpenses("#Cost_of_sales + (#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depriciation,_amortization_and_impairment_losses)");
//        testIncomeConfig.setInterestExpense("#Finance_costs");
//        testIncomeConfig.setDepricationAndAmortization("#Depriciation,_amortization_and_impairment losses");
//        testIncomeConfig.setEbitda("#Operating profit + #Depriciation, amortization and impairment losses");
//        testIncomeConfig.setEbitdaRatio("(#Operating profit + #Depreciation …) / (#Revenue + #Other operating income)");
//        testIncomeConfig.setOperatingIncome("#Operating profit");
//        testIncomeConfig.setOperatingIncomeRatio("#Operating profit / (#Revenue + #Other operating income)");
//        testIncomeConfig.setTotalOtherIncomeExpensesNet(null);
//        testIncomeConfig.setIncomeBeforeTax("#NET PROFIT FOR THE FINANCIAL YEAR");
//        testIncomeConfig.setIncomeBeforeTaxRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
//        testIncomeConfig.setIncomeTaxExpense(null);
//        testIncomeConfig.setNetIncome("#NET PROFIT FOR THE FINANCIAL YEAR");
//        testIncomeConfig.setNetIncomeRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
//        testIncomeConfig.setEps("#Basic and diluted earnings per share");
//        testIncomeConfig.setEpsDiluted("#Basic and diluted earnings per share");
//        testIncomeConfig.setWeightedAverageShsOut(null);
//        testIncomeConfig.setWeightedAverageShsOutDil(null);
        LOGGER.info(incomeRequest.getDateFrom());
        testIncomeConfig.setDateFrom(incomeRequest.getDateFrom());
        testIncomeConfig.setDateTo(incomeRequest.getDateTo());
        testIncomeConfig.setRevenue(incomeRequest.getRevenue());
        testIncomeConfig.setCostOfRevenue(incomeRequest.getCostOfRevenue());
        testIncomeConfig.setGrossProfit(incomeRequest.getGrossProfit());
        testIncomeConfig.setGrossProfitRatio(incomeRequest.getGrossProfitRatio());
        testIncomeConfig.setRAndDexpenses(incomeRequest.getRAndDexpenses());
        testIncomeConfig.setGeneralAndAdminExpenses(incomeRequest.getGeneralAndAdminExpenses());
        testIncomeConfig.setSellingAndMarketingExpenses(incomeRequest.getSellingAndMarketingExpenses());
        testIncomeConfig.setOtherExpenses(incomeRequest.getOtherExpenses());
        testIncomeConfig.setOperatingExpenses(incomeRequest.getOperatingExpenses());
        testIncomeConfig.setCostAndExpenses(incomeRequest.getCostAndExpenses());
        testIncomeConfig.setInterestExpense(incomeRequest.getInterestExpense());
        testIncomeConfig.setDepricationAndAmortization(incomeRequest.getDepricationAndAmortization());
        testIncomeConfig.setEbitda(incomeRequest.getEbitda());
        testIncomeConfig.setEbitdaRatio(incomeRequest.getEbitdaRatio());
        testIncomeConfig.setOperatingIncome(incomeRequest.getOperatingIncome());
        testIncomeConfig.setOperatingIncomeRatio(incomeRequest.getOperatingIncomeRatio());
        testIncomeConfig.setTotalOtherIncomeExpensesNet(incomeRequest.getTotalOtherIncomeExpensesNet());
        testIncomeConfig.setIncomeBeforeTax(incomeRequest.getIncomeBeforeTax());
        testIncomeConfig.setIncomeBeforeTaxRatio(incomeRequest.getIncomeBeforeTaxRatio());
        testIncomeConfig.setIncomeTaxExpense(incomeRequest.getIncomeTaxExpense());
        testIncomeConfig.setNetIncome(incomeRequest.getNetIncome());
        testIncomeConfig.setNetIncomeRatio(incomeRequest.getNetIncomeRatio());
        testIncomeConfig.setEps(incomeRequest.getEps());
        testIncomeConfig.setEpsDiluted(incomeRequest.getEpsDiluted());
        testIncomeConfig.setWeightedAverageShsOut(incomeRequest.getWeightedAverageShsOut());
        testIncomeConfig.setWeightedAverageShsOutDil(incomeRequest.getWeightedAverageShsOutDil());

        company.getIncomeConfigurations().add(testIncomeConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas for income, balance, cashflow statements are done"));
    }

    @PostMapping(value = "/createMappingFor/{ticker}",  produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> mapping(@PathVariable String ticker,
                                                   @RequestBody final ObjectNode json) {

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();
        CompanyCashflowStatFormulaConfig testCashflowConfig = new CompanyCashflowStatFormulaConfig();
        CompanyBalanceStatFormulaConfig testBalanceConfig = new CompanyBalanceStatFormulaConfig();

        long id = 1;
        testIncomeConfig.setCompany_config_collection_id(id);

        JsonNode incomeNode = json.get("incomeRequest");
        JsonNode cashflowNode = json.get("cashflowRequest");
        JsonNode balanceNode = json.get("balanceRequest");
//        testIncomeConfig.setDateFrom("2016");
//        testIncomeConfig.setDateTo(null);
//        testIncomeConfig.setRevenue("#Revenue + #Other_operating_income");
//        testIncomeConfig.setCostOfRevenue("#Cost of sales");
//        testIncomeConfig.setGrossProfit("#Revenue - #Cost_of_sales");
//        testIncomeConfig.setGrossProfitRatio("(#Revenue - #Cost_of_sales) / (#Revenue + #Other_operating_income)");
//        testIncomeConfig.setRAndDexpenses(null);
//        testIncomeConfig.setGeneralAndAdminExpenses("#Other_Operating_Expenses + #Staff_costs");
//        testIncomeConfig.setSellingAndMarketingExpenses(null);
//        testIncomeConfig.setOtherExpenses("#Other_expenses");
//        testIncomeConfig.setOperatingExpenses("#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depreciation");
//        testIncomeConfig.setCostAndExpenses("#Cost_of_sales + (#Other_Operating_Expenses + #Staff_costs + #Other_expenses + #Depriciation,_amortization_and_impairment_losses)");
//        testIncomeConfig.setInterestExpense("#Finance_costs");
//        testIncomeConfig.setDepricationAndAmortization("#Depriciation,_amortization_and_impairment losses");
//        testIncomeConfig.setEbitda("#Operating profit + #Depriciation, amortization and impairment losses");
//        testIncomeConfig.setEbitdaRatio("(#Operating profit + #Depreciation …) / (#Revenue + #Other operating income)");
//        testIncomeConfig.setOperatingIncome("#Operating profit");
//        testIncomeConfig.setOperatingIncomeRatio("#Operating profit / (#Revenue + #Other operating income)");
//        testIncomeConfig.setTotalOtherIncomeExpensesNet(null);
//        testIncomeConfig.setIncomeBeforeTax("#NET PROFIT FOR THE FINANCIAL YEAR");
//        testIncomeConfig.setIncomeBeforeTaxRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
//        testIncomeConfig.setIncomeTaxExpense(null);
//        testIncomeConfig.setNetIncome("#NET PROFIT FOR THE FINANCIAL YEAR");
//        testIncomeConfig.setNetIncomeRatio("#NET PROFIT FOR THE FINANCIAL YEAR / (#Revenue + #Other operating income)");
//        testIncomeConfig.setEps("#Basic and diluted earnings per share");
//        testIncomeConfig.setEpsDiluted("#Basic and diluted earnings per share");
//        testIncomeConfig.setWeightedAverageShsOut(null);
//        testIncomeConfig.setWeightedAverageShsOutDil(null);
        //LOGGER.info(incomeNode.get("dateFrom").asText());

        setValuesToIncomeConfig(testIncomeConfig, incomeNode);
        setValuesToCashflowConfig(testCashflowConfig, cashflowNode);
        setValuesToBalanceConfig(testBalanceConfig, balanceNode);

        company.getIncomeConfigurations().add(testIncomeConfig);
        company.getCashflowConfigurations().add(testCashflowConfig);
        company.getBalanceConfigurations().add(testBalanceConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if formulas for income, balance, cashflow statements are done"));
    }

    // .asText() can also be used. But instead of null, value be returned as String with text "null"
    public void setValuesToIncomeConfig(CompanyIncomeStatFormulaConfig testIncomeConfig, JsonNode incomeNode) {
        testIncomeConfig.setDateFrom(incomeNode.get("dateFrom").textValue());
        testIncomeConfig.setDateTo(incomeNode.get("dateTo").textValue());
        testIncomeConfig.setRevenue(incomeNode.get("revenue").textValue());
        testIncomeConfig.setCostOfRevenue(incomeNode.get("costOfRevenue").textValue());
        testIncomeConfig.setGrossProfit(incomeNode.get("grossProfit").textValue());
        testIncomeConfig.setGrossProfitRatio(incomeNode.get("grossProfitRatio").textValue());
        testIncomeConfig.setRAndDexpenses(incomeNode.get("rAndDexpenses").textValue());
        testIncomeConfig.setGeneralAndAdminExpenses(incomeNode.get("generalAndAdminExpenses").textValue());
        testIncomeConfig.setSellingAndMarketingExpenses(incomeNode.get("sellingAndMarketingExpenses").textValue());
        testIncomeConfig.setOtherExpenses(incomeNode.get("otherExpenses").textValue());
        testIncomeConfig.setOperatingExpenses(incomeNode.get("operatingExpenses").textValue());
        testIncomeConfig.setCostAndExpenses(incomeNode.get("costAndExpenses").textValue());
        testIncomeConfig.setInterestExpense(incomeNode.get("interestExpense").textValue());
        testIncomeConfig.setDepricationAndAmortization(incomeNode.get("depricationAndAmortization").textValue());
        testIncomeConfig.setEbitda(incomeNode.get("ebitda").textValue());
        testIncomeConfig.setEbitdaRatio(incomeNode.get("ebitdaRatio").textValue());
        testIncomeConfig.setOperatingIncome(incomeNode.get("operatingIncome").textValue());
        testIncomeConfig.setOperatingIncomeRatio(incomeNode.get("operatingIncomeRatio").textValue());
        testIncomeConfig.setTotalOtherIncomeExpensesNet(incomeNode.get("totalOtherIncomeExpensesNet").textValue());
        testIncomeConfig.setIncomeBeforeTax(incomeNode.get("incomeBeforeTax").textValue());
        testIncomeConfig.setIncomeBeforeTaxRatio(incomeNode.get("incomeBeforeTaxRatio").textValue());
        testIncomeConfig.setIncomeTaxExpense(incomeNode.get("incomeTaxExpense").textValue());
        testIncomeConfig.setNetIncome(incomeNode.get("netIncome").textValue());
        testIncomeConfig.setNetIncomeRatio(incomeNode.get("netIncomeRatio").textValue());
        testIncomeConfig.setEps(incomeNode.get("eps").textValue());
        testIncomeConfig.setEpsDiluted(incomeNode.get("epsDiluted").textValue());
        testIncomeConfig.setWeightedAverageShsOut(incomeNode.get("weightedAverageShsOut").textValue());
        testIncomeConfig.setWeightedAverageShsOutDil(incomeNode.get("weightedAverageShsOutDil").textValue());
    }
    public void setValuesToCashflowConfig(CompanyCashflowStatFormulaConfig testCashflowConfig, JsonNode cashflowNode) {
        testCashflowConfig.setDateFrom(cashflowNode.get("dateFrom").textValue());
        testCashflowConfig.setDateTo(cashflowNode.get("dateTo").textValue());
        testCashflowConfig.setNetIncome(cashflowNode.get("netIncome").textValue());
        testCashflowConfig.setDepriciationAndAmortization(cashflowNode.get("depriciationAndAmortization").textValue());
        testCashflowConfig.setStockBasedCompensation(cashflowNode.get("stockBasedCompensation").textValue());
        testCashflowConfig.setChangeInWorkingCapital(cashflowNode.get("changeInWorkingCapital").textValue());
        testCashflowConfig.setAccountsReceivables(cashflowNode.get("accountsReceivables").textValue());
        testCashflowConfig.setInventory(cashflowNode.get("inventory").textValue());
        testCashflowConfig.setAccountsPayments(cashflowNode.get("accountsPayments").textValue());
        testCashflowConfig.setOtherNonCashItems(cashflowNode.get("otherNonCashItems").textValue());
        testCashflowConfig.setNetCashProvidedByOperatingActivities(cashflowNode.get("netCashProvidedByOperatingActivities").textValue());
        testCashflowConfig.setInvestmentsInPropertyPlantAndEquipment(cashflowNode.get("investmentsInPropertyPlantAndEquipment").textValue());
        testCashflowConfig.setAcquisitionsNet(cashflowNode.get("acquisitionsNet").textValue());
        testCashflowConfig.setPurchasesOfInvestments(cashflowNode.get("purchasesOfInvestments").textValue());
        testCashflowConfig.setSalesMaturitiesOfInvestments(cashflowNode.get("salesMaturitiesOfInvestments").textValue());
        testCashflowConfig.setOtherInvestingActivities(cashflowNode.get("otherInvestingActivities").textValue());
        testCashflowConfig.setNetCashUsedForInvestingActivities(cashflowNode.get("netCashUsedForInvestingActivities").textValue());
        testCashflowConfig.setDebtRepayment(cashflowNode.get("debtRepayment").textValue());
    }

    public void setValuesToBalanceConfig(CompanyBalanceStatFormulaConfig testBalanceConfig, JsonNode balanceNode) {
        testBalanceConfig.setDateFrom(balanceNode.get("dateFrom").textValue());
        testBalanceConfig.setDateTo(balanceNode.get("dateTo").textValue());
        testBalanceConfig.setCashAndCashEquivalents(balanceNode.get("cashAndCashEquivalents").textValue());
        testBalanceConfig.setShortTermInvestments(balanceNode.get("shortTermInvestments").textValue());
        testBalanceConfig.setCashAndShortTermInvestments(balanceNode.get("cashAndShortTermInvestments").textValue());
        testBalanceConfig.setNetReceivables(balanceNode.get("netReceivables").textValue());
        testBalanceConfig.setInventory(balanceNode.get("inventory").textValue());
        testBalanceConfig.setOtherCurrentAssets(balanceNode.get("otherCurrentAssets").textValue());
        testBalanceConfig.setTotalCurrentAssets(balanceNode.get("totalCurrentAssets").textValue());
        testBalanceConfig.setPropertyPlantEquipmentAssets(balanceNode.get("propertyPlantEquipmentAssets").textValue());
        testBalanceConfig.setGoodwill(balanceNode.get("goodwill").textValue());
        testBalanceConfig.setIntangibleAssets(balanceNode.get("intangibleAssets").textValue());
        testBalanceConfig.setGoodwillAndIntangibleAssets(balanceNode.get("goodwillAndIntangibleAssets").textValue());
        testBalanceConfig.setLongTermInvestmets(balanceNode.get("longTermInvestmets").textValue());
        testBalanceConfig.setTaxAssets(balanceNode.get("taxAssets").textValue());
        testBalanceConfig.setOtherNonCurrentAssets(balanceNode.get("otherNonCurrentAssets").textValue());
        testBalanceConfig.setTotalNonCurrentAssets(balanceNode.get("totalNonCurrentAssets").textValue());
        testBalanceConfig.setOtherAssets(balanceNode.get("otherAssets").textValue());
        testBalanceConfig.setTotalAssets(balanceNode.get("totalAssets").textValue());
        testBalanceConfig.setAccountPayables(balanceNode.get("accountPayables").textValue());
        testBalanceConfig.setShortTermDebt(balanceNode.get("shortTermDebt").textValue());
        testBalanceConfig.setTaxPayables(balanceNode.get("taxPayables").textValue());
        testBalanceConfig.setDeferredRevenue(balanceNode.get("deferredRevenue").textValue());
        testBalanceConfig.setOtherCurrentLiabilities(balanceNode.get("otherCurrentLiabilities").textValue());
        testBalanceConfig.setTotalCurrentLiabilities(balanceNode.get("totalCurrentLiabilities").textValue());
        testBalanceConfig.setLongTermDebt(balanceNode.get("longTermDebt").textValue());
        testBalanceConfig.setDeferredRevenueNonCurrent(balanceNode.get("deferredRevenueNonCurrent").textValue());
        testBalanceConfig.setDeferredTaxLiabilitiesNonCurrent(balanceNode.get("deferredTaxLiabilitiesNonCurrent").textValue());
        testBalanceConfig.setOtherNonCurrentLiabilities(balanceNode.get("otherNonCurrentLiabilities").textValue());
        testBalanceConfig.setTotalNonCurrentLiabilities(balanceNode.get("totalNonCurrentLiabilities").textValue());
        testBalanceConfig.setOtherLiabilities(balanceNode.get("otherLiabilities").textValue());
        testBalanceConfig.setTotalLiabilities(balanceNode.get("totalLiabilities").textValue());
        testBalanceConfig.setCommonStock(balanceNode.get("commonStock").textValue());
        testBalanceConfig.setRetainedEarnings(balanceNode.get("retainedEarnings").textValue());
        testBalanceConfig.setAccumulatedOtherComprehensiveIncomeLoss(balanceNode.get("accumulatedOtherComprehensiveIncomeLoss").textValue());
        testBalanceConfig.setOtherTotalStockholdersEquity(balanceNode.get("otherTotalStockholdersEquity").textValue());
        testBalanceConfig.setTotalStockholdersEquity(balanceNode.get("totalStockholdersEquity").textValue());
        testBalanceConfig.setTotalLiabilitiesAndStockHoldersEquity(balanceNode.get("totalLiabilitiesAndStockHoldersEquity").textValue());
        testBalanceConfig.setTotalInvestments(balanceNode.get("totalInvestments").textValue());
        testBalanceConfig.setTotalDebt(balanceNode.get("totalDebt").textValue());
        testBalanceConfig.setNetDebt(balanceNode.get("netDebt").textValue());
    }
}

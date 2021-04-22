package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/map")
public class RawToStandardMappingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @GetMapping("/createMappingFor/{ticker}")
    public ResponseEntity<MessageResponse> readAndSaveToDb(@PathVariable String ticker) {

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));

        CompanyIncomeStatFormulaConfig testIncomeConfig = new CompanyIncomeStatFormulaConfig();

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

}

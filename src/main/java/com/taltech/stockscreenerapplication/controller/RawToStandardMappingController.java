package com.taltech.stockscreenerapplication.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.CompanyIncomeStatFormulaConfigRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
import com.taltech.stockscreenerapplication.service.formulaToValue.StandardStatementCreationHelper;
import com.taltech.stockscreenerapplication.util.payload.request.IncomeMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/map")
public class RawToStandardMappingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;
    private CompanyIncomeStatFormulaConfigRepository companyIncomeStatFormulaConfigRepository;

    @GetMapping("/createMappingFor/{ticker}/createIncomeStatementFromFormula")
    public ResponseEntity<MessageResponse> incomeMappingFromFormula(@PathVariable String ticker) {
        LOGGER.info("1");
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();


        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));
        LOGGER.info("2");
        List<IncomeStatRaw> rawIncomeStatements = company.getIncomeRawStatements();
        LOGGER.info("3");
        /*  Finding incomeStatement with right date/period  */
        //        Predicate<Client> hasSameNameAsOneUser =
        //                c -> users.stream().anyMatch(u -> u.getName().equals(c.getName()));
        //
        //        return clients.stream()
        //                .filter(hasSameNameAsOneUser)
        //                .collect(Collectors.toList());

        List<Attribute> attributesWithValues = rawIncomeStatements.get(0).getAttributes();
        LOGGER.info("4");
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        LOGGER.info("5");
        StandardEvaluationContext stContext  = new StandardEvaluationContext(incomeStatement); // sulgudes on see, mis objekti jaoks need # tehakse

        // stContext.setVariable("Revenue", 150);
        // stContext.setVariable("Other_revenue", 10);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        LOGGER.info("6");

        CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig = company.getIncomeConfigurations().get(0);
        SpelExpressionParser parser = new SpelExpressionParser();

        LOGGER.info("7");
        // revenue=#Revenue  + #Other_revenue
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("\\s+", "_"));
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("[aA-zZ]\\s+[aA-zZ]", "_")); // think about that how can you replace spaces without replacing characters before and after space

        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        standardStatementCreationHelper.createIncomeStrings(rightCompanyIncomeConfig);

        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();

        standardStatementCreationHelper.createValuesForIncomeStatementFromFormulas(incomeStandardFieldFormulas, parser, stContext);

        /*
        LOGGER.info(revenueString);
        LOGGER.info("8");
        SpelExpression expression = parser.parseRaw(revenueString);
        LOGGER.info("9");
        expression.getValue(stContext);

        LOGGER.info(costOfRevenueString);
        SpelExpression expression2 = parser.parseRaw(costOfRevenueString);
        expression2.getValue(stContext);

        LOGGER.info(grossProfitString);
        SpelExpression expression3 = parser.parseRaw(grossProfitString);
        expression3.getValue(stContext);

        LOGGER.info(grossProfitRatioString);
        SpelExpression expression4 = parser.parseRaw(grossProfitRatioString);
        expression4.getValue(stContext);

        LOGGER.info(rAndDexpensesString);

        SpelExpression expression5 = parser.parseRaw(rAndDexpensesString);
        expression5.getValue(stContext);

        LOGGER.info(generalAndAdminExpensesString);
        SpelExpression expression6 = parser.parseRaw(generalAndAdminExpensesString);
        expression6.getValue(stContext);

        LOGGER.info(sellingAndMarketingExpensesString);
        SpelExpression expression7 = parser.parseRaw(sellingAndMarketingExpensesString);
        expression7.getValue(stContext);

        LOGGER.info(otherExpensesString);
        SpelExpression expression8 = parser.parseRaw(otherExpensesString);
        expression8.getValue(stContext);

        LOGGER.info(operatingExpensesString);
        SpelExpression expression9 = parser.parseRaw(operatingExpensesString);
        expression9.getValue(stContext);

        LOGGER.info(costAndExpensesString);
        SpelExpression expression10 = parser.parseRaw(costAndExpensesString);
        expression10.getValue(stContext);

        LOGGER.info(interestExpenseString);
        SpelExpression expression11 = parser.parseRaw(interestExpenseString);
        expression11.getValue(stContext);

        LOGGER.info(depricationAndAmortizationString);
        SpelExpression expression12 = parser.parseRaw(depricationAndAmortizationString);
        expression12.getValue(stContext);

        LOGGER.info(ebitdaString);
        SpelExpression expression13 = parser.parseRaw(ebitdaString);
        expression13.getValue(stContext);

        LOGGER.info(ebitdaRatioString);
        SpelExpression expression14 = parser.parseRaw(ebitdaRatioString);
        expression14.getValue(stContext);

        LOGGER.info(operatingIncomeString);
        SpelExpression expression15 = parser.parseRaw(operatingIncomeString);
        expression15.getValue(stContext);

        LOGGER.info(operatingIncomeRatioString);
        SpelExpression expression16 = parser.parseRaw(operatingIncomeRatioString);
        expression16.getValue(stContext);

        LOGGER.info(totalOtherIncomeExpensesNetString);

        SpelExpression expression17 = parser.parseRaw(totalOtherIncomeExpensesNetString);
        expression17.getValue(stContext);

        LOGGER.info(incomeBeforeTaxString);
        SpelExpression expression18 = parser.parseRaw(incomeBeforeTaxString);
        expression18.getValue(stContext);

        LOGGER.info(incomeBeforeTaxRatioString);
        SpelExpression expression19 = parser.parseRaw(incomeBeforeTaxRatioString);
        expression19.getValue(stContext);

        LOGGER.info(incomeTaxExpenseString);

        SpelExpression expression20 = parser.parseRaw(incomeTaxExpenseString);
        expression20.getValue(stContext);

        LOGGER.info(netIncomeString);
        SpelExpression expression21 = parser.parseRaw(netIncomeString);
        expression21.getValue(stContext);

        LOGGER.info(netIncomeRatioString);
        SpelExpression expression22 = parser.parseRaw(netIncomeRatioString);
        expression22.getValue(stContext);

        LOGGER.info(epsString);
        SpelExpression expression23 = parser.parseRaw(epsString);
        expression23.getValue(stContext);

        LOGGER.info(epsDilutedeString);
        SpelExpression expression24 = parser.parseRaw(epsDilutedeString);
        expression24.getValue(stContext);

        LOGGER.info(weightedAverageShsOutString);
        SpelExpression expression25 = parser.parseRaw(weightedAverageShsOutString);
        expression25.getValue(stContext);

        LOGGER.info(weightedAverageShsOutDilString);
        SpelExpression expression26 = parser.parseRaw(weightedAverageShsOutDilString);
        expression26.getValue(stContext);
         */

        /*
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());
        System.out.println("Income statement CostOfRevenue()); is :  " + incomeStatement.getCostOfRevenue());
        System.out.println("Income statement GrossProfit()); is :  " + incomeStatement.getGrossProfit());
        System.out.println("Income statement GrossProfitRatio()); is :  " + incomeStatement.getGrossProfitRatio());
        System.out.println("Income statement RAndDexpenses()); is :  " + incomeStatement.getRAndDexpenses());
        System.out.println("Income statement GeneralAndAdminExpenses()); is :  " + incomeStatement.getGeneralAndAdminExpenses());
        System.out.println("Income statement SellingAndMarketingExpenses()); is :  " + incomeStatement.getSellingAndMarketingExpenses());
        System.out.println("Income statement OtherExpenses()); is :  " + incomeStatement.getOtherExpenses());
        System.out.println("Income statement OperatingExpenses()); is :  " + incomeStatement.getOperatingExpenses());
        System.out.println("Income statement CostAndExpenses()); is :  " + incomeStatement.getCostAndExpenses());
        System.out.println("Income statement InterestExpense()); is :  " + incomeStatement.getInterestExpense());
        System.out.println("Income statement DepricationAndAmortization()); is :  " + incomeStatement.getDepricationAndAmortization());
        System.out.println("Income statement Ebitda()); is :  " + incomeStatement.getEbitda());
        System.out.println("Income statement EbitdaRatio()); is :  " + incomeStatement.getEbitdaRatio());
        System.out.println("Income statement OperatingIncome()); is :  " + incomeStatement.getOperatingIncome());
        System.out.println("Income statement OperatingIncomeRatio()); is :  " + incomeStatement.getOperatingIncomeRatio());
        System.out.println("Income statement TotalOtherIncomeExpensesNet()); is :  " + incomeStatement.getTotalOtherIncomeExpensesNet());
        System.out.println("Income statement IncomeBeforeTax()); is :  " + incomeStatement.getIncomeBeforeTax());
        System.out.println("Income statement IncomeBeforeTaxRatio()); is :  " + incomeStatement.getIncomeBeforeTaxRatio());
        System.out.println("Income statement IncomeTaxExpense()); is :  " + incomeStatement.getIncomeTaxExpense());
        System.out.println("Income statement NetIncome()); is :  " + incomeStatement.getNetIncome());
        System.out.println("Income statement NetIncomeRatio()); is :  " + incomeStatement.getNetIncomeRatio());
        System.out.println("Income statement Eps()); is :  " + incomeStatement.getEps());
        System.out.println("Income statement EpsDiluted()); is :  " + incomeStatement.getEpsDiluted());
        System.out.println("Income statement WeightedAverageShsOut()); is :  " + incomeStatement.getWeightedAverageShsOut());
        System.out.println("Income statement WeightedAverageShsOutDil()); is :  " + incomeStatement.getWeightedAverageShsOutDil());
         */

        LOGGER.info("10");
        company.getIncomeStatements().add(incomeStatement);

        LOGGER.info("11");
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (income) returned. Check if result is correct"));
    }

    @GetMapping("/createMappingFor/{ticker}/createCashflowStatementFromFormula")
    public ResponseEntity<MessageResponse> cashflowMappingFromFormula(@PathVariable String ticker) {
        LOGGER.info("1");
        CashflowStatStandWithValues cashflowStatement = new CashflowStatStandWithValues();


        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));
        LOGGER.info("2");
        List<CashflowStatRaw> rawCashflowStatements = company.getCashflowRawStatements();
        LOGGER.info("3");
        /*  Finding incomeStatement with right date/period  */
        //        Predicate<Client> hasSameNameAsOneUser =
        //                c -> users.stream().anyMatch(u -> u.getName().equals(c.getName()));
        //
        //        return clients.stream()
        //                .filter(hasSameNameAsOneUser)
        //                .collect(Collectors.toList());

        List<Attribute> attributesWithValues = rawCashflowStatements.get(0).getAttributes();
        LOGGER.info("4");
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        LOGGER.info("5");
        StandardEvaluationContext stContext  = new StandardEvaluationContext(cashflowStatement); // sulgudes on see, mis objekti jaoks need # tehakse

        // stContext.setVariable("Revenue", 150);
        // stContext.setVariable("Other_revenue", 10);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        LOGGER.info("6");

        CompanyCashflowStatFormulaConfig rightCompanyCashflowConfig = company.getCashflowConfigurations().get(0);
        SpelExpressionParser parser = new SpelExpressionParser();

        LOGGER.info("7");
        // revenue=#Revenue  + #Other_revenue
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("\\s+", "_"));
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("[aA-zZ]\\s+[aA-zZ]", "_")); // think about that how can you replace spaces without replacing characters before and after space

        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        LOGGER.info("8");
        standardStatementCreationHelper.createCashflowStrings(rightCompanyCashflowConfig);
        LOGGER.info("9");
        List<String> cashflowStandardFieldFormulas = standardStatementCreationHelper.getCashflowStandardFieldFormulas();
        LOGGER.info("10");
        standardStatementCreationHelper.createValuesForCashflowStatementFromFormulas(cashflowStandardFieldFormulas, parser, stContext);
        LOGGER.info("11");
        /*
        LOGGER.info(revenueString);
        LOGGER.info("8");
        SpelExpression expression = parser.parseRaw(revenueString);
        LOGGER.info("9");
        expression.getValue(stContext);

        LOGGER.info(costOfRevenueString);
        SpelExpression expression2 = parser.parseRaw(costOfRevenueString);
        expression2.getValue(stContext);

        LOGGER.info(grossProfitString);
        SpelExpression expression3 = parser.parseRaw(grossProfitString);
        expression3.getValue(stContext);

        LOGGER.info(grossProfitRatioString);
        SpelExpression expression4 = parser.parseRaw(grossProfitRatioString);
        expression4.getValue(stContext);

        LOGGER.info(rAndDexpensesString);

        SpelExpression expression5 = parser.parseRaw(rAndDexpensesString);
        expression5.getValue(stContext);

        LOGGER.info(generalAndAdminExpensesString);
        SpelExpression expression6 = parser.parseRaw(generalAndAdminExpensesString);
        expression6.getValue(stContext);

        LOGGER.info(sellingAndMarketingExpensesString);
        SpelExpression expression7 = parser.parseRaw(sellingAndMarketingExpensesString);
        expression7.getValue(stContext);

        LOGGER.info(otherExpensesString);
        SpelExpression expression8 = parser.parseRaw(otherExpensesString);
        expression8.getValue(stContext);

        LOGGER.info(operatingExpensesString);
        SpelExpression expression9 = parser.parseRaw(operatingExpensesString);
        expression9.getValue(stContext);

        LOGGER.info(costAndExpensesString);
        SpelExpression expression10 = parser.parseRaw(costAndExpensesString);
        expression10.getValue(stContext);

        LOGGER.info(interestExpenseString);
        SpelExpression expression11 = parser.parseRaw(interestExpenseString);
        expression11.getValue(stContext);

        LOGGER.info(depricationAndAmortizationString);
        SpelExpression expression12 = parser.parseRaw(depricationAndAmortizationString);
        expression12.getValue(stContext);

        LOGGER.info(ebitdaString);
        SpelExpression expression13 = parser.parseRaw(ebitdaString);
        expression13.getValue(stContext);

        LOGGER.info(ebitdaRatioString);
        SpelExpression expression14 = parser.parseRaw(ebitdaRatioString);
        expression14.getValue(stContext);

        LOGGER.info(operatingIncomeString);
        SpelExpression expression15 = parser.parseRaw(operatingIncomeString);
        expression15.getValue(stContext);

        LOGGER.info(operatingIncomeRatioString);
        SpelExpression expression16 = parser.parseRaw(operatingIncomeRatioString);
        expression16.getValue(stContext);

        LOGGER.info(totalOtherIncomeExpensesNetString);

        SpelExpression expression17 = parser.parseRaw(totalOtherIncomeExpensesNetString);
        expression17.getValue(stContext);

        LOGGER.info(incomeBeforeTaxString);
        SpelExpression expression18 = parser.parseRaw(incomeBeforeTaxString);
        expression18.getValue(stContext);

        LOGGER.info(incomeBeforeTaxRatioString);
        SpelExpression expression19 = parser.parseRaw(incomeBeforeTaxRatioString);
        expression19.getValue(stContext);

        LOGGER.info(incomeTaxExpenseString);

        SpelExpression expression20 = parser.parseRaw(incomeTaxExpenseString);
        expression20.getValue(stContext);

        LOGGER.info(netIncomeString);
        SpelExpression expression21 = parser.parseRaw(netIncomeString);
        expression21.getValue(stContext);

        LOGGER.info(netIncomeRatioString);
        SpelExpression expression22 = parser.parseRaw(netIncomeRatioString);
        expression22.getValue(stContext);

        LOGGER.info(epsString);
        SpelExpression expression23 = parser.parseRaw(epsString);
        expression23.getValue(stContext);

        LOGGER.info(epsDilutedeString);
        SpelExpression expression24 = parser.parseRaw(epsDilutedeString);
        expression24.getValue(stContext);

        LOGGER.info(weightedAverageShsOutString);
        SpelExpression expression25 = parser.parseRaw(weightedAverageShsOutString);
        expression25.getValue(stContext);

        LOGGER.info(weightedAverageShsOutDilString);
        SpelExpression expression26 = parser.parseRaw(weightedAverageShsOutDilString);
        expression26.getValue(stContext);
         */

        /*
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());
        System.out.println("Income statement CostOfRevenue()); is :  " + incomeStatement.getCostOfRevenue());
        System.out.println("Income statement GrossProfit()); is :  " + incomeStatement.getGrossProfit());
        System.out.println("Income statement GrossProfitRatio()); is :  " + incomeStatement.getGrossProfitRatio());
        System.out.println("Income statement RAndDexpenses()); is :  " + incomeStatement.getRAndDexpenses());
        System.out.println("Income statement GeneralAndAdminExpenses()); is :  " + incomeStatement.getGeneralAndAdminExpenses());
        System.out.println("Income statement SellingAndMarketingExpenses()); is :  " + incomeStatement.getSellingAndMarketingExpenses());
        System.out.println("Income statement OtherExpenses()); is :  " + incomeStatement.getOtherExpenses());
        System.out.println("Income statement OperatingExpenses()); is :  " + incomeStatement.getOperatingExpenses());
        System.out.println("Income statement CostAndExpenses()); is :  " + incomeStatement.getCostAndExpenses());
        System.out.println("Income statement InterestExpense()); is :  " + incomeStatement.getInterestExpense());
        System.out.println("Income statement DepricationAndAmortization()); is :  " + incomeStatement.getDepricationAndAmortization());
        System.out.println("Income statement Ebitda()); is :  " + incomeStatement.getEbitda());
        System.out.println("Income statement EbitdaRatio()); is :  " + incomeStatement.getEbitdaRatio());
        System.out.println("Income statement OperatingIncome()); is :  " + incomeStatement.getOperatingIncome());
        System.out.println("Income statement OperatingIncomeRatio()); is :  " + incomeStatement.getOperatingIncomeRatio());
        System.out.println("Income statement TotalOtherIncomeExpensesNet()); is :  " + incomeStatement.getTotalOtherIncomeExpensesNet());
        System.out.println("Income statement IncomeBeforeTax()); is :  " + incomeStatement.getIncomeBeforeTax());
        System.out.println("Income statement IncomeBeforeTaxRatio()); is :  " + incomeStatement.getIncomeBeforeTaxRatio());
        System.out.println("Income statement IncomeTaxExpense()); is :  " + incomeStatement.getIncomeTaxExpense());
        System.out.println("Income statement NetIncome()); is :  " + incomeStatement.getNetIncome());
        System.out.println("Income statement NetIncomeRatio()); is :  " + incomeStatement.getNetIncomeRatio());
        System.out.println("Income statement Eps()); is :  " + incomeStatement.getEps());
        System.out.println("Income statement EpsDiluted()); is :  " + incomeStatement.getEpsDiluted());
        System.out.println("Income statement WeightedAverageShsOut()); is :  " + incomeStatement.getWeightedAverageShsOut());
        System.out.println("Income statement WeightedAverageShsOutDil()); is :  " + incomeStatement.getWeightedAverageShsOutDil());
         */

        LOGGER.info("12");
        company.getCashflowStatements().add(cashflowStatement);

        LOGGER.info("13");
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (cashflow) returned. Check if result is correct"));
    }

    @GetMapping("/createMappingFor/{ticker}/createBalanceStatementFromFormula")
    public ResponseEntity<MessageResponse> balanceMappingFromFormula(@PathVariable String ticker) {
        LOGGER.info("1");
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company with ticker: " + ticker));
        LOGGER.info("2");
        List<BalanceStatRaw> rawBalanceStatements = company.getBilanceRawStatements();
        LOGGER.info("3");
        /*  Finding incomeStatement with right date/period  */
        //        Predicate<Client> hasSameNameAsOneUser =
        //                c -> users.stream().anyMatch(u -> u.getName().equals(c.getName()));
        //
        //        return clients.stream()
        //                .filter(hasSameNameAsOneUser)
        //                .collect(Collectors.toList());

        List<Attribute> attributesWithValues = rawBalanceStatements.get(0).getAttributes();
        LOGGER.info("4");
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        LOGGER.info("5");
        StandardEvaluationContext stContext  = new StandardEvaluationContext(balanceStatement); // sulgudes on see, mis objekti jaoks need # tehakse

        // stContext.setVariable("Revenue", 150);
        // stContext.setVariable("Other_revenue", 10);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        LOGGER.info("6");

        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = company.getBalanceConfigurations().get(0);
        SpelExpressionParser parser = new SpelExpressionParser();

        LOGGER.info("7");
        // revenue=#Revenue  + #Other_revenue
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("\\s+", "_"));
        //String revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue().replaceAll("[aA-zZ]\\s+[aA-zZ]", "_")); // think about that how can you replace spaces without replacing characters before and after space

        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        LOGGER.info("8");
        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);
        LOGGER.info("9");
        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();
        LOGGER.info("10");
        standardStatementCreationHelper.createValuesForBalanceStatementFromFormulas(balanceStandardFieldFormulas, parser, stContext);
        LOGGER.info("11");
        /*
        LOGGER.info(revenueString);
        LOGGER.info("8");
        SpelExpression expression = parser.parseRaw(revenueString);
        LOGGER.info("9");
        expression.getValue(stContext);

        LOGGER.info(costOfRevenueString);
        SpelExpression expression2 = parser.parseRaw(costOfRevenueString);
        expression2.getValue(stContext);

        LOGGER.info(grossProfitString);
        SpelExpression expression3 = parser.parseRaw(grossProfitString);
        expression3.getValue(stContext);

        LOGGER.info(grossProfitRatioString);
        SpelExpression expression4 = parser.parseRaw(grossProfitRatioString);
        expression4.getValue(stContext);

        LOGGER.info(rAndDexpensesString);

        SpelExpression expression5 = parser.parseRaw(rAndDexpensesString);
        expression5.getValue(stContext);

        LOGGER.info(generalAndAdminExpensesString);
        SpelExpression expression6 = parser.parseRaw(generalAndAdminExpensesString);
        expression6.getValue(stContext);

        LOGGER.info(sellingAndMarketingExpensesString);
        SpelExpression expression7 = parser.parseRaw(sellingAndMarketingExpensesString);
        expression7.getValue(stContext);

        LOGGER.info(otherExpensesString);
        SpelExpression expression8 = parser.parseRaw(otherExpensesString);
        expression8.getValue(stContext);

        LOGGER.info(operatingExpensesString);
        SpelExpression expression9 = parser.parseRaw(operatingExpensesString);
        expression9.getValue(stContext);

        LOGGER.info(costAndExpensesString);
        SpelExpression expression10 = parser.parseRaw(costAndExpensesString);
        expression10.getValue(stContext);

        LOGGER.info(interestExpenseString);
        SpelExpression expression11 = parser.parseRaw(interestExpenseString);
        expression11.getValue(stContext);

        LOGGER.info(depricationAndAmortizationString);
        SpelExpression expression12 = parser.parseRaw(depricationAndAmortizationString);
        expression12.getValue(stContext);

        LOGGER.info(ebitdaString);
        SpelExpression expression13 = parser.parseRaw(ebitdaString);
        expression13.getValue(stContext);

        LOGGER.info(ebitdaRatioString);
        SpelExpression expression14 = parser.parseRaw(ebitdaRatioString);
        expression14.getValue(stContext);

        LOGGER.info(operatingIncomeString);
        SpelExpression expression15 = parser.parseRaw(operatingIncomeString);
        expression15.getValue(stContext);

        LOGGER.info(operatingIncomeRatioString);
        SpelExpression expression16 = parser.parseRaw(operatingIncomeRatioString);
        expression16.getValue(stContext);

        LOGGER.info(totalOtherIncomeExpensesNetString);

        SpelExpression expression17 = parser.parseRaw(totalOtherIncomeExpensesNetString);
        expression17.getValue(stContext);

        LOGGER.info(incomeBeforeTaxString);
        SpelExpression expression18 = parser.parseRaw(incomeBeforeTaxString);
        expression18.getValue(stContext);

        LOGGER.info(incomeBeforeTaxRatioString);
        SpelExpression expression19 = parser.parseRaw(incomeBeforeTaxRatioString);
        expression19.getValue(stContext);

        LOGGER.info(incomeTaxExpenseString);

        SpelExpression expression20 = parser.parseRaw(incomeTaxExpenseString);
        expression20.getValue(stContext);

        LOGGER.info(netIncomeString);
        SpelExpression expression21 = parser.parseRaw(netIncomeString);
        expression21.getValue(stContext);

        LOGGER.info(netIncomeRatioString);
        SpelExpression expression22 = parser.parseRaw(netIncomeRatioString);
        expression22.getValue(stContext);

        LOGGER.info(epsString);
        SpelExpression expression23 = parser.parseRaw(epsString);
        expression23.getValue(stContext);

        LOGGER.info(epsDilutedeString);
        SpelExpression expression24 = parser.parseRaw(epsDilutedeString);
        expression24.getValue(stContext);

        LOGGER.info(weightedAverageShsOutString);
        SpelExpression expression25 = parser.parseRaw(weightedAverageShsOutString);
        expression25.getValue(stContext);

        LOGGER.info(weightedAverageShsOutDilString);
        SpelExpression expression26 = parser.parseRaw(weightedAverageShsOutDilString);
        expression26.getValue(stContext);
         */

        /*
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());
        System.out.println("Income statement CostOfRevenue()); is :  " + incomeStatement.getCostOfRevenue());
        System.out.println("Income statement GrossProfit()); is :  " + incomeStatement.getGrossProfit());
        System.out.println("Income statement GrossProfitRatio()); is :  " + incomeStatement.getGrossProfitRatio());
        System.out.println("Income statement RAndDexpenses()); is :  " + incomeStatement.getRAndDexpenses());
        System.out.println("Income statement GeneralAndAdminExpenses()); is :  " + incomeStatement.getGeneralAndAdminExpenses());
        System.out.println("Income statement SellingAndMarketingExpenses()); is :  " + incomeStatement.getSellingAndMarketingExpenses());
        System.out.println("Income statement OtherExpenses()); is :  " + incomeStatement.getOtherExpenses());
        System.out.println("Income statement OperatingExpenses()); is :  " + incomeStatement.getOperatingExpenses());
        System.out.println("Income statement CostAndExpenses()); is :  " + incomeStatement.getCostAndExpenses());
        System.out.println("Income statement InterestExpense()); is :  " + incomeStatement.getInterestExpense());
        System.out.println("Income statement DepricationAndAmortization()); is :  " + incomeStatement.getDepricationAndAmortization());
        System.out.println("Income statement Ebitda()); is :  " + incomeStatement.getEbitda());
        System.out.println("Income statement EbitdaRatio()); is :  " + incomeStatement.getEbitdaRatio());
        System.out.println("Income statement OperatingIncome()); is :  " + incomeStatement.getOperatingIncome());
        System.out.println("Income statement OperatingIncomeRatio()); is :  " + incomeStatement.getOperatingIncomeRatio());
        System.out.println("Income statement TotalOtherIncomeExpensesNet()); is :  " + incomeStatement.getTotalOtherIncomeExpensesNet());
        System.out.println("Income statement IncomeBeforeTax()); is :  " + incomeStatement.getIncomeBeforeTax());
        System.out.println("Income statement IncomeBeforeTaxRatio()); is :  " + incomeStatement.getIncomeBeforeTaxRatio());
        System.out.println("Income statement IncomeTaxExpense()); is :  " + incomeStatement.getIncomeTaxExpense());
        System.out.println("Income statement NetIncome()); is :  " + incomeStatement.getNetIncome());
        System.out.println("Income statement NetIncomeRatio()); is :  " + incomeStatement.getNetIncomeRatio());
        System.out.println("Income statement Eps()); is :  " + incomeStatement.getEps());
        System.out.println("Income statement EpsDiluted()); is :  " + incomeStatement.getEpsDiluted());
        System.out.println("Income statement WeightedAverageShsOut()); is :  " + incomeStatement.getWeightedAverageShsOut());
        System.out.println("Income statement WeightedAverageShsOutDil()); is :  " + incomeStatement.getWeightedAverageShsOutDil());
         */

        LOGGER.info("12");
        company.getBalanceStatements().add(balanceStatement);

        LOGGER.info("13");
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (cashflow) returned. Check if result is correct"));
    }

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

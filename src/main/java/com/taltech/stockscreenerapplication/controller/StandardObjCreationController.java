package com.taltech.stockscreenerapplication.controller;

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
import com.taltech.stockscreenerapplication.service.formulaToValue.StandardStatementCreationHelper;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/standardStatementCreation")
public class StandardObjCreationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardObjCreationController.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @GetMapping("/{ticker}/createIncomeStatementFromFormula")
    public ResponseEntity<MessageResponse> incomeMappingFromFormula(@PathVariable String ticker) {
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();
        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        List<IncomeStatRaw> rawIncomeStatements = company.getIncomeRawStatements();

        /*  Finding incomeStatement with right date/period  */
        //        Predicate<Client> hasSameNameAsOneUser =
        //                c -> users.stream().anyMatch(u -> u.getName().equals(c.getName()));
        //
        //        return clients.stream()
        //                .filter(hasSameNameAsOneUser)
        //                .collect(Collectors.toList());

        List<Attribute> attributesWithValues = rawIncomeStatements.get(0).getAttributes();
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        // sulgudes on see, mis objekti jaoks need # tehakse
        StandardEvaluationContext stContext  = new StandardEvaluationContext(incomeStatement);

        // stContext.setVariable("Revenue", 150);
        // stContext.setVariable("Other_revenue", 10);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig = company.getIncomeConfigurations().get(0);
        SpelExpressionParser parser = new SpelExpressionParser();

        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        standardStatementCreationHelper.createIncomeStrings(rightCompanyIncomeConfig);

        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(incomeStandardFieldFormulas, parser, stContext);

        /*
        LOGGER.info(revenueString);
        SpelExpression expression = parser.parseRaw(revenueString);
        expression.getValue(stContext);

        LOGGER.info(costOfRevenueString);
        SpelExpression expression2 = parser.parseRaw(costOfRevenueString);
        expression2.getValue(stContext);
         */

        /*
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());
        */

        company.getIncomeStatements().add(incomeStatement);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (income) returned. Check if result is correct"));
    }

    @GetMapping("/{ticker}/createCashflowStatementFromFormula")
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
        standardStatementCreationHelper.createValuesForStatementFromFormulas(cashflowStandardFieldFormulas, parser, stContext);
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

    @GetMapping("/{ticker}/createBalanceStatementFromFormula")
    public ResponseEntity<MessageResponse> balanceMappingFromFormula(@PathVariable String ticker) {
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        List<BalanceStatRaw> rawBalanceStatements = company.getBilanceRawStatements();

        /*  Finding incomeStatement with right date/period  */
        //        Predicate<Client> hasSameNameAsOneUser =
        //                c -> users.stream().anyMatch(u -> u.getName().equals(c.getName()));
        //
        //        return clients.stream()
        //                .filter(hasSameNameAsOneUser)
        //                .collect(Collectors.toList());

        List<Attribute> attributesWithValues = rawBalanceStatements.get(0).getAttributes();
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        StandardEvaluationContext stContext  = new StandardEvaluationContext(balanceStatement); // sulgudes on see, mis objekti jaoks need # tehakse

        // stContext.setVariable("Revenue", 150);
        // stContext.setVariable("Other_revenue", 10);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = company.getBalanceConfigurations().get(0);
        SpelExpressionParser parser = new SpelExpressionParser();

        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);

        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(balanceStandardFieldFormulas, parser, stContext);
        /*
        LOGGER.info(revenueString);
        SpelExpression expression = parser.parseRaw(revenueString);
        expression.getValue(stContext);

        LOGGER.info(costOfRevenueString);
        SpelExpression expression2 = parser.parseRaw(costOfRevenueString);
        expression2.getValue(stContext);
        */

        /*
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());
        System.out.println("Income statement CostOfRevenue()); is :  " + incomeStatement.getCostOfRevenue());
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
}

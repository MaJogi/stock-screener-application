package com.taltech.stockscreenerapplication.service.formulaToValue;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class StandardStatementCreationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardStatementCreationHelper.class);

    List<String> incomeStandardFieldFormulas;

    String revenueString;
    String costOfRevenueString;
    String grossProfitString;
    String grossProfitRatioString;
    String rAndDexpensesString;
    String generalAndAdminExpensesString;
    String sellingAndMarketingExpensesString;
    String otherExpensesString;
    String operatingExpensesString;
    String costAndExpensesString;
    String interestExpenseString;
    String depricationAndAmortizationString;
    String ebitdaString;
    String ebitdaRatioString;
    String operatingIncomeString;
    String operatingIncomeRatioString;
    String totalOtherIncomeExpensesNetString;
    String incomeBeforeTaxString;
    String incomeBeforeTaxRatioString;
    String incomeTaxExpenseString;
    String netIncomeString ;
    String netIncomeRatioString;
    String epsString;
    String epsDilutedeString;
    String weightedAverageShsOutString;
    String weightedAverageShsOutDilString;

    List<String> cashflowStandardFieldFormulas;

    String netCashflowIncomeString;
    String depriciationAndAmortizationString;
    String stockBasedCompensationString;
    String changeInWorkingCapitalString;
    String accountsReceivablesString;
    String inventoryString;
    String accountsPaymentsString;
    String otherWorkingCapitalString;
    String otherNonCashItemsString;
    String netCashProvidedByOperatingActivitiesString;
    String investmentsInPropertyPlantAndEquipmentString;
    String acquisitionsNetString;
    String purchasesOfInvestmentsString;
    String salesMaturitiesOfInvestmentsString;
    String otherInvestingActivitiesString;
    String netCashUsedForInvestingActivitiesString;
    String debtRepaymentString;



    public StandardStatementCreationHelper() {
        incomeStandardFieldFormulas = new LinkedList<>();
        cashflowStandardFieldFormulas = new LinkedList<>();

    }

    public void createValuesForIncomeStatementFromFormulas(List<String> incomeStandardFieldFormulas,
                                                           SpelExpressionParser parser,
                                                           StandardEvaluationContext stContext){
        for (String currentFormulaString : incomeStandardFieldFormulas) {
            LOGGER.info(currentFormulaString);
            SpelExpression expression = parser.parseRaw(currentFormulaString);
            expression.getValue(stContext);
        }
    }

    public void createValuesForCashflowStatementFromFormulas(List<String> cashflowStandardFieldFormulas,
                                                           SpelExpressionParser parser,
                                                           StandardEvaluationContext stContext){
        for (String currentFormulaString : cashflowStandardFieldFormulas) {
            LOGGER.info(currentFormulaString);
            SpelExpression expression = parser.parseRaw(currentFormulaString);
            expression.getValue(stContext);
        }
    }

    public void createIncomeStrings(CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig) {
        revenueString = String.format("revenue=%s", rightCompanyIncomeConfig.getRevenue());
        costOfRevenueString = String.format("costOfRevenue=%s", rightCompanyIncomeConfig.getCostOfRevenue());
        grossProfitString = String.format("grossProfit=%s", rightCompanyIncomeConfig.getGrossProfit());
        grossProfitRatioString = String.format("grossProfitRatio=%s", rightCompanyIncomeConfig.getGrossProfitRatio());
        rAndDexpensesString = String.format("rAndDexpenses=%s", rightCompanyIncomeConfig.getRAndDexpenses());
        generalAndAdminExpensesString = String.format("generalAndAdminExpenses=%s", rightCompanyIncomeConfig.getGeneralAndAdminExpenses());
        sellingAndMarketingExpensesString = String.format("sellingAndMarketingExpenses=%s", rightCompanyIncomeConfig.getSellingAndMarketingExpenses());
        otherExpensesString = String.format("otherExpenses=%s", rightCompanyIncomeConfig.getOtherExpenses());
        operatingExpensesString = String.format("operatingExpenses=%s", rightCompanyIncomeConfig.getOperatingExpenses());
        costAndExpensesString = String.format("costAndExpenses=%s", rightCompanyIncomeConfig.getCostAndExpenses());
        interestExpenseString = String.format("interestExpense=%s", rightCompanyIncomeConfig.getInterestExpense());
        depricationAndAmortizationString = String.format("depricationAndAmortization=%s", rightCompanyIncomeConfig.getDepricationAndAmortization());
        ebitdaString = String.format("ebitda=%s", rightCompanyIncomeConfig.getEbitda());
        ebitdaRatioString = String.format("ebitdaRatio=%s", rightCompanyIncomeConfig.getEbitdaRatio());
        operatingIncomeString = String.format("operatingIncome=%s", rightCompanyIncomeConfig.getOperatingIncome());
        operatingIncomeRatioString = String.format("operatingIncomeRatio=%s", rightCompanyIncomeConfig.getOperatingIncomeRatio());
        totalOtherIncomeExpensesNetString = String.format("totalOtherIncomeExpensesNet=%s", rightCompanyIncomeConfig.getTotalOtherIncomeExpensesNet());
        incomeBeforeTaxString = String.format("incomeBeforeTax=%s", rightCompanyIncomeConfig.getIncomeBeforeTax());
        incomeBeforeTaxRatioString = String.format("incomeBeforeTaxRatio=%s", rightCompanyIncomeConfig.getIncomeBeforeTaxRatio());
        incomeTaxExpenseString = String.format("incomeTaxExpense=%s", rightCompanyIncomeConfig.getIncomeTaxExpense());
        netIncomeString = String.format("netIncome=%s", rightCompanyIncomeConfig.getNetIncome());
        netIncomeRatioString = String.format("netIncomeRatio=%s", rightCompanyIncomeConfig.getNetIncomeRatio());
        epsString = String.format("eps=%s", rightCompanyIncomeConfig.getEps());
        epsDilutedeString = String.format("epsDiluted=%s", rightCompanyIncomeConfig.getEpsDiluted());
        weightedAverageShsOutString = String.format("weightedAverageShsOut=%s", rightCompanyIncomeConfig.getWeightedAverageShsOut());
        weightedAverageShsOutDilString = String.format("weightedAverageShsOutDil=%s", rightCompanyIncomeConfig.getWeightedAverageShsOutDil());

        createListOfIncomeStrings();
    }

    public void createCashflowStrings(CompanyCashflowStatFormulaConfig rightCompanyCashflowConfig) {
        LOGGER.info("Start of string .format");
        netCashflowIncomeString = String.format("netIncome=%s", rightCompanyCashflowConfig.getNetIncome());
        depriciationAndAmortizationString = String.format("depriciationAndAmortization=%s", rightCompanyCashflowConfig.getDepriciationAndAmortization());
        stockBasedCompensationString = String.format("stockBasedCompensation=%s", rightCompanyCashflowConfig.getStockBasedCompensation());
        changeInWorkingCapitalString = String.format("changeInWorkingCapital=%s", rightCompanyCashflowConfig.getChangeInWorkingCapital());
        accountsReceivablesString = String.format("accountsReceivables=%s", rightCompanyCashflowConfig.getAccountsReceivables());
        inventoryString = String.format("inventory=%s", rightCompanyCashflowConfig.getInventory());
        accountsPaymentsString = String.format("accountsPayments=%s", rightCompanyCashflowConfig.getAccountsPayments());
        otherWorkingCapitalString = String.format("otherWorkingCapital=%s", rightCompanyCashflowConfig.getOtherWorkingCapital());
        otherNonCashItemsString = String.format("otherNonCashItems=%s", rightCompanyCashflowConfig.getOtherNonCashItems());
        netCashProvidedByOperatingActivitiesString = String.format("netCashProvidedByOperatingActivities=%s", rightCompanyCashflowConfig.getNetCashProvidedByOperatingActivities());
        investmentsInPropertyPlantAndEquipmentString = String.format("investmentsInPropertyPlantAndEquipment=%s", rightCompanyCashflowConfig.getInvestmentsInPropertyPlantAndEquipment());
        acquisitionsNetString = String.format("acquisitionsNet=%s", rightCompanyCashflowConfig.getAcquisitionsNet());
        purchasesOfInvestmentsString = String.format("purchasesOfInvestments=%s", rightCompanyCashflowConfig.getPurchasesOfInvestments());
        salesMaturitiesOfInvestmentsString = String.format("salesMaturitiesOfInvestments=%s", rightCompanyCashflowConfig.getSalesMaturitiesOfInvestments());
        otherInvestingActivitiesString = String.format("otherInvestingActivities=%s", rightCompanyCashflowConfig.getOtherInvestingActivities());
        netCashUsedForInvestingActivitiesString = String.format("netCashUsedForInvestingActivities=%s", rightCompanyCashflowConfig.getNetCashUsedForInvestingActivities());
        debtRepaymentString = String.format("debtRepayment=%s", rightCompanyCashflowConfig.getDebtRepayment());
        LOGGER.info("End of string .format");

        createListOfCashflowStrings();
    }

    public void createListOfIncomeStrings(){
        this.incomeStandardFieldFormulas.add(revenueString);
        this.incomeStandardFieldFormulas.add(costOfRevenueString);
        this.incomeStandardFieldFormulas.add(grossProfitString);
        this.incomeStandardFieldFormulas.add(grossProfitRatioString);
        this.incomeStandardFieldFormulas.add(rAndDexpensesString);
        this.incomeStandardFieldFormulas.add(generalAndAdminExpensesString);
        this.incomeStandardFieldFormulas.add(sellingAndMarketingExpensesString);
        this.incomeStandardFieldFormulas.add(otherExpensesString);
        this.incomeStandardFieldFormulas.add(operatingExpensesString);
        this.incomeStandardFieldFormulas.add(costAndExpensesString);
        this.incomeStandardFieldFormulas.add(interestExpenseString);
        this.incomeStandardFieldFormulas.add(depricationAndAmortizationString);
        this.incomeStandardFieldFormulas.add(ebitdaString);
        this.incomeStandardFieldFormulas.add(ebitdaRatioString);
        this.incomeStandardFieldFormulas.add(operatingIncomeString);
        this.incomeStandardFieldFormulas.add(operatingIncomeRatioString);
        this.incomeStandardFieldFormulas.add(totalOtherIncomeExpensesNetString);
        this.incomeStandardFieldFormulas.add(incomeBeforeTaxString);
        this.incomeStandardFieldFormulas.add(incomeBeforeTaxRatioString);
        this.incomeStandardFieldFormulas.add(incomeTaxExpenseString);
        this.incomeStandardFieldFormulas.add(netIncomeString);
        this.incomeStandardFieldFormulas.add(netIncomeRatioString);
        this.incomeStandardFieldFormulas.add(epsString);
        this.incomeStandardFieldFormulas.add(epsDilutedeString);
        this.incomeStandardFieldFormulas.add(weightedAverageShsOutString);
        this.incomeStandardFieldFormulas.add(weightedAverageShsOutDilString);
    }
    public void createListOfCashflowStrings() {
        LOGGER.info("Start of list.add");
        this.cashflowStandardFieldFormulas.add(netCashflowIncomeString);
        this.cashflowStandardFieldFormulas.add(depriciationAndAmortizationString);
        this.cashflowStandardFieldFormulas.add(stockBasedCompensationString);
        this.cashflowStandardFieldFormulas.add(changeInWorkingCapitalString);
        this.cashflowStandardFieldFormulas.add(accountsReceivablesString);
        this.cashflowStandardFieldFormulas.add(inventoryString);
        this.cashflowStandardFieldFormulas.add(accountsPaymentsString);
        this.cashflowStandardFieldFormulas.add(otherWorkingCapitalString);
        this.cashflowStandardFieldFormulas.add(otherNonCashItemsString);
        this.cashflowStandardFieldFormulas.add(netCashProvidedByOperatingActivitiesString);
        this.cashflowStandardFieldFormulas.add(investmentsInPropertyPlantAndEquipmentString);
        this.cashflowStandardFieldFormulas.add(acquisitionsNetString);
        this.cashflowStandardFieldFormulas.add(purchasesOfInvestmentsString);
        this.cashflowStandardFieldFormulas.add(salesMaturitiesOfInvestmentsString);
        this.cashflowStandardFieldFormulas.add(otherInvestingActivitiesString);
        this.cashflowStandardFieldFormulas.add(netCashUsedForInvestingActivitiesString);
        this.cashflowStandardFieldFormulas.add(debtRepaymentString);

        LOGGER.info("End of list.add");
    }
}

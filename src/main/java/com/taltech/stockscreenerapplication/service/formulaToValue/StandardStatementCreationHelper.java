package com.taltech.stockscreenerapplication.service.formulaToValue;

import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
//@Service
public class StandardStatementCreationHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardStatementCreationHelper.class);
    // Initializing Spel parser and helper class to help controller do its job
    SpelExpressionParser parser = new SpelExpressionParser();

    // Spring expression language'i spetsiifiline context, tänu millele saab kasutada selle valemite mootorit
    StandardEvaluationContext stContextBalance;
    StandardEvaluationContext stContextCashflow;
    StandardEvaluationContext stContextIncome;

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
    String inventoryCashflowString;
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

    List<String> balanceStandardFieldFormulas;

    String cashAndCashEquivalentsString;
    String shortTermInvestmentsString;
    String cashAndShortTermInvestmentsString;
    String netReceivablesString;
    String inventoryBalanceString;
    String otherCurrentAssetsString;
    String totalCurrentAssetsString;
    String propertyPlantEquipmentAssetsString;
    String goodwillString;
    String intangibleAssetsString;
    String goodwillAndIntangibleAssetsString;
    String longTermInvestmetsString;
    String taxAssetsString;
    String otherNonCurrentAssetsString;
    String totalNonCurrentAssetsString;
    String otherAssetsString;
    String totalAssetsString;
    String accountPayablesString;
    String shortTermDebtString;
    String taxPayablesString;
    String deferredRevenueString;
    String otherCurrentLiabilitiesString;
    String totalCurrentLiabilitiesString;
    String longTermDebtString;
    String deferredRevenueNonCurrentString;
    String deferredTaxLiabilitiesNonCurrentString;
    String otherNonCurrentLiabilitiesString;
    String totalNonCurrentLiabilitiesString;
    String otherLiabilitiesString;
    String totalLiabilitiesString;
    String commonStockString;
    String retainedEarningsString;
    String accumulatedOtherComprehensiveIncomeLossString;
    String otherTotalStockholdersEquityString;
    String totalStockholdersEquityString;
    String totalLiabilitiesAndStockHoldersEquityString;
    String totalInvestmentsString;
    String totalDebtString;
    String netDebtString;


    public StandardStatementCreationHelper() {
        incomeStandardFieldFormulas = new LinkedList<>();
        cashflowStandardFieldFormulas = new LinkedList<>();
        balanceStandardFieldFormulas = new LinkedList<>();
    }

    public void populateContextesWithValues(List<Attribute> balanceAttributesWithValues,
                                            List<Attribute> cashflowAttributesWithValues,
                                            List<Attribute> incomeAttributesWithValues) {
        createAttributeWithValuesContext(balanceAttributesWithValues, stContextBalance);
        createAttributeWithValuesContext(cashflowAttributesWithValues, stContextCashflow);
        createAttributeWithValuesContext(incomeAttributesWithValues, stContextIncome);
    }

    public void createValuesForStatementFromFormulas(List<String> statementStandardFieldFormulas,
                                                           StandardEvaluationContext stContext){
        for (String currentFormulaString : statementStandardFieldFormulas) {
            LOGGER.info(currentFormulaString);
            SpelExpression expression = parser.parseRaw(currentFormulaString);
            expression.getValue(stContext);
        }
    }

    public void createIncomeStrings(CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig) {
        revenueString = String.format("revenue=%s",
                rightCompanyIncomeConfig.getRevenue());
        costOfRevenueString = String.format("costOfRevenue=%s",
                rightCompanyIncomeConfig.getCostOfRevenue());
        grossProfitString = String.format("grossProfit=%s",
                rightCompanyIncomeConfig.getGrossProfit());
        grossProfitRatioString = String.format("grossProfitRatio=%s",
                rightCompanyIncomeConfig.getGrossProfitRatio());
        rAndDexpensesString = String.format("rAndDexpenses=%s",
                rightCompanyIncomeConfig.getRAndDexpenses());
        generalAndAdminExpensesString = String.format("generalAndAdminExpenses=%s",
                rightCompanyIncomeConfig.getGeneralAndAdminExpenses());
        sellingAndMarketingExpensesString = String.format("sellingAndMarketingExpenses=%s",
                rightCompanyIncomeConfig.getSellingAndMarketingExpenses());
        otherExpensesString = String.format("otherExpenses=%s",
                rightCompanyIncomeConfig.getOtherExpenses());
        operatingExpensesString = String.format("operatingExpenses=%s",
                rightCompanyIncomeConfig.getOperatingExpenses());
        costAndExpensesString = String.format("costAndExpenses=%s",
                rightCompanyIncomeConfig.getCostAndExpenses());
        interestExpenseString = String.format("interestExpense=%s",
                rightCompanyIncomeConfig.getInterestExpense());
        depricationAndAmortizationString = String.format("depricationAndAmortization=%s",
                rightCompanyIncomeConfig.getDepricationAndAmortization());
        ebitdaString = String.format("ebitda=%s",
                rightCompanyIncomeConfig.getEbitda());
        ebitdaRatioString = String.format("ebitdaRatio=%s",
                rightCompanyIncomeConfig.getEbitdaRatio());
        operatingIncomeString = String.format("operatingIncome=%s",
                rightCompanyIncomeConfig.getOperatingIncome());
        operatingIncomeRatioString = String.format("operatingIncomeRatio=%s",
                rightCompanyIncomeConfig.getOperatingIncomeRatio());
        totalOtherIncomeExpensesNetString = String.format("totalOtherIncomeExpensesNet=%s",
                rightCompanyIncomeConfig.getTotalOtherIncomeExpensesNet());
        incomeBeforeTaxString = String.format("incomeBeforeTax=%s",
                rightCompanyIncomeConfig.getIncomeBeforeTax());
        incomeBeforeTaxRatioString = String.format("incomeBeforeTaxRatio=%s",
                rightCompanyIncomeConfig.getIncomeBeforeTaxRatio());
        incomeTaxExpenseString = String.format("incomeTaxExpense=%s",
                rightCompanyIncomeConfig.getIncomeTaxExpense());
        netIncomeString = String.format("netIncome=%s",
                rightCompanyIncomeConfig.getNetIncome());
        netIncomeRatioString = String.format("netIncomeRatio=%s",
                rightCompanyIncomeConfig.getNetIncomeRatio());
        epsString = String.format("eps=%s",
                rightCompanyIncomeConfig.getEps());
        epsDilutedeString = String.format("epsDiluted=%s",
                rightCompanyIncomeConfig.getEpsDiluted());
        weightedAverageShsOutString = String.format("weightedAverageShsOut=%s",
                rightCompanyIncomeConfig.getWeightedAverageShsOut());
        weightedAverageShsOutDilString = String.format("weightedAverageShsOutDil=%s",
                rightCompanyIncomeConfig.getWeightedAverageShsOutDil());

        createListOfIncomeStrings();
    }

    public void createCashflowStrings(CompanyCashflowStatFormulaConfig rightCompanyCashflowConfig) {
        LOGGER.info("Start of string .format");
        netCashflowIncomeString = String.format("netIncome=%s",
                rightCompanyCashflowConfig.getNetIncome());
        depriciationAndAmortizationString = String.format("depriciationAndAmortization=%s",
                rightCompanyCashflowConfig.getDepriciationAndAmortization());
        stockBasedCompensationString = String.format("stockBasedCompensation=%s",
                rightCompanyCashflowConfig.getStockBasedCompensation());
        changeInWorkingCapitalString = String.format("changeInWorkingCapital=%s",
                rightCompanyCashflowConfig.getChangeInWorkingCapital());
        accountsReceivablesString = String.format("accountsReceivables=%s",
                rightCompanyCashflowConfig.getAccountsReceivables());
        inventoryCashflowString = String.format("inventory=%s",
                rightCompanyCashflowConfig.getInventory());
        accountsPaymentsString = String.format("accountsPayments=%s",
                rightCompanyCashflowConfig.getAccountsPayments());
        otherWorkingCapitalString = String.format("otherWorkingCapital=%s",
                rightCompanyCashflowConfig.getOtherWorkingCapital());
        otherNonCashItemsString = String.format("otherNonCashItems=%s",
                rightCompanyCashflowConfig.getOtherNonCashItems());
        netCashProvidedByOperatingActivitiesString = String.format("netCashProvidedByOperatingActivities=%s",
                rightCompanyCashflowConfig.getNetCashProvidedByOperatingActivities());
        investmentsInPropertyPlantAndEquipmentString = String.format("investmentsInPropertyPlantAndEquipment=%s",
                rightCompanyCashflowConfig.getInvestmentsInPropertyPlantAndEquipment());
        acquisitionsNetString = String.format("acquisitionsNet=%s",
                rightCompanyCashflowConfig.getAcquisitionsNet());
        purchasesOfInvestmentsString = String.format("purchasesOfInvestments=%s",
                rightCompanyCashflowConfig.getPurchasesOfInvestments());
        salesMaturitiesOfInvestmentsString = String.format("salesMaturitiesOfInvestments=%s",
                rightCompanyCashflowConfig.getSalesMaturitiesOfInvestments());
        otherInvestingActivitiesString = String.format("otherInvestingActivities=%s",
                rightCompanyCashflowConfig.getOtherInvestingActivities());
        netCashUsedForInvestingActivitiesString = String.format("netCashUsedForInvestingActivities=%s",
                rightCompanyCashflowConfig.getNetCashUsedForInvestingActivities());
        debtRepaymentString = String.format("debtRepayment=%s",
                rightCompanyCashflowConfig.getDebtRepayment());
        LOGGER.info("End of string .format");

        createListOfCashflowStrings();
    }

    public void createBalanceStrings(CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig) {
        LOGGER.info("Start of string .format");
        cashAndCashEquivalentsString = String.format("cashAndCashEquivalents=%s",
                rightCompanyBalanceConfig.getCashAndCashEquivalents());
        shortTermInvestmentsString = String.format("shortTermInvestments=%s",
                rightCompanyBalanceConfig.getShortTermInvestments());
        cashAndShortTermInvestmentsString = String.format("cashAndShortTermInvestments=%s",
                rightCompanyBalanceConfig.getCashAndShortTermInvestments());
        netReceivablesString = String.format("netReceivables=%s",
                rightCompanyBalanceConfig.getNetReceivables());
        inventoryBalanceString = String.format("inventory=%s",
                rightCompanyBalanceConfig.getInventory());
        otherCurrentAssetsString = String.format("otherCurrentAssets=%s",
                rightCompanyBalanceConfig.getOtherCurrentAssets());
        totalCurrentAssetsString = String.format("totalCurrentAssets=%s",
                rightCompanyBalanceConfig.getTotalCurrentAssets());
        propertyPlantEquipmentAssetsString = String.format("propertyPlantEquipmentAssets=%s",
                rightCompanyBalanceConfig.getPropertyPlantEquipmentAssets());
        goodwillString = String.format("goodwill=%s",
                rightCompanyBalanceConfig.getGoodwill());
        intangibleAssetsString = String.format("intangibleAssets=%s",
                rightCompanyBalanceConfig.getIntangibleAssets());
        goodwillAndIntangibleAssetsString = String.format("goodwillAndIntangibleAssets=%s",
                rightCompanyBalanceConfig.getGoodwillAndIntangibleAssets());
        longTermInvestmetsString = String.format("longTermInvestmets=%s",
                rightCompanyBalanceConfig.getLongTermInvestmets());
        taxAssetsString = String.format("taxAssets=%s",
                rightCompanyBalanceConfig.getTaxAssets());
        otherNonCurrentAssetsString = String.format("otherNonCurrentAssets=%s",
                rightCompanyBalanceConfig.getOtherNonCurrentAssets());
        totalNonCurrentAssetsString = String.format("totalNonCurrentAssets=%s",
                rightCompanyBalanceConfig.getTotalNonCurrentAssets());
        otherAssetsString = String.format("otherAssets=%s",
                rightCompanyBalanceConfig.getOtherAssets());
        totalAssetsString = String.format("totalAssets=%s",
                rightCompanyBalanceConfig.getTotalAssets());
        accountPayablesString = String.format("accountPayables=%s",
                rightCompanyBalanceConfig.getAccountPayables());
        shortTermDebtString = String.format("shortTermDebt=%s",
                rightCompanyBalanceConfig.getShortTermDebt());
        taxPayablesString = String.format("taxPayables=%s",
                rightCompanyBalanceConfig.getTaxPayables());
        deferredRevenueString = String.format("deferredRevenue=%s",
                rightCompanyBalanceConfig.getDeferredRevenue());
        otherCurrentLiabilitiesString = String.format("otherCurrentLiabilities=%s",
                rightCompanyBalanceConfig.getOtherCurrentLiabilities());
        totalCurrentLiabilitiesString = String.format("totalCurrentLiabilities=%s",
                rightCompanyBalanceConfig.getTotalCurrentLiabilities());
        longTermDebtString = String.format("longTermDebt=%s",
                rightCompanyBalanceConfig.getLongTermDebt());
        deferredRevenueNonCurrentString = String.format("deferredRevenueNonCurrent=%s",
                rightCompanyBalanceConfig.getDeferredRevenueNonCurrent());
        deferredTaxLiabilitiesNonCurrentString = String.format("deferredTaxLiabilitiesNonCurrent=%s",
                rightCompanyBalanceConfig.getDeferredTaxLiabilitiesNonCurrent());
        otherNonCurrentLiabilitiesString = String.format("otherNonCurrentLiabilities=%s",
                rightCompanyBalanceConfig.getOtherNonCurrentAssets());
        totalNonCurrentLiabilitiesString = String.format("totalNonCurrentLiabilities=%s",
                rightCompanyBalanceConfig.getTotalNonCurrentLiabilities());
        otherLiabilitiesString = String.format("otherLiabilities=%s",
                rightCompanyBalanceConfig.getOtherLiabilities());
        totalLiabilitiesString = String.format("totalLiabilities=%s",
                rightCompanyBalanceConfig.getTotalLiabilities());
        commonStockString = String.format("commonStock=%s",
                rightCompanyBalanceConfig.getCommonStock());
        retainedEarningsString = String.format("retainedEarnings=%s",
                rightCompanyBalanceConfig.getRetainedEarnings());
        accumulatedOtherComprehensiveIncomeLossString = String.format("accumulatedOtherComprehensiveIncomeLoss=%s",
                rightCompanyBalanceConfig.getAccumulatedOtherComprehensiveIncomeLoss());
        otherTotalStockholdersEquityString = String.format("otherTotalStockholdersEquity=%s",
                rightCompanyBalanceConfig.getOtherTotalStockholdersEquity());
        totalStockholdersEquityString = String.format("totalStockholdersEquity=%s",
                rightCompanyBalanceConfig.getTotalStockholdersEquity());
        totalLiabilitiesAndStockHoldersEquityString = String.format("totalLiabilitiesAndStockHoldersEquity=%s",
                rightCompanyBalanceConfig.getTotalLiabilitiesAndStockHoldersEquity());
        totalInvestmentsString = String.format("totalInvestments=%s",
                rightCompanyBalanceConfig.getTotalInvestments());
        totalDebtString = String.format("totalDebt=%s",
                rightCompanyBalanceConfig.getTotalDebt());
        netDebtString = String.format("netDebt=%s",
                rightCompanyBalanceConfig.getNetDebt());


        LOGGER.info("End of string .format");

        createListOfBalanceStrings();
    }

    public void createListOfIncomeStrings(){
        incomeStandardFieldFormulas.add(revenueString);
        incomeStandardFieldFormulas.add(costOfRevenueString);
        incomeStandardFieldFormulas.add(grossProfitString);
        incomeStandardFieldFormulas.add(grossProfitRatioString);
        incomeStandardFieldFormulas.add(rAndDexpensesString);
        incomeStandardFieldFormulas.add(generalAndAdminExpensesString);
        incomeStandardFieldFormulas.add(sellingAndMarketingExpensesString);
        incomeStandardFieldFormulas.add(otherExpensesString);
        incomeStandardFieldFormulas.add(operatingExpensesString);
        incomeStandardFieldFormulas.add(costAndExpensesString);
        incomeStandardFieldFormulas.add(interestExpenseString);
        incomeStandardFieldFormulas.add(depricationAndAmortizationString);
        incomeStandardFieldFormulas.add(ebitdaString);
        incomeStandardFieldFormulas.add(ebitdaRatioString);
        incomeStandardFieldFormulas.add(operatingIncomeString);
        incomeStandardFieldFormulas.add(operatingIncomeRatioString);
        incomeStandardFieldFormulas.add(totalOtherIncomeExpensesNetString);
        incomeStandardFieldFormulas.add(incomeBeforeTaxString);
        incomeStandardFieldFormulas.add(incomeBeforeTaxRatioString);
        incomeStandardFieldFormulas.add(incomeTaxExpenseString);
        incomeStandardFieldFormulas.add(netIncomeString);
        incomeStandardFieldFormulas.add(netIncomeRatioString);
        incomeStandardFieldFormulas.add(epsString);
        incomeStandardFieldFormulas.add(epsDilutedeString);
        incomeStandardFieldFormulas.add(weightedAverageShsOutString);
        incomeStandardFieldFormulas.add(weightedAverageShsOutDilString);
    }
    public void createListOfCashflowStrings() {
        LOGGER.info("Start of list.add");
        cashflowStandardFieldFormulas.add(netCashflowIncomeString);
        cashflowStandardFieldFormulas.add(depriciationAndAmortizationString);
        cashflowStandardFieldFormulas.add(stockBasedCompensationString);
        cashflowStandardFieldFormulas.add(changeInWorkingCapitalString);
        cashflowStandardFieldFormulas.add(accountsReceivablesString);
        cashflowStandardFieldFormulas.add(inventoryCashflowString);
        cashflowStandardFieldFormulas.add(accountsPaymentsString);
        cashflowStandardFieldFormulas.add(otherWorkingCapitalString);
        cashflowStandardFieldFormulas.add(otherNonCashItemsString);
        cashflowStandardFieldFormulas.add(netCashProvidedByOperatingActivitiesString);
        cashflowStandardFieldFormulas.add(investmentsInPropertyPlantAndEquipmentString);
        cashflowStandardFieldFormulas.add(acquisitionsNetString);
        cashflowStandardFieldFormulas.add(purchasesOfInvestmentsString);
        cashflowStandardFieldFormulas.add(salesMaturitiesOfInvestmentsString);
        cashflowStandardFieldFormulas.add(otherInvestingActivitiesString);
        cashflowStandardFieldFormulas.add(netCashUsedForInvestingActivitiesString);
        cashflowStandardFieldFormulas.add(debtRepaymentString);

        LOGGER.info("End of list.add");
    }

    public void createListOfBalanceStrings() {
        LOGGER.info("Start of list.add");
        balanceStandardFieldFormulas.add(cashAndCashEquivalentsString);
        balanceStandardFieldFormulas.add(shortTermInvestmentsString);
        balanceStandardFieldFormulas.add(cashAndShortTermInvestmentsString);
        balanceStandardFieldFormulas.add(netReceivablesString);
        balanceStandardFieldFormulas.add(inventoryBalanceString);
        balanceStandardFieldFormulas.add(otherCurrentAssetsString);
        balanceStandardFieldFormulas.add(totalCurrentAssetsString);
        balanceStandardFieldFormulas.add(propertyPlantEquipmentAssetsString);
        balanceStandardFieldFormulas.add(goodwillString);
        balanceStandardFieldFormulas.add(intangibleAssetsString);
        balanceStandardFieldFormulas.add(goodwillAndIntangibleAssetsString);
        balanceStandardFieldFormulas.add(longTermInvestmetsString);
        balanceStandardFieldFormulas.add(taxAssetsString);
        balanceStandardFieldFormulas.add(otherNonCurrentAssetsString);
        balanceStandardFieldFormulas.add(totalNonCurrentAssetsString);
        balanceStandardFieldFormulas.add(otherAssetsString);
        balanceStandardFieldFormulas.add(totalAssetsString);
        balanceStandardFieldFormulas.add(accountPayablesString);
        balanceStandardFieldFormulas.add(shortTermDebtString);
        balanceStandardFieldFormulas.add(taxPayablesString);
        balanceStandardFieldFormulas.add(propertyPlantEquipmentAssetsString);
        balanceStandardFieldFormulas.add(deferredRevenueString);
        balanceStandardFieldFormulas.add(otherCurrentLiabilitiesString);
        balanceStandardFieldFormulas.add(totalCurrentLiabilitiesString);
        balanceStandardFieldFormulas.add(longTermDebtString);
        balanceStandardFieldFormulas.add(deferredRevenueNonCurrentString);
        balanceStandardFieldFormulas.add(deferredTaxLiabilitiesNonCurrentString);
        balanceStandardFieldFormulas.add(deferredTaxLiabilitiesNonCurrentString);
        balanceStandardFieldFormulas.add(otherNonCurrentLiabilitiesString);
        balanceStandardFieldFormulas.add(totalNonCurrentLiabilitiesString);
        balanceStandardFieldFormulas.add(otherLiabilitiesString);
        balanceStandardFieldFormulas.add(totalLiabilitiesString);
        balanceStandardFieldFormulas.add(commonStockString);
        balanceStandardFieldFormulas.add(retainedEarningsString);
        balanceStandardFieldFormulas.add(accumulatedOtherComprehensiveIncomeLossString);
        balanceStandardFieldFormulas.add(otherTotalStockholdersEquityString);
        balanceStandardFieldFormulas.add(totalStockholdersEquityString);
        balanceStandardFieldFormulas.add(totalLiabilitiesAndStockHoldersEquityString);
        balanceStandardFieldFormulas.add(totalInvestmentsString);
        balanceStandardFieldFormulas.add(totalDebtString);
        balanceStandardFieldFormulas.add(netDebtString);
        LOGGER.info("End of list.add");
    }

    public static GroupOfStatements findRightGroupOfStatements(List<GroupOfStatements> companyFullRawGroupOfStatements,
                                                               String balance_date) {
        for (GroupOfStatements rawGroupOfStatements : companyFullRawGroupOfStatements) {
            if (rawGroupOfStatements.getBalanceStatRaw().getDateOrPeriod().equals(balance_date)) {
                LOGGER.info("Found right raw GroupOfStatements: -> {} ", rawGroupOfStatements.getGroup_of_stats_id()
                        .toString());
                return rawGroupOfStatements;
            }
        }
        return null;
    }

    // Right config according to date.
    /*
        Ex balance_date is "30.06.2017"
        We have balance configurations: from 31.12.2015 to 31.12.2016 AND other one 01.01.2017 - 31.12.2019
        Now we have to find right configuration according to balance_date. This way we will get also a configuration
        collection id, which in turn gives us three configurations.
        This condfigurations will be used to generate standard statement objects.
    */
    public static CompanyBalanceStatFormulaConfig findRightBalanceConfig(
            List<CompanyBalanceStatFormulaConfig> companyBalanceConfigs,
            String balance_date) {
        Date dateObject = new Date();
        try {
            dateObject = new SimpleDateFormat("dd.MM.yyyy").parse(balance_date);
            System.out.println(balance_date +"\t" + dateObject );
        }
        catch (ParseException e) {
            LOGGER.info("ParseExceiption!");
        }

        for (CompanyBalanceStatFormulaConfig currentConfig : companyBalanceConfigs) {
            try {
                Date dateFrom = new SimpleDateFormat("dd.MM.yyyy").parse(currentConfig.getDateFrom());
                Date dateTo = new SimpleDateFormat("dd.MM.yyyy").parse(currentConfig.getDateTo());

                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(dateTo);
                c.add(Calendar.HOUR, 24);

                // convert calendar to date
                Date dateToOne = c.getTime();

                if (dateObject.after(dateFrom) && dateObject.before(dateToOne) ) {
                    LOGGER.info("Right company balanceConfig id is: {}", currentConfig);
                    return currentConfig;
                }
            }
            catch (ParseException e) {
                LOGGER.info("ParseException!");
            }
        }
        return null;
    }

    public static CompanyCashflowStatFormulaConfig findRightCashflowConfig(
            List<CompanyCashflowStatFormulaConfig> companyCashflowStatFormulaConfigs,
            Long companyConfigCollectionId) {
        for (CompanyCashflowStatFormulaConfig config : companyCashflowStatFormulaConfigs) {
            if (config.getCompany_config_collection_id().equals(companyConfigCollectionId)) {
                return config;
            }
        }
        return null;
    }

    public static CompanyIncomeStatFormulaConfig findRightIncomeConfig(
            List<CompanyIncomeStatFormulaConfig> companyIncomeStatFormulaConfigs,
            Long companyConfigCollectionId) {
        for (CompanyIncomeStatFormulaConfig config : companyIncomeStatFormulaConfigs) {
            if (config.getCompany_config_collection_id().equals(companyConfigCollectionId)) {
                return config;
            }
        }
        return null;
    }

    // Atribuutide lisamine konteksti, et neid saaks hiljem muutujatena kasutada arvutustes
    // stContext.setVariable("Revenue", 150);
    public void createAttributeWithValuesContext(List<Attribute> statementAttributesWithValues,
                                                 StandardEvaluationContext stContext) {
        for (Attribute attr : statementAttributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        for (Attribute attr : statementAttributesWithValues) {
            LOGGER.info(attr.getFieldName().replaceAll("\\s+", "_"));
        }
    }
    public void createStContextes(BalanceStatStandWithValues balanceStatement,
                                  CashflowStatStandWithValues cashflowStatement,
                                  IncomeStatStandWithValues incomeStatement) {
        stContextBalance = new StandardEvaluationContext(balanceStatement);
        stContextCashflow = new StandardEvaluationContext(cashflowStatement);
        stContextIncome = new StandardEvaluationContext(incomeStatement);
    }

    public void createBalanceStatement(CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig) {
        // Dynamically are taken previously inserted formulas into balance conf, then parsed into correct executable
        // statements which will later be used to populate standard statements. Correct executable statements are added
        // into list.
        createBalanceStrings(rightCompanyBalanceConfig);
        // Now we get dynamically created formulas
        List<String> balanceStandardFieldFormulas = getBalanceStandardFieldFormulas();
        // Preparation is done.
        // Now method is executed, which using formulas with pre defined variables
        // generates new standard balance statement.
        createValuesForStatementFromFormulas(balanceStandardFieldFormulas,
                stContextBalance);
    }

    public void createCashflowStatement(CompanyCashflowStatFormulaConfig cashflowConfig) {
        createCashflowStrings(cashflowConfig);
        List<String> cashflowStandardFieldFormulas = getCashflowStandardFieldFormulas();
        createValuesForStatementFromFormulas(cashflowStandardFieldFormulas,
                stContextCashflow);
    }

    public void createIncomeStatement(CompanyIncomeStatFormulaConfig incomeConfig) {
        createIncomeStrings(incomeConfig);
        List<String> incomeStandardFieldFormulas = getIncomeStandardFieldFormulas();
        createValuesForStatementFromFormulas(incomeStandardFieldFormulas,
                stContextIncome);
    }
}


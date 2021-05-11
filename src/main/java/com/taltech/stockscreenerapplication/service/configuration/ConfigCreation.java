package com.taltech.stockscreenerapplication.service.configuration;

import com.taltech.stockscreenerapplication.model.statement.configuration.BalanceStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.CashflowStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.BalanceMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.CashflowMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.IncomeMappingRequest;
import org.springframework.stereotype.Service;

@Service
public class ConfigCreation {
    public static void setIncomeConfigObjectFields(IncomeStatConfig testIncomeConfig,
                                                   IncomeMappingRequest incomeRequest) {
        testIncomeConfig.setCompany_config_collection_id(incomeRequest.getCompanyConfigCollectionId());
        testIncomeConfig.setDateFrom(incomeRequest.getDateFrom());
        testIncomeConfig.setDateTo(incomeRequest.getDateTo());
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
    }

    public static void setCashflowConfigObjectFields(CashflowStatConfig testCashflowConfig,
                                                     CashflowMappingRequest cashflowRequest) {
        testCashflowConfig.setCompany_config_collection_id(cashflowRequest.getCompanyConfigCollectionId());
        testCashflowConfig.setDateFrom(cashflowRequest.getDateFrom());
        testCashflowConfig.setDateTo(cashflowRequest.getDateTo());
        testCashflowConfig.setDateFrom(cashflowRequest.getDateFrom());
        testCashflowConfig.setDateTo(cashflowRequest.getDateTo());
        testCashflowConfig.setNetIncome(cashflowRequest.getNetIncome());
        testCashflowConfig.setDepriciationAndAmortization(cashflowRequest.getDepriciationAndAmortization());
        testCashflowConfig.setStockBasedCompensation(cashflowRequest.getStockBasedCompensation());
        testCashflowConfig.setStockBasedCompensation(cashflowRequest.getStockBasedCompensation());
        testCashflowConfig.setChangeInWorkingCapital(cashflowRequest.getChangeInWorkingCapital());
        testCashflowConfig.setAccountsReceivables(cashflowRequest.getAccountsReceivables());
        testCashflowConfig.setInventory(cashflowRequest.getInventory());
        testCashflowConfig.setAccountsPayments(cashflowRequest.getAccountsPayments());
        testCashflowConfig.setOtherNonCashItems(cashflowRequest.getOtherNonCashItems());
        testCashflowConfig.setNetCashProvidedByOperatingActivities(
                cashflowRequest.getNetCashProvidedByOperatingActivities());
        testCashflowConfig.setInvestmentsInPropertyPlantAndEquipment(
                cashflowRequest.getInvestmentsInPropertyPlantAndEquipment());
        testCashflowConfig.setAcquisitionsNet(cashflowRequest.getAcquisitionsNet());
        testCashflowConfig.setPurchasesOfInvestments(cashflowRequest.getPurchasesOfInvestments());
        testCashflowConfig.setSalesMaturitiesOfInvestments(cashflowRequest.getSalesMaturitiesOfInvestments());
        testCashflowConfig.setOtherInvestingActivities(cashflowRequest.getOtherInvestingActivities());
        testCashflowConfig.setNetCashUsedForInvestingActivities(cashflowRequest.getNetCashUsedForInvestingActivities());
        testCashflowConfig.setDebtRepayment(cashflowRequest.getDebtRepayment());
    }

    public static void setBalanceConfigObjectFields(BalanceStatConfig testBalanceConfig,
                                                    BalanceMappingRequest balanceRequest) {
        testBalanceConfig.setCompany_config_collection_id(balanceRequest.getCompanyConfigCollectionId());
        testBalanceConfig.setDateFrom(balanceRequest.getDateFrom());
        testBalanceConfig.setDateTo(balanceRequest.getDateTo());
        testBalanceConfig.setDateFrom(balanceRequest.getDateFrom());
        testBalanceConfig.setDateTo(balanceRequest.getDateTo());
        testBalanceConfig.setCashAndCashEquivalents(balanceRequest.getCashAndCashEquivalents());
        testBalanceConfig.setShortTermInvestments(balanceRequest.getShortTermInvestments());
        testBalanceConfig.setCashAndShortTermInvestments(balanceRequest.getCashAndShortTermInvestments());
        testBalanceConfig.setNetReceivables(balanceRequest.getNetReceivables());
        testBalanceConfig.setInventory(balanceRequest.getInventory());
        testBalanceConfig.setOtherCurrentAssets(balanceRequest.getOtherCurrentAssets());
        testBalanceConfig.setTotalCurrentAssets(balanceRequest.getTotalCurrentAssets());
        testBalanceConfig.setPropertyPlantEquipmentAssets(balanceRequest.getPropertyPlantEquipmentAssets());
        testBalanceConfig.setGoodwill(balanceRequest.getGoodwill());
        testBalanceConfig.setIntangibleAssets(balanceRequest.getIntangibleAssets());
        testBalanceConfig.setGoodwillAndIntangibleAssets(balanceRequest.getGoodwillAndIntangibleAssets());
        testBalanceConfig.setLongTermInvestmets(balanceRequest.getLongTermInvestmets());
        testBalanceConfig.setTaxAssets(balanceRequest.getTaxAssets());
        testBalanceConfig.setOtherNonCurrentAssets(balanceRequest.getOtherNonCurrentAssets());
        testBalanceConfig.setTotalNonCurrentAssets(balanceRequest.getTotalNonCurrentAssets());
        testBalanceConfig.setOtherAssets(balanceRequest.getOtherAssets());
        testBalanceConfig.setTotalAssets(balanceRequest.getTotalAssets());
        testBalanceConfig.setAccountPayables(balanceRequest.getAccountPayables());
        testBalanceConfig.setShortTermDebt(balanceRequest.getShortTermDebt());
        testBalanceConfig.setTaxPayables(balanceRequest.getTaxPayables());
        testBalanceConfig.setDeferredRevenue(balanceRequest.getDeferredRevenue());
        testBalanceConfig.setOtherCurrentLiabilities(balanceRequest.getOtherCurrentLiabilities());
        testBalanceConfig.setTotalCurrentLiabilities(balanceRequest.getTotalCurrentLiabilities());
        testBalanceConfig.setLongTermDebt(balanceRequest.getLongTermDebt());
        testBalanceConfig.setDeferredRevenueNonCurrent(balanceRequest.getDeferredRevenueNonCurrent());
        testBalanceConfig.setDeferredTaxLiabilitiesNonCurrent(balanceRequest.getDeferredTaxLiabilitiesNonCurrent());
        testBalanceConfig.setOtherNonCurrentLiabilities(balanceRequest.getOtherNonCurrentLiabilities());
        testBalanceConfig.setTotalNonCurrentLiabilities(balanceRequest.getTotalNonCurrentLiabilities());
        testBalanceConfig.setOtherLiabilities(balanceRequest.getOtherLiabilities());
        testBalanceConfig.setTotalLiabilities(balanceRequest.getTotalLiabilities());
        testBalanceConfig.setCommonStock(balanceRequest.getCommonStock());
        testBalanceConfig.setRetainedEarnings(balanceRequest.getRetainedEarnings());
        testBalanceConfig.setAccumulatedOtherComprehensiveIncomeLoss(
                balanceRequest.getAccumulatedOtherComprehensiveIncomeLoss());
        testBalanceConfig.setOtherTotalStockholdersEquity(balanceRequest.getOtherTotalStockholdersEquity());
        testBalanceConfig.setTotalStockholdersEquity(balanceRequest.getTotalStockholdersEquity());
        testBalanceConfig.setTotalLiabilitiesAndStockHoldersEquity(
                balanceRequest.getTotalLiabilitiesAndStockHoldersEquity());
        testBalanceConfig.setTotalInvestments(balanceRequest.getTotalInvestments());
        testBalanceConfig.setTotalDebt(balanceRequest.getTotalDebt());
        testBalanceConfig.setNetDebt(balanceRequest.getNetDebt());
    }
}

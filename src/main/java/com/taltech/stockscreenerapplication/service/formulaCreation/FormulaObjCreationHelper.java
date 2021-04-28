package com.taltech.stockscreenerapplication.service.formulaCreation;

import com.fasterxml.jackson.databind.JsonNode;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.BalanceMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.CashflowMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.IncomeMappingRequest;
import org.springframework.stereotype.Service;

@Service
public class FormulaObjCreationHelper {
    public static void setIncomeConfigObjectFields(CompanyIncomeStatFormulaConfig testIncomeConfig, IncomeMappingRequest incomeRequest) {
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

    public static void setCashflowConfigObjectFields(CompanyCashflowStatFormulaConfig testCashflowConfig, CashflowMappingRequest cashflowRequest) {
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
        testCashflowConfig.setNetCashProvidedByOperatingActivities(cashflowRequest.getNetCashProvidedByOperatingActivities());
        testCashflowConfig.setInvestmentsInPropertyPlantAndEquipment(cashflowRequest.getInvestmentsInPropertyPlantAndEquipment());
        testCashflowConfig.setAcquisitionsNet(cashflowRequest.getAcquisitionsNet());
        testCashflowConfig.setPurchasesOfInvestments(cashflowRequest.getPurchasesOfInvestments());
        testCashflowConfig.setSalesMaturitiesOfInvestments(cashflowRequest.getSalesMaturitiesOfInvestments());
        testCashflowConfig.setOtherInvestingActivities(cashflowRequest.getOtherInvestingActivities());
        testCashflowConfig.setNetCashUsedForInvestingActivities(cashflowRequest.getNetCashUsedForInvestingActivities());
        testCashflowConfig.setDebtRepayment(cashflowRequest.getDebtRepayment());
    }

    public static void setBalanceConfigObjectFields(CompanyBalanceStatFormulaConfig testBalanceConfig, BalanceMappingRequest balanceRequest) {
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
        testBalanceConfig.setAccumulatedOtherComprehensiveIncomeLoss(balanceRequest.getAccumulatedOtherComprehensiveIncomeLoss());
        testBalanceConfig.setOtherTotalStockholdersEquity(balanceRequest.getOtherTotalStockholdersEquity());
        testBalanceConfig.setTotalStockholdersEquity(balanceRequest.getTotalStockholdersEquity());
        testBalanceConfig.setTotalLiabilitiesAndStockHoldersEquity(balanceRequest.getTotalLiabilitiesAndStockHoldersEquity());
        testBalanceConfig.setTotalInvestments(balanceRequest.getTotalInvestments());
        testBalanceConfig.setTotalDebt(balanceRequest.getTotalDebt());
        testBalanceConfig.setNetDebt(balanceRequest.getNetDebt());
    }

    // .asText() can also be used. But instead of null, value be returned as String with text "null"
    public static void setJsonValuesToIncomeConfig(CompanyIncomeStatFormulaConfig testIncomeConfig, JsonNode incomeNode) {
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

    public static void setJsonValuesToCashflowConfig(CompanyCashflowStatFormulaConfig testCashflowConfig, JsonNode cashflowNode) {
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

    public static void setJsonValuesToBalanceConfig(CompanyBalanceStatFormulaConfig testBalanceConfig, JsonNode balanceNode) {
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

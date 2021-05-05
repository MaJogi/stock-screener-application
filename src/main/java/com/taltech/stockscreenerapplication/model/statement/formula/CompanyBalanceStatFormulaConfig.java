package com.taltech.stockscreenerapplication.model.statement.formula;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "firm_balance_statement_formula")
public class CompanyBalanceStatFormulaConfig extends FormulaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_stat_formula_id")
    private Long income_stat_formula_id;

    /*
    @Column(name = "symbol") // which company it belong to
    private String symbol;
     */

    /*
    // Administrator can create a collection of statement configurations, for specific date. For example: 31.06.2017
    @Column(name = "company_config_collection_id")
    private Long company_config_collection_id;

    @Column(name = "date_from") // For example 2014-
    private String dateFrom;

    @Column(name = "date_to")  // For example -2018 (later on, another formulas should be used,
    // because company changed statement writing policy
    private String dateTo;
     */

    @Column(name = "cash_and_cash_equivalents")
    private String cashAndCashEquivalents;

    @Column(name = "short_term_investments")
    private String shortTermInvestments;

    @Column(name = "cash_and_short_term_investments")
    private String cashAndShortTermInvestments;

    @Column(name = "net_receivables")
    private String netReceivables;

    @Column(name = "inventory")
    private String inventory;

    @Column(name = "other_current_assets")
    private String otherCurrentAssets;

    @Column(name = "total_current_assets")
    private String totalCurrentAssets;

    @Column(name = "property_plant_equipment_assets")
    private String propertyPlantEquipmentAssets;

    @Column(name = "goodwill")
    private String goodwill;

    @Column(name = "intangible_assets")
    private String intangibleAssets;

    @Column(name = "goodwill_and_intangible_assets")
    private String goodwillAndIntangibleAssets;

    @Column(name = "long_term_investments")
    private String longTermInvestmets;

    @Column(name = "tax_assets")
    private String taxAssets;

    @Column(name = "other_non_current_assets")
    private String otherNonCurrentAssets;

    @Column(name = "total_non_current_assets")
    private String totalNonCurrentAssets;

    @Column(name = "other_assets")
    private String otherAssets;

    @Column(name = "total_assets")
    private String totalAssets;

    @Column(name = "account_payables")
    private String accountPayables;

    @Column(name = "short_term_debt")
    private String shortTermDebt;

    @Column(name = "tax_payables")
    private String taxPayables;

    @Column(name = "deferred_revenue")
    private String deferredRevenue;

    @Column(name = "other_current_liabilities")
    private String otherCurrentLiabilities;

    @Column(name = "total_current_liabilities")
    private String totalCurrentLiabilities;

    @Column(name = "long_term_debt")
    private String longTermDebt;

    @Column(name = "deferred_revenue_non_current")
    private String deferredRevenueNonCurrent;

    @Column(name = "deferred_tax_liabilities_non_current")
    private String deferredTaxLiabilitiesNonCurrent;

    @Column(name = "other_non_current_liabilities")
    private String otherNonCurrentLiabilities;

    @Column(name = "total_non_current_liabilities")
    private String totalNonCurrentLiabilities;

    @Column(name = "other_liabilities")
    private String otherLiabilities;

    @Column(name = "total_liabilities")
    private String totalLiabilities;

    @Column(name = "common_stock")
    private String commonStock;

    @Column(name = "retained_earnings")
    private String retainedEarnings;

    @Column(name = "accumulated_other_comprehensive_income_loss")
    private String accumulatedOtherComprehensiveIncomeLoss;

    @Column(name = "other_total_stockholders_equity")
    private String otherTotalStockholdersEquity;

    @Column(name = "total_stockholders_equity")
    private String totalStockholdersEquity;

    @Column(name = "total_liabilities_and_stockholders_equity")
    private String totalLiabilitiesAndStockHoldersEquity;

    @Column(name = "total_investments")
    private String totalInvestments;

    @Column(name = "total_debt")
    private String totalDebt;

    @Column(name = "net_debt")
    private String netDebt;

    @Override
    public String toString() {
        return "CompanyBalanceStatFormulaConfig{" +
                "income_stat_formula_id=" + income_stat_formula_id +
//                ", company_config_collection_id=" + company_config_collection_id +
//                ", dateFrom='" + dateFrom + '\'' +
//                ", dateTo='" + dateTo + '\'' +
                ", cashAndCashEquivalents='" + cashAndCashEquivalents + '\'' +
                ", shortTermInvestments='" + shortTermInvestments + '\'' +
                ", cashAndShortTermInvestments='" + cashAndShortTermInvestments + '\'' +
                ", netReceivables='" + netReceivables + '\'' +
                ", inventory='" + inventory + '\'' +
                ", otherCurrentAssets='" + otherCurrentAssets + '\'' +
                ", totalCurrentAssets='" + totalCurrentAssets + '\'' +
                ", propertyPlantEquipmentAssets='" + propertyPlantEquipmentAssets + '\'' +
                ", goodwill='" + goodwill + '\'' +
                ", intangibleAssets='" + intangibleAssets + '\'' +
                ", goodwillAndIntangibleAssets='" + goodwillAndIntangibleAssets + '\'' +
                ", longTermInvestmets='" + longTermInvestmets + '\'' +
                ", taxAssets='" + taxAssets + '\'' +
                ", otherNonCurrentAssets='" + otherNonCurrentAssets + '\'' +
                ", totalNonCurrentAssets='" + totalNonCurrentAssets + '\'' +
                ", otherAssets='" + otherAssets + '\'' +
                ", totalAssets='" + totalAssets + '\'' +
                ", accountPayables='" + accountPayables + '\'' +
                ", shortTermDebt='" + shortTermDebt + '\'' +
                ", taxPayables='" + taxPayables + '\'' +
                ", deferredRevenue='" + deferredRevenue + '\'' +
                ", otherCurrentLiabilities='" + otherCurrentLiabilities + '\'' +
                ", totalCurrentLiabilities='" + totalCurrentLiabilities + '\'' +
                ", longTermDebt='" + longTermDebt + '\'' +
                ", deferredRevenueNonCurrent='" + deferredRevenueNonCurrent + '\'' +
                ", deferredTaxLiabilitiesNonCurrent='" + deferredTaxLiabilitiesNonCurrent + '\'' +
                ", otherNonCurrentLiabilities='" + otherNonCurrentLiabilities + '\'' +
                ", totalNonCurrentLiabilities='" + totalNonCurrentLiabilities + '\'' +
                ", otherLiabilities='" + otherLiabilities + '\'' +
                ", totalLiabilities='" + totalLiabilities + '\'' +
                ", commonStock='" + commonStock + '\'' +
                ", retainedEarnings='" + retainedEarnings + '\'' +
                ", accumulatedOtherComprehensiveIncomeLoss='" + accumulatedOtherComprehensiveIncomeLoss + '\'' +
                ", otherTotalStockholdersEquity='" + otherTotalStockholdersEquity + '\'' +
                ", totalStockholdersEquity='" + totalStockholdersEquity + '\'' +
                ", totalLiabilitiesAndStockHoldersEquity='" + totalLiabilitiesAndStockHoldersEquity + '\'' +
                ", totalInvestments='" + totalInvestments + '\'' +
                ", totalDebt='" + totalDebt + '\'' +
                ", netDebt='" + netDebt + '\'' +
                '}';
    }

    public CompanyBalanceStatFormulaConfig() {}

}

package com.taltech.stockscreenerapplication.model.statement.balancestatement;

import com.taltech.stockscreenerapplication.model.CompanyDimension;

import javax.persistence.*;
import java.util.Date;

public class BalanceStatStandWithValues {
    @Id
    @Column(name = "balance_stat_stand_with_values")
    private Long balance_stat_standard_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;

    @Column(name = "date_quarter_year")
    private String dateOrQuarterOrYear;

    @Column(name = "symbol")
    private String symbol; // we need to check if they are identical

    @Column(name = "reported_currency")
    private String reportedCurrency;

    @Column(name = "filling_date")
    private Date fillingDate;

    @Column(name = "accepted_date")
    private Date acceptedDate;

    @Column(name = "period")
    private String period;

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

}

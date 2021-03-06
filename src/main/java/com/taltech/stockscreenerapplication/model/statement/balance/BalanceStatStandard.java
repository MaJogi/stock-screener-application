package com.taltech.stockscreenerapplication.model.statement.balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.StatStandard;
import com.taltech.stockscreenerapplication.model.statement.configuration.BalanceStatConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "balance_statement_stand")
public class BalanceStatStandard extends StatStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_stat_standard_id")
    private Long balance_stat_standard_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "balance_stat_formula_id") // or maybe joincolumn
    private BalanceStatConfig balance_stat_formula_id;

    @Column(name = "cash_and_cash_equivalents")
    private Double cashAndCashEquivalents;

    @Column(name = "short_term_investments")
    private Double shortTermInvestments;

    @Column(name = "cash_and_short_term_investments")
    private Double cashAndShortTermInvestments;

    @Column(name = "net_receivables")
    private Double netReceivables;

    @Column(name = "inventory")
    private Double inventory;

    @Column(name = "other_current_assets")
    private Double otherCurrentAssets;

    @Column(name = "total_current_assets")
    private Double totalCurrentAssets;

    @Column(name = "property_plant_equipment_assets")
    private Double propertyPlantEquipmentAssets;

    @Column(name = "goodwill")
    private Double goodwill;

    @Column(name = "intangible_assets")
    private Double intangibleAssets;

    @Column(name = "goodwill_and_intangible_assets")
    private Double goodwillAndIntangibleAssets;

    @Column(name = "long_term_investments")
    private Double longTermInvestmets;

    @Column(name = "tax_assets")
    private Double taxAssets;

    @Column(name = "other_non_current_assets")
    private Double otherNonCurrentAssets;

    @Column(name = "total_non_current_assets")
    private Double totalNonCurrentAssets;

    @Column(name = "other_assets")
    private Double otherAssets;

    @Column(name = "total_assets")
    private Double totalAssets;

    @Column(name = "account_payables")
    private Double accountPayables;

    @Column(name = "short_term_debt")
    private Double shortTermDebt;

    @Column(name = "tax_payables")
    private Double taxPayables;

    @Column(name = "deferred_revenue")
    private Double deferredRevenue;

    @Column(name = "other_current_liabilities")
    private Double otherCurrentLiabilities;

    @Column(name = "total_current_liabilities")
    private Double totalCurrentLiabilities;

    @Column(name = "long_term_debt")
    private Double longTermDebt;

    @Column(name = "deferred_revenue_non_current")
    private Double deferredRevenueNonCurrent;

    @Column(name = "deferred_tax_liabilities_non_current")
    private Double deferredTaxLiabilitiesNonCurrent;

    @Column(name = "other_non_current_liabilities")
    private Double otherNonCurrentLiabilities;

    @Column(name = "total_non_current_liabilities")
    private Double totalNonCurrentLiabilities;

    @Column(name = "other_liabilities")
    private Double otherLiabilities;

    @Column(name = "total_liabilities")
    private Double totalLiabilities;

    @Column(name = "common_stock")
    private Double commonStock;

    @Column(name = "retained_earnings")
    private Double retainedEarnings;

    @Column(name = "accumulated_other_comprehensive_income_loss")
    private Double accumulatedOtherComprehensiveIncomeLoss;

    @Column(name = "other_total_stockholders_equity")
    private Double otherTotalStockholdersEquity;

    @Column(name = "total_stockholders_equity")
    private Double totalStockholdersEquity;

    @Column(name = "total_liabilities_and_stockholders_equity")
    private Double totalLiabilitiesAndStockHoldersEquity;

    @Column(name = "total_investments")
    private Double totalInvestments;

    @Column(name = "total_debt")
    private Double totalDebt;

    @Column(name = "net_debt")
    private Double netDebt;

    public BalanceStatStandard() { }

    @Override
    public String toString() {
        return "BalanceStatStandard{" +
                "balance_stat_standard_id=" + balance_stat_standard_id +
                ", balance_stat_formula_id=" + balance_stat_formula_id +
                ", cashAndCashEquivalents=" + cashAndCashEquivalents +
                ", shortTermInvestments=" + shortTermInvestments +
                ", cashAndShortTermInvestments=" + cashAndShortTermInvestments +
                ", netReceivables=" + netReceivables +
                ", inventory=" + inventory +
                ", otherCurrentAssets=" + otherCurrentAssets +
                ", totalCurrentAssets=" + totalCurrentAssets +
                ", propertyPlantEquipmentAssets=" + propertyPlantEquipmentAssets +
                ", goodwill=" + goodwill +
                ", intangibleAssets=" + intangibleAssets +
                ", goodwillAndIntangibleAssets=" + goodwillAndIntangibleAssets +
                ", longTermInvestmets=" + longTermInvestmets +
                ", taxAssets=" + taxAssets +
                ", otherNonCurrentAssets=" + otherNonCurrentAssets +
                ", totalNonCurrentAssets=" + totalNonCurrentAssets +
                ", otherAssets=" + otherAssets +
                ", totalAssets=" + totalAssets +
                ", accountPayables=" + accountPayables +
                ", shortTermDebt=" + shortTermDebt +
                ", taxPayables=" + taxPayables +
                ", deferredRevenue=" + deferredRevenue +
                ", otherCurrentLiabilities=" + otherCurrentLiabilities +
                ", totalCurrentLiabilities=" + totalCurrentLiabilities +
                ", longTermDebt=" + longTermDebt +
                ", deferredRevenueNonCurrent=" + deferredRevenueNonCurrent +
                ", deferredTaxLiabilitiesNonCurrent=" + deferredTaxLiabilitiesNonCurrent +
                ", otherNonCurrentLiabilities=" + otherNonCurrentLiabilities +
                ", totalNonCurrentLiabilities=" + totalNonCurrentLiabilities +
                ", otherLiabilities=" + otherLiabilities +
                ", totalLiabilities=" + totalLiabilities +
                ", commonStock=" + commonStock +
                ", retainedEarnings=" + retainedEarnings +
                ", accumulatedOtherComprehensiveIncomeLoss=" + accumulatedOtherComprehensiveIncomeLoss +
                ", otherTotalStockholdersEquity=" + otherTotalStockholdersEquity +
                ", totalStockholdersEquity=" + totalStockholdersEquity +
                ", totalLiabilitiesAndStockHoldersEquity=" + totalLiabilitiesAndStockHoldersEquity +
                ", totalInvestments=" + totalInvestments +
                ", totalDebt=" + totalDebt +
                ", netDebt=" + netDebt +
                '}';
    }
}

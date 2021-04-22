package com.taltech.stockscreenerapplication.model.statement.formula;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "firm_income_statement_formula")
public class CompanyIncomeStatFormulaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_formula_id")
    private Long income_stat_formula_id;

    // Financial administrator can choose a collection of financial configurations
    @Column(name = "company_config_collection_id")
    private Long company_config_collection_id;

    /*
    @Column(name = "symbol") // which company it belong to
    private String symbol;
     */

    // NB: Maybe we should manually choose which formula configuration file should be used on each financial statement

    @Column(name = "date_from") // For example 2014-
    private String DateFrom;

    @Column(name = "date_to")  // For example -2018 (later on, another formulas should be used,
                               // because company changed statement writing policy
    private String DateTo;

    @Column(name = "revenue")
    private String revenue; // #revenue
    // #Cost of sales  + any additional costs incurred to generate a sale
    @Column(name = "cost_of_revenue") // #Cost of sales  + #other operating expenes + #staff costs + #other expenses
    private String costOfRevenue;

    @Column(name = "gross_profit")    // #this.revenue - this.costOfRevenue
    private String grossProfit;

    @Column(name = "gross_profit_ratio") // #this.gross_profit / #this.netIncome
    private String grossProfitRatio;

    @Column(name = "r_and_d_expenses") // Information missing
    private String rAndDexpenses;

    @Column(name = "general_and_administrative_expenses") //#Other operating expenses
    private String generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses") // Information missing
    private String sellingAndMarketingExpenses;

    @Column(name = "other_expenses") // #Other expenses
    private String otherExpenses;

    @Column(name = "operating_expenses") // #Other operating expenses
    private String operatingExpenses;

    @Column(name = "cost_and_expenses") // Missing or how is it calculated?
    private String costAndExpenses;

    @Column(name = "interest_expense") // Information missing
    private String interestExpense;

    @Column(name = "deprication_and_amortization") // #Depreciation,   amortisation   and   impairment losses
    private String depricationAndAmortization;

    @Column(name = "ebitda") // Two possibilities: 1) this.revenue - this.costOfRevenue - rAndDExpenses - this.generalAndAdminExpenses - this.otherExpenses - this.operatingExpenses - this.costAndExpenses
    private String ebitda;   //                    2) #revenue - other operating incoe - cost of sales - other operating expenes - staff costs - other expneses

    @Column(name = "ebitda_ratio") // this.ebitda / this.netIncome
    private String ebitdaRatio;

    @Column(name = "operating_income") // #operating profit
    private String operatingIncome;

    @Column(name = "operating_income_ratio") // #operating profit / this.netIncome
    private String operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net") // Missing?
    private String totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax") // #Profit before tax
    private String incomeBeforeTax;

    @Column(name = "income_before_tax_ratio") // # (Profit before tax) / revenue * 100
    private String incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense") // #income tax expense
    private String incomeTaxExpense;

    @Column(name = "net_income") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR
    private String netIncome;

    @Column(name = "net_income_ratio") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR / #Revenue
    private String netIncomeRatio;

    @Column(name = "eps") // Missing
    private String eps;

    @Column(name = "eps_diluted") // #Basic and diluted earnings per share (euros)
    private String epsDiluted;

    @Column(name = "weighted_average_shs_out") // Missing
    private String weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil") // Missing
    private String weightedAverageShsOutDil;

    public CompanyIncomeStatFormulaConfig() {}

}

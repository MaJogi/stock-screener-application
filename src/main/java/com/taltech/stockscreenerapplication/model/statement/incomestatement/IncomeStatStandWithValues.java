package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "income_statement_stand_with_values")
public class IncomeStatStandWithValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_standard_id")
    private Long income_stat_standard_id;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
     */

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "reported_currency") // Always euro (määrad spring expression confis)
    private String reportedCurrency;

    @Column(name = "filling_date") // Date.today()
    private Date fillingDate;

    @Column(name = "accepted_date")
    private Date acceptedDate;

    @Column(name = "date_or_period") // I mean quarter, year or specific date. Q1 2017, 2018 etc
    private String dateOrPeriod;

    @Column(name = "revenue")
    private double revenue; // #revenue
                                      // #Cost of sales  + any additional costs incurred to generate a sale
    @Column(name = "cost_of_revenue") // #Cost of sales  + #other operating expenes + #staff costs + #other expenses
    private double costOfRevenue;

    @Column(name = "gross_profit")    // #this.revenue - this.costOfRevenue
    private double grossProfit;

    @Column(name = "gross_profit_ratio") // #this.gross_profit / #this.netIncome
    private double grossProfitRatio;

    @Column(name = "r_and_d_expenses") // Information missing
    private double rAndDexpenses;

    @Column(name = "general_and_administrative_expenses") //#Other operating expenses
    private double generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses") // Information missing
    private double sellingAndMarketingExpenses;

    @Column(name = "other_expenses") // #Other expenses
    private double otherExpenses;

    @Column(name = "operating_expenses") // #Other operating expenses
    private double operatingExpenses;

    @Column(name = "cost_and_expenses") // Missing or how is it calculated?
    private double costAndExpenses;

    @Column(name = "interest_expense") // Information missing
    private double interestExpense;

    @Column(name = "deprication_and_amortization") // #Depreciation,   amortisation   and   impairment losses
    private double depricationAndAmortization;

    @Column(name = "ebitda") // Two possibilities: 1) this.revenue - this.costOfRevenue - rAndDExpenses - this.generalAndAdminExpenses - this.otherExpenses - this.operatingExpenses - this.costAndExpenses
    private double ebitda;   //                    2) #revenue - other operating incoe - cost of sales - other operating expenes - staff costs - other expneses

    @Column(name = "ebitda_ratio") // this.ebitda / this.netIncome
    private double ebitdaRatio;

    @Column(name = "operating_income") // #operating profit
    private double operatingIncome;

    @Column(name = "operating_income_ratio") // #operating profit / this.netIncome
    private double operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net") // Missing?
    private double totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax") // #Profit before tax
    private double incomeBeforeTax;

    @Column(name = "income_before_tax_ratio") // # (Profit before tax) / revenue * 100
    private double incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense") // #income tax expense
    private double incomeTaxExpense;

    @Column(name = "net_income") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR
    private double netIncome;

    @Column(name = "net_income_ratio") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR / #Revenue
    private double netIncomeRatio;

    @Column(name = "eps") // Missing
    private double eps;

    @Column(name = "eps_diluted") // #Basic and diluted earnings per share (euros)
    private double epsDiluted;

    @Column(name = "weighted_average_shs_out") // Missing
    private double weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil") // Missing
    private double weightedAverageShsOutDil;

    public IncomeStatStandWithValues() {}
}

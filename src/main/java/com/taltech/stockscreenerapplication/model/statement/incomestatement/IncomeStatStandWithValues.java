package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "income_stat_formula_id") // or maybe joincolumn
    private CompanyIncomeStatFormulaConfig income_stat_formula_id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "reported_currency")
    private String reportedCurrency;

    @Column(name = "filling_date") // Date.today()
    private Date fillingDate;

    @Column(name = "accepted_date")
    private Date acceptedDate;

    @Column(name = "date_or_period") // I mean quarter, year or specific date. Q1 2017, 2018 etc
    private String dateOrPeriod;

    @Column(name = "revenue")
    private Double revenue; // #revenue
                                      // #Cost of sales  + any additional costs incurred to generate a sale
    @Column(name = "cost_of_revenue") // #Cost of sales  + #other operating expenes + #staff costs + #other expenses
    private Double costOfRevenue;

    @Column(name = "gross_profit")    // #this.revenue - this.costOfRevenue
    private Double grossProfit;

    @Column(name = "gross_profit_ratio") // #this.gross_profit / #this.netIncome
    private Double grossProfitRatio;

    @Column(name = "r_and_d_expenses") // Information missing
    private Double rAndDexpenses;

    @Column(name = "general_and_administrative_expenses") //#Other operating expenses
    private Double generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses") // Information missing
    private Double sellingAndMarketingExpenses;

    @Column(name = "other_expenses") // #Other expenses
    private Double otherExpenses;

    @Column(name = "operating_expenses") // #Other operating expenses
    private Double operatingExpenses;

    @Column(name = "cost_and_expenses") // Missing or how is it calculated?
    private Double costAndExpenses;

    @Column(name = "interest_expense") // Information missing
    private Double interestExpense;

    @Column(name = "deprication_and_amortization") // #Depreciation,   amortisation   and   impairment losses
    private Double depricationAndAmortization;

    @Column(name = "ebitda") // Two possibilities: 1) this.revenue - this.costOfRevenue - rAndDExpenses - this.generalAndAdminExpenses - this.otherExpenses - this.operatingExpenses - this.costAndExpenses
    private Double ebitda;   //                    2) #revenue - other operating incoe - cost of sales - other operating expenes - staff costs - other expneses

    @Column(name = "ebitda_ratio") // this.ebitda / this.netIncome
    private Double ebitdaRatio;

    @Column(name = "operating_income") // #operating profit
    private Double operatingIncome;

    @Column(name = "operating_income_ratio") // #operating profit / this.netIncome
    private Double operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net") // Missing?
    private Double totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax") // #Profit before tax
    private Double incomeBeforeTax;

    @Column(name = "income_before_tax_ratio") // # (Profit before tax) / revenue * 100
    private Double incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense") // #income tax expense
    private Double incomeTaxExpense;

    @Column(name = "net_income") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR
    private Double netIncome;

    @Column(name = "net_income_ratio") // Missing or #NET PROFIT FOR THE FINANCIAL YEAR / #Revenue
    private Double netIncomeRatio;

    @Column(name = "eps") // Missing
    private Double eps;

    @Column(name = "eps_diluted") // #Basic and diluted earnings per share (euros)
    private Double epsDiluted;

    @Column(name = "weighted_average_shs_out") // Missing
    private Double weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil") // Missing
    private Double weightedAverageShsOutDil;

    public IncomeStatStandWithValues() {}
}

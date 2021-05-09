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
@Table(name = "company_income_statement_config")
public class IncomeStatConfig extends FormulaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_formula_id")
    private Long income_stat_formula_id;

    @Column(name = "revenue")
    private String revenue; // #Revenue
    // Later on in config file it can be: #Cost of sales + any additional costs incurred to generate a sale

    @Column(name = "cost_of_revenue") // #Cost of sales + #other operating expenes + #staff costs + #other expenses
    private String costOfRevenue;

    @Column(name = "gross_profit")
    private String grossProfit;

    @Column(name = "gross_profit_ratio")
    private String grossProfitRatio;

    @Column(name = "r_and_d_expenses")
    private String rAndDexpenses;

    @Column(name = "general_and_administrative_expenses")
    private String generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses")
    private String sellingAndMarketingExpenses;

    @Column(name = "other_expenses")
    private String otherExpenses;

    @Column(name = "operating_expenses")
    private String operatingExpenses;

    @Column(name = "cost_and_expenses")
    private String costAndExpenses;

    @Column(name = "interest_expense")
    private String interestExpense;

    @Column(name = "deprication_and_amortization")
    private String depricationAndAmortization;

    @Column(name = "ebitda")
    private String ebitda;

    @Column(name = "ebitda_ratio")
    private String ebitdaRatio;

    @Column(name = "operating_income")
    private String operatingIncome;

    @Column(name = "operating_income_ratio")
    private String operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net")
    private String totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax")
    private String incomeBeforeTax;

    @Column(name = "income_before_tax_ratio")
    private String incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense")
    private String incomeTaxExpense;

    @Column(name = "net_income")
    private String netIncome;

    @Column(name = "net_income_ratio")
    private String netIncomeRatio;

    @Column(name = "eps")
    private String eps;

    @Column(name = "eps_diluted")
    private String epsDiluted;

    @Column(name = "weighted_average_shs_out")
    private String weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil")
    private String weightedAverageShsOutDil;

    public IncomeStatConfig() {}

}

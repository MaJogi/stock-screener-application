package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "income_statement_standard")
public class IncomeStatStandard {
    @Id
    @Column(name = "income_stat_standard_id")
    private String income_stat_standard_id;

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

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "cost_of_revenue")
    private double costOfRevenue;

    @Column(name = "gross_profit")
    private double grossProfit;

    @Column(name = "gross_profit_ratio")
    private double grossProfitRatio;


    @Column(name = "r_and_d_expenses")
    private double rAndDexpenses;

    @Column(name = "general_and_administrative_expenses")
    private double generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses")
    private double sellingAndMarketingExpenses;

    @Column(name = "other_expenses")
    private double otherExpenses;

    @Column(name = "operating_expenses")
    private double operatingExpenses;

    @Column(name = "cost_and_expenses")
    private double costAndExpenses;

    @Column(name = "interest_expense")
    private double interestExpense;

    @Column(name = "deprication_and_amortization")
    private double depricationAndAmortization;

    @Column(name = "ebitda")
    private double ebitda;

    @Column(name = "ebitda_ratio")
    private double ebitdaRatio;

    @Column(name = "operating_income")
    private double operatingIncome;

    @Column(name = "operating_income_ratio")
    private double operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net")
    private double totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax")
    private double incomeBeforeTax;

    @Column(name = "income_before_tax_ratio")
    private double incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense")
    private double incomeTaxExpense;

    @Column(name = "net_income")
    private double netIncome;

    @Column(name = "net_income_ratio")
    private double netIncomeRatio;

    @Column(name = "eps")
    private double eps;

    @Column(name = "eps_diluted")
    private double epsDiluted;

    @Column(name = "weighted_average_shs_out")
    private double weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil")
    private double weightedAverageShsOutDil;

    public IncomeStatStandard() {}
}

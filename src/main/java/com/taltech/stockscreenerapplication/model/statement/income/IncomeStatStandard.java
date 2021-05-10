package com.taltech.stockscreenerapplication.model.statement.income;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.StatStandard;
import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "income_statement_stand")
public class IncomeStatStandard extends StatStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_standard_id")
    private Long income_stat_standard_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "income_stat_formula_id") // or maybe joincolumn
    private IncomeStatConfig income_stat_formula_id;

    @Column(name = "revenue")
    private Double revenue;

    @Column(name = "cost_of_revenue")
    private Double costOfRevenue;

    @Column(name = "gross_profit")
    private Double grossProfit;

    @Column(name = "gross_profit_ratio")
    private Double grossProfitRatio;

    @Column(name = "r_and_d_expenses")
    private Double rAndDexpenses;

    @Column(name = "general_and_administrative_expenses")
    private Double generalAndAdminExpenses;

    @Column(name = "selling_and_marketing_expenses")
    private Double sellingAndMarketingExpenses;

    @Column(name = "other_expenses")
    private Double otherExpenses;

    @Column(name = "operating_expenses")
    private Double operatingExpenses;

    @Column(name = "cost_and_expenses")
    private Double costAndExpenses;

    @Column(name = "interest_expense")
    private Double interestExpense;

    @Column(name = "deprication_and_amortization")
    private Double depricationAndAmortization;

    @Column(name = "ebitda")
    private Double ebitda;

    @Column(name = "ebitda_ratio")
    private Double ebitdaRatio;

    @Column(name = "operating_income")
    private Double operatingIncome;

    @Column(name = "operating_income_ratio")
    private Double operatingIncomeRatio;

    @Column(name = "total_other_income_expenses_net")
    private Double totalOtherIncomeExpensesNet;

    @Column(name = "income_before_tax")
    private Double incomeBeforeTax;

    @Column(name = "income_before_tax_ratio")
    private Double incomeBeforeTaxRatio;

    @Column(name = "income_tax_expense")
    private Double incomeTaxExpense;

    @Column(name = "net_income")
    private Double netIncome;

    @Column(name = "net_income_ratio")
    private Double netIncomeRatio;

    @Column(name = "eps")
    private Double eps;

    @Column(name = "eps_diluted")
    private Double epsDiluted;

    @Column(name = "weighted_average_shs_out")
    private Double weightedAverageShsOut;

    @Column(name = "weighted_average_shs_out_dil")
    private Double weightedAverageShsOutDil;

    public IncomeStatStandard() {}
}

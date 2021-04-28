package com.taltech.stockscreenerapplication.util.payload.request.statementMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class IncomeMappingRequest {

    @NotBlank
    private String dateFrom;

    @NotBlank
    private String dateTo;

    @NotBlank
    private String revenue;

    @NotBlank
    private String costOfRevenue;

    @NotBlank
    private String grossProfit;

    @NotBlank
    private String grossProfitRatio;

    @NotBlank
    private String rAndDexpenses;

    @NotBlank
    private String generalAndAdminExpenses;

    @NotBlank
    private String sellingAndMarketingExpenses;

    @NotBlank
    private String otherExpenses;

    @NotBlank
    private String operatingExpenses;

    @NotBlank
    private String costAndExpenses;

    @NotBlank
    private String interestExpense;

    @NotBlank
    private String depricationAndAmortization;

    @NotBlank
    private String ebitda;

    @NotBlank
    private String ebitdaRatio;

    @NotBlank
    private String operatingIncome;

    @NotBlank
    private String operatingIncomeRatio;

    @NotBlank
    private String totalOtherIncomeExpensesNet;

    @NotBlank
    private String incomeBeforeTax;

    @NotBlank
    private String incomeBeforeTaxRatio;

    @NotBlank
    private String incomeTaxExpense;

    @NotBlank
    private String netIncome;

    @NotBlank
    private String netIncomeRatio;

    @NotBlank
    private String eps;

    @NotBlank
    private String epsDiluted;

    @NotBlank
    private String weightedAverageShsOut;

    @NotBlank
    private String weightedAverageShsOutDil;

    public IncomeMappingRequest() {
        super();
    }
}









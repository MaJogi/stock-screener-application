package com.taltech.stockscreenerapplication.util.payload.request.statementMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class BalanceMappingRequest {

    @NotBlank
    private String dateFrom;

    @NotBlank
    private String dateTo;

    @NotBlank
    private String cashAndCashEquivalents;

    @NotBlank
    private String shortTermInvestments;

    @NotBlank
    private String cashAndShortTermInvestments;

    @NotBlank
    private String netReceivables;

    @NotBlank
    private String inventory;

    @NotBlank
    private String otherCurrentAssets;

    @NotBlank
    private String totalCurrentAssets;

    @NotBlank
    private String propertyPlantEquipmentAssets;

    @NotBlank
    private String goodwill;

    @NotBlank
    private String intangibleAssets;

    @NotBlank
    private String goodwillAndIntangibleAssets;

    @NotBlank
    private String longTermInvestmets;

    @NotBlank
    private String taxAssets;

    @NotBlank
    private String otherNonCurrentAssets;

    @NotBlank
    private String totalNonCurrentAssets;

    @NotBlank
    private String otherAssets;

    @NotBlank
    private String totalAssets;

    @NotBlank
    private String accountPayables;

    @NotBlank
    private String shortTermDebt;

    @NotBlank
    private String taxPayables;

    @NotBlank
    private String deferredRevenue;

    @NotBlank
    private String otherCurrentLiabilities;

    @NotBlank
    private String totalCurrentLiabilities;

    @NotBlank
    private String longTermDebt;

    @NotBlank
    private String deferredRevenueNonCurrent;

    @NotBlank
    private String deferredTaxLiabilitiesNonCurrent;

    @NotBlank
    private String otherNonCurrentLiabilities;

    @NotBlank
    private String totalNonCurrentLiabilities;

    @NotBlank
    private String otherLiabilities;

    @NotBlank
    private String totalLiabilities;

    @NotBlank
    private String commonStock;

    @NotBlank
    private String retainedEarnings;

    @NotBlank
    private String accumulatedOtherComprehensiveIncomeLoss;

    @NotBlank
    private String otherTotalStockholdersEquity;

    @NotBlank
    private String totalStockholdersEquity;

    @NotBlank
    private String totalLiabilitiesAndStockHoldersEquity;

    @NotBlank
    private String totalInvestments;

    @NotBlank
    private String totalDebt;

    @NotBlank
    private String netDebt;

    public BalanceMappingRequest() {
        super();
    }
}




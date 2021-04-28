package com.taltech.stockscreenerapplication.util.payload.request.statementMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class CashflowMappingRequest {

    /* Custom */

    @NotBlank
    private Long companyConfigCollectionId;

    /* Custom Ending */

    @NotBlank
    private String dateFrom;

    @NotBlank
    private String dateTo;

    @NotBlank
    private String netIncome;

    @NotBlank
    private String depriciationAndAmortization;

    @NotBlank
    private String stockBasedCompensation;

    @NotBlank
    private String changeInWorkingCapital;

    @NotBlank
    private String accountsReceivables;

    @NotBlank
    private String inventory;

    @NotBlank
    private String accountsPayments;

    @NotBlank
    private String otherWorkingCapital;

    @NotBlank
    private String otherNonCashItems;

    @NotBlank
    private String netCashProvidedByOperatingActivities;

    @NotBlank
    private String investmentsInPropertyPlantAndEquipment;

    @NotBlank
    private String acquisitionsNet;

    @NotBlank
    private String purchasesOfInvestments;

    @NotBlank
    private String salesMaturitiesOfInvestments;

    @NotBlank
    private String otherInvestingActivities;

    @NotBlank
    private String netCashUsedForInvestingActivities;

    @NotBlank
    private String debtRepayment;

    public CashflowMappingRequest() {
        super();
    }
}










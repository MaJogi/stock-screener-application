package com.taltech.stockscreenerapplication.model.statement.formula;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "firm_cashflow_statement_formula")
public class CompanyCashflowStatFormulaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashflow_stat_formula_id")
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

    @Column(name = "net_income")
    private double netIncome;

    @Column(name = "deprication_and_amortization")
    private double depriciationAndAmortization;

    @Column(name = "stock_based_compensation")
    private double stockBasedCompensation;

    @Column(name = "change_in_working_capital")
    private double ChangeInWorkingCapital;

    @Column(name = "accounts_receivables")
    private double accountsReceivables;

    @Column(name = "inventory")
    private double inventory;

    @Column(name = "accounts_payments")
    private double accountsPayments;

    @Column(name = "other_working_capital")
    private double otherWorkingCapital;

    @Column(name = "other_non_cash_items")
    private double otherNonCashItems;

    @Column(name = "net_cash_provided_by_operating_activities")
    private double netCashProvidedByOperatingActivities;

    @Column(name = "investments_in_property_plant_and_equipment")
    private double investmentsInPropertyPlantAndEquipment;

    @Column(name = "acquisitions_net")
    private double acquisitionsNet;

    @Column(name = "purchases_of_investments")
    private double purchasesOfInvestments;

    @Column(name = "sales_maturities_of_investments")
    private double salesMaturitiesOfInvestments;

    @Column(name = "other_investing_activities")
    private double otherInvestingActivities;

    @Column(name = "net_cash_used_for_investing_activities")
    private double netCashUsedForInvestingActivities;

    @Column(name = "debt_repayment")
    private double debtRepayment;

    public CompanyCashflowStatFormulaConfig() {}

}

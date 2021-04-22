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
    
    @Column(name = "date_from") // For example 2014-
    private String DateFrom;

    @Column(name = "date_to")  // For example -2018 (later on, another formulas should be used,
    // because company changed statement writing policy
    private String DateTo;

    @Column(name = "net_income")
    private String netIncome;

    @Column(name = "deprication_and_amortization")
    private String depriciationAndAmortization;

    @Column(name = "stock_based_compensation")
    private String stockBasedCompensation;

    @Column(name = "change_in_working_capital")
    private String ChangeInWorkingCapital;

    @Column(name = "accounts_receivables")
    private String accountsReceivables;

    @Column(name = "inventory")
    private String inventory;

    @Column(name = "accounts_payments")
    private String accountsPayments;

    @Column(name = "other_working_capital")
    private String otherWorkingCapital;

    @Column(name = "other_non_cash_items")
    private String otherNonCashItems;

    @Column(name = "net_cash_provided_by_operating_activities")
    private String netCashProvidedByOperatingActivities;

    @Column(name = "investments_in_property_plant_and_equipment")
    private String investmentsInPropertyPlantAndEquipment;

    @Column(name = "acquisitions_net")
    private String acquisitionsNet;

    @Column(name = "purchases_of_investments")
    private String purchasesOfInvestments;

    @Column(name = "sales_maturities_of_investments")
    private String salesMaturitiesOfInvestments;

    @Column(name = "other_investing_activities")
    private String otherInvestingActivities;

    @Column(name = "net_cash_used_for_investing_activities")
    private String netCashUsedForInvestingActivities;

    @Column(name = "debt_repayment")
    private String debtRepayment;

    public CompanyCashflowStatFormulaConfig() {}

}

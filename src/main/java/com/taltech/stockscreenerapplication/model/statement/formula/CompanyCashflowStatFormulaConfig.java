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
@Table(name = "company_cashflow_statement_config")
public class CompanyCashflowStatFormulaConfig extends FormulaConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashflow_stat_formula_id")
    private Long cashflow_stat_formula_id;

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

    @Override
    public String toString() {
        return "CompanyCashflowStatFormulaConfig{" +
                "cashflow_stat_formula_id=" + cashflow_stat_formula_id +
//                ", company_config_collection_id=" + company_config_collection_id +
//                ", dateFrom='" + dateFrom + '\'' +
//                ", dateTo='" + dateTo + '\'' +
                ", netIncome='" + netIncome + '\'' +
                ", depriciationAndAmortization='" + depriciationAndAmortization + '\'' +
                ", stockBasedCompensation='" + stockBasedCompensation + '\'' +
                ", ChangeInWorkingCapital='" + ChangeInWorkingCapital + '\'' +
                ", accountsReceivables='" + accountsReceivables + '\'' +
                ", inventory='" + inventory + '\'' +
                ", accountsPayments='" + accountsPayments + '\'' +
                ", otherWorkingCapital='" + otherWorkingCapital + '\'' +
                ", otherNonCashItems='" + otherNonCashItems + '\'' +
                ", netCashProvidedByOperatingActivities='" + netCashProvidedByOperatingActivities + '\'' +
                ", investmentsInPropertyPlantAndEquipment='" + investmentsInPropertyPlantAndEquipment + '\'' +
                ", acquisitionsNet='" + acquisitionsNet + '\'' +
                ", purchasesOfInvestments='" + purchasesOfInvestments + '\'' +
                ", salesMaturitiesOfInvestments='" + salesMaturitiesOfInvestments + '\'' +
                ", otherInvestingActivities='" + otherInvestingActivities + '\'' +
                ", netCashUsedForInvestingActivities='" + netCashUsedForInvestingActivities + '\'' +
                ", debtRepayment='" + debtRepayment + '\'' +
                '}';
    }

    public CompanyCashflowStatFormulaConfig() {}

}

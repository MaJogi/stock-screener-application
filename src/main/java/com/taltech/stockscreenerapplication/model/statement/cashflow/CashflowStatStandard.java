package com.taltech.stockscreenerapplication.model.statement.cashflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.StatStandard;
import com.taltech.stockscreenerapplication.model.statement.formula.CashflowStatConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "cashflow_statement_stand")
public class CashflowStatStandard extends StatStandard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashflow_stat_standard_id")
    private Long cashflow_stat_standard_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "cashflow_stat_formula_id") // or maybe joincolumn
    private CashflowStatConfig cashflow_stat_formula_id;

    @Column(name = "net_income")
    private Double netIncome;

    @Column(name = "deprication_and_amortization")
    private Double depriciationAndAmortization;

    @Column(name = "stock_based_compensation")
    private Double stockBasedCompensation;

    @Column(name = "change_in_working_capital")
    private Double changeInWorkingCapital;

    @Column(name = "accounts_receivables")
    private Double accountsReceivables;

    @Column(name = "inventory")
    private Double inventory;

    @Column(name = "accounts_payments")
    private Double accountsPayments;

    @Column(name = "other_working_capital")
    private Double otherWorkingCapital;

    @Column(name = "other_non_cash_items")
    private Double otherNonCashItems;

    @Column(name = "net_cash_provided_by_operating_activities")
    private Double netCashProvidedByOperatingActivities;

    @Column(name = "investments_in_property_plant_and_equipment")
    private Double investmentsInPropertyPlantAndEquipment;

    @Column(name = "acquisitions_net")
    private Double acquisitionsNet;

    @Column(name = "purchases_of_investments")
    private Double purchasesOfInvestments;

    @Column(name = "sales_maturities_of_investments")
    private Double salesMaturitiesOfInvestments;

    @Column(name = "other_investing_activities")
    private Double otherInvestingActivities;

    @Column(name = "net_cash_used_for_investing_activities")
    private Double netCashUsedForInvestingActivities;

    @Column(name = "debt_repayment")
    private Double debtRepayment;

    public CashflowStatStandard() {}


}

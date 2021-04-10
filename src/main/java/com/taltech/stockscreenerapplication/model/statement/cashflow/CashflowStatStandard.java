package com.taltech.stockscreenerapplication.model.statement.cashflow;

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
@Table(name = "cash_flow_statement_standard")
public class CashflowStatStandard {

    @Id
    @Column(name = "cashflow_standard_id")
    private String cashflow_standard_id;

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

    public CashflowStatStandard() {}


}

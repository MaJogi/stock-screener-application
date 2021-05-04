package com.taltech.stockscreenerapplication.model.statement.groupOfStatements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@JsonSerialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "group_of_statements")
public class GroupOfStatements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_of_stats_id")
    private Long group_of_stats_id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //@JoinColumn(name = "income_stat_raw_id") // or maybe joincolumn
    private IncomeStatRaw incomeStatRaw;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL) // When GriuoIfStatements is removed, CashflowStatRaw will be removed also.
    //@JoinColumn(name = "cashflow_stat_raw_id") // or maybe joincolumn
    private CashflowStatRaw cashflowStatRaw;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //@JoinColumn(name = "balance_stat_raw_id") // or maybe joincolumn
    private BalanceStatRaw balanceStatRaw;

    private Boolean allStatementsPresent;

    public GroupOfStatements() { }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension companyDimension;

    @Override
    public String toString() {
        return "GroupOfStatements{" +
                "group_of_stats_id=" + group_of_stats_id +
                ", incomeStatRaw=" + incomeStatRaw +
                ", cashflowStatRaw=" + cashflowStatRaw +
                ", balanceStatRaw=" + balanceStatRaw +
                ", allStatementsPresent=" + allStatementsPresent +
                '}';
    }
}

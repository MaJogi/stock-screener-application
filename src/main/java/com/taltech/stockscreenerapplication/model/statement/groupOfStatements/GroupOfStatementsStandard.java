package com.taltech.stockscreenerapplication.model.statement.groupOfStatements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatStandard;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandard;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatStandard;
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
@Table(name = "group_of_statements_standard")
public class GroupOfStatementsStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_of_stats_standard_id")
    private Long group_of_stats_standard_id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private IncomeStatStandard incomeStat;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL) // When GroupOfStatementsStandard is removed, CashflowStat will be removed also.
    private CashflowStatStandard cashflowStat;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private BalanceStatStandard balanceStat;

    public GroupOfStatementsStandard() { }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id")
    private CompanyDimension companyDimension;

    @Override
    public String toString() {
        return "GroupOfStatementsStandard{" +
                "group_of_stats_standard_id=" + group_of_stats_standard_id +
                ", incomeStat=" + incomeStat +
                ", cashflowStat=" + cashflowStat +
                ", balanceStat=" + balanceStat +
                ", companyDimension=" + companyDimension +
                '}';
    }
}


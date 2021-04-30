package com.taltech.stockscreenerapplication.model.statement;

import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private IncomeStatRaw incomeStatRaw;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL) // When GriuoIfStatements is removed, CashflowStatRaw will be removed also.
    private CashflowStatRaw cashflowStatRaw;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private BalanceStatRaw balanceStatRaw;

    private Boolean allStatementsPresent;

    public GroupOfStatements() { }

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

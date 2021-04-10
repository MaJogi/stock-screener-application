package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "income_statement_raw_eav")
public class IncomeStatRaw_EAV {
    @Id
    @Column(name = "income_stat_raw_eav_id")
    private String income_stat_raw_eav_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "income_stat_raw_id")
    private CashflowStatRaw income_stat_raw_id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "value")
    private double value;

    @Column(name = "type") // by default double value
    private String type;

    public IncomeStatRaw_EAV() {}
}

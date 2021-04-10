package com.taltech.stockscreenerapplication.model.statement.cashflow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "cashflow_statement_raw_eav")
public class CashflowStatRaw_EAV {
    @Id
    @Column(name = "cashflow_raw_eav_id")
    private String cashflow_raw_eav_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "cashflow_raw_id")
    private CashflowStatRaw cashflow_raw_id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "value")
    private double value;

    @Column(name = "type") // by default double value
    private String type;

    public CashflowStatRaw_EAV() {}




}

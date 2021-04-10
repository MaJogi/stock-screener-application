package com.taltech.stockscreenerapplication.model.statement.cashflow;

import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "cash_flow_statement_as_imported")
public class CashflowStatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cashflow_stat_raw_id")
    private Long cashflow_raw_id;

    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Attribute> attributes = new HashSet<>();

    // is it really one to one. Every balance instance can have only one parent csv file
    // (because one csv file can't have multiple balance sheets & balance sheet can't have multiple source files)
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SourceCsvFile sourceCsvFile;


    public CashflowStatRaw() {}

}

package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "income_statement_as_imported")
public class IncomeStatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_raw_id")
    private Long income_stat_raw_id;

    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Attribute> attributes = new HashSet<>();
    */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attribute> attributes;


    // is it really one to one. Every balance instance can have only one parent csv file
    // (because one csv file can't have multiple balance sheets & balance sheet can't have multiple source files)

    /*
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    //@PrimaryKeyJoinColumn
    private SourceCsvFile sourceCsvFile;
     */


    /* Seems like its not necessary
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
    */


    public IncomeStatRaw() {}

    @Override
    public String toString() {
        return "IncomeStatRaw{" +
                "income_stat_raw_id=" + income_stat_raw_id +
                ", dateOrPeriod='" + dateOrPeriod + '\'' +
                ", attributes=" + attributes +
                /*", sourceCsvFile=" + sourceCsvFile + */
                /*", ticker_id=" + ticker_id + */
                '}';
    }
}

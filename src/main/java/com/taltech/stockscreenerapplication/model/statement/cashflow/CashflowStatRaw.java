package com.taltech.stockscreenerapplication.model.statement.cashflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "cash_flow_statement_as_imported")
public class CashflowStatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashflow_stat_raw_id")
    private Long cashflow_stat_raw_id;

    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attribute> attributes;

    public CashflowStatRaw() {}

    @Override
    public String toString() {
        return "CashflowStatRaw{" +
                "cashflow_stat_raw_id=" + cashflow_stat_raw_id +
                ", dateOrPeriod='" + dateOrPeriod + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}

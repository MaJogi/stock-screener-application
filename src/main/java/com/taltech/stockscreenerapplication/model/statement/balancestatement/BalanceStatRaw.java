package com.taltech.stockscreenerapplication.model.statement.balancestatement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@Table(name = "balance_statement_as_imported")
public class BalanceStatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_stat_raw_id")
    private Long balance_stat_raw_id;

    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attribute> attributes;

    public BalanceStatRaw() {}

    @Override
    public String toString() {
        return "BalanceStatRaw{" +
                "balance_stat_raw_id=" + balance_stat_raw_id +
                ", dateOrPeriod='" + dateOrPeriod + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}

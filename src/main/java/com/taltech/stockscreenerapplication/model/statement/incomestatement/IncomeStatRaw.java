package com.taltech.stockscreenerapplication.model.statement.incomestatement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.taltech.stockscreenerapplication.model.statement.StatRaw;
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
@Table(name = "income_statement_as_imported")
public class IncomeStatRaw extends StatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "income_stat_raw_id")
    private Long income_stat_raw_id;

    public IncomeStatRaw() {}

    @Override
    public String toString() {
        return "IncomeStatRaw{" +
                "income_stat_raw_id=" + income_stat_raw_id +
                /*", dateOrPeriod='" + dateOrPeriod + '\'' + */
                /*", attributes=" + attributes + */
                /*", sourceCsvFile=" + sourceCsvFile + */
                /*", ticker_id=" + ticker_id + */
                '}';
    }
}

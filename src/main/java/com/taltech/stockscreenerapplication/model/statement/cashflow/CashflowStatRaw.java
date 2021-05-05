package com.taltech.stockscreenerapplication.model.statement.cashflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.StatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "cash_flow_statement_as_imported")
public class CashflowStatRaw extends StatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashflow_stat_raw_id")
    private Long cashflow_stat_raw_id;

    public CashflowStatRaw() {}

    @Override
    public String toString() {
        return "CashflowStatRaw{" +
                "cashflow_stat_raw_id=" + cashflow_stat_raw_id +
                /*", dateOrPeriod='" + dateOrPeriod + '\'' + */
                /*", attributes=" + attributes + */
                '}';
    }
}

package com.taltech.stockscreenerapplication.model.statement.balancestatement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.StatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Data
@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@Table(name = "balance_statement_as_imported")
public class BalanceStatRaw extends StatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_stat_raw_id")
    private Long balance_stat_raw_id;

    public BalanceStatRaw() {}

    @Override
    public String toString() {
        return "BalanceStatRaw{" +
                "balance_stat_raw_id=" + balance_stat_raw_id +
                /*", dateOrPeriod='" + dateOrPeriod + '\'' + */
                /*", attributes=" + attributes + */
                '}';
    }
}

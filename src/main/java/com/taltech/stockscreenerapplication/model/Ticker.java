package com.taltech.stockscreenerapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticker")
public class Ticker {

    @Id
    private Long Id;

    @Column(name = "ticker")
    @Size(min = 5, max = 5)
    private String ticker;

    @Column(name = "company")
    private String company;

    @Column(name = "last_price")
    private Double last_price;

    @Column(name = "chg_percentage")
    private String chg_percentage;

    @Column(name = "chg")
    private Double chg;

    @Column(name = "rating")
    private String rating;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "mkt_cap")
    private Double mkt_cap;

    @Column(name = "p_e")
    private Double p_e;

    @Column(name = "eps")
    private Double eps;

    @Column(name = "employees")
    private Integer employees;

    @Column(name = "sector")
    private String sector;

    @Override
    public String toString() {
        return "Ticker{" +
                "Id=" + Id +
                ", ticker='" + ticker + '\'' +
                ", company='" + company + '\'' +
                ", last_price=" + last_price +
                ", chg_percentage='" + chg_percentage + '\'' +
                ", chg=" + chg +
                ", rating='" + rating + '\'' +
                ", volume=" + volume +
                ", mkt_cap=" + mkt_cap +
                ", p_e=" + p_e +
                ", eps=" + eps +
                ", employees=" + employees +
                ", sector='" + sector + '\'' +
                '}';
    }
}

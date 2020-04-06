package com.taltech.stockscreenerapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financialsDaily")
public class FinancialsDaily {

    @Id
    @Column(name = "ticker_id")
    private String ticker_Id;

    @Column(name = "div_yield")
    private double div_yield;

    @Column(name = "mkt_cap")
    private double mkt_cap;

    @Column(name = "p_e")
    private double p_e;

    @Column(name = "price_rev")
    private double price_rev;

    @Column(name = "p_b")
    private double p_b;

    @Column(name = "ev_ebitda")
    private double ev_ebitda;

    @Column(name = "ev")
    private double ev;

    @Column(name = "chg")
    private double chg;

    @Column(name = "weekly_perf")
    private double weekly_perf;

    @Column(name = "monthly_perf")
    private double monthly_perf;

    @Column(name = "3_month_perf")
    private double three_month_perf;

    @Column(name = "6_month_perf")
    private double six_month_perf;

    @Column(name = "ytd_perf")
    private double ytd_perf;

    @Column(name = "yearly_perf")
    private double yearly_perf;

    @Column(name = "1_y_beta")
    private double one_year_beta;

    @Column(name = "volatility")
    private double volatility;

    @Override
    public String toString() {
        return "FinancialsDaily{" +
                "ticker_Id='" + ticker_Id + '\'' +
                ", div_yield=" + div_yield +
                ", mkt_cap=" + mkt_cap +
                ", p_e=" + p_e +
                ", price_rev=" + price_rev +
                ", p_b=" + p_b +
                ", ev_ebitda=" + ev_ebitda +
                ", ev=" + ev +
                ", chg=" + chg +
                ", weekly_perf=" + weekly_perf +
                ", monthly_perf=" + monthly_perf +
                ", three_month_perf=" + three_month_perf +
                ", six_month_perf=" + six_month_perf +
                ", ytd_perf=" + ytd_perf +
                ", yearly_perf=" + yearly_perf +
                ", one_year_beta=" + one_year_beta +
                ", volatility=" + volatility +
                '}';
    }
}

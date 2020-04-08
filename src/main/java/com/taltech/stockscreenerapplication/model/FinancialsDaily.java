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
@Table(name = "financials_daily")
public class FinancialsDaily {

    @Id
    @Column(name = "ticker_id")
    private String ticker_id;

    @Column(name = "price")
    private Double price;

    @Column(name = "div_yield")
    private Double div_yield;

    @Column(name = "mkt_cap")
    private Double mkt_cap;

    @Column(name = "p_e")
    private Double p_e;

    @Column(name = "price_rev")
    private Double price_rev;

    @Column(name = "p_b")
    private Double p_b;

    @Column(name = "ev_ebitda")
    private Double ev_ebitda;

    @Column(name = "ev")
    private Double ev;

    @Column(name = "chg")
    private Double chg;

    @Column(name = "weekly_perf")
    private Double weekly_perf;

    @Column(name = "monthly_perf")
    private Double monthly_perf;

    @Column(name = "three_month_perf")
    private Double three_month_perf;

    @Column(name = "six_month_perf")
    private Double six_month_perf;

    @Column(name = "ytd_perf")
    private Double ytd_perf;

    @Column(name = "yearly_perf")
    private Double yearly_perf;

    @Column(name = "one_y_beta")
    private Double one_year_beta;

    @Column(name = "volatility")
    private Double volatility;

    @Override
    public String toString() {
        return "FinancialsDaily{" +
                "ticker_id='" + ticker_id + '\'' +
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

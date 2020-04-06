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
@Table(name = "financialsQuarterly")
public class FinancialsQuarterly {

    @Id
    @Column(name = "ticker_id")
    private String ticker_id;

    @Column(name = "current_ratio")
    private double current_ratio;

    @Column(name = "debt_to_equity")
    private double debt_to_equity;

    @Column(name = "net_debt")
    private double net_debt;

    @Column(name = "quick_ratio")
    private double quick_ratio;

    @Column(name = "assets")
    private double assets;

    @Column(name = "debt")
    private double debt;

    @Column(name = "current_assets")
    private double current_assets;

    @Column(name = "eps_fy")
    private double eps_fy;

    @Column(name = "eps_ttm")
    private double eps_ttm;

    @Column(name = "eps_diluted")
    private double eps_diluted;

    @Column(name = "ebitda")
    private double ebitda;

    @Column(name = "gross_profit_mrq")
    private double gross_profit_mrq;

    @Column(name = "gross_profit_fy")
    private double gross_profit_fy;

    @Column(name = "revenue")
    private double revenue;

    @Column(name = "eps_diluted_fy")
    private double eps_diluted_fy;

    @Column(name = "annual_revenue")
    private double annual_revenue;

    @Column(name = "income")
    private double income;

    @Column(name = "gross_mrq")
    private double gross_mrq;

    @Column(name = "operating_mrq")
    private double operating_mrq;

    @Column(name = "pretax_mrq")
    private double pretax_mrq;

    @Column(name = "net_mrq")
    private double net_mrq;

    @Column(name = "div_paid")
    private double div_paid;

    @Column(name = "div_per_share")
    private double div_per_share;

    @Column(name = "roa")
    private double roa;

    @Column(name = "roe")
    private double roe;

    @Column(name = "shares")
    private double shares;

    @Override
    public String toString() {
        return "FinancialsQuarterly{" +
                "ticker_id='" + ticker_id + '\'' +
                ", current_ratio=" + current_ratio +
                ", debt_to_equity=" + debt_to_equity +
                ", net_debt=" + net_debt +
                ", quick_ratio=" + quick_ratio +
                ", assets=" + assets +
                ", debt=" + debt +
                ", current_assets=" + current_assets +
                ", eps_fy=" + eps_fy +
                ", eps_ttm=" + eps_ttm +
                ", eps_diluted=" + eps_diluted +
                ", ebitda=" + ebitda +
                ", gross_profit_mrq=" + gross_profit_mrq +
                ", gross_profit_fy=" + gross_profit_fy +
                ", revenue=" + revenue +
                ", eps_diluted_fy=" + eps_diluted_fy +
                ", annual_revenue=" + annual_revenue +
                ", income=" + income +
                ", gross_mrq=" + gross_mrq +
                ", operating_mrq=" + operating_mrq +
                ", pretax_mrq=" + pretax_mrq +
                ", net_mrq=" + net_mrq +
                ", div_paid=" + div_paid +
                ", div_per_share=" + div_per_share +
                ", roa=" + roa +
                ", roe=" + roe +
                ", shares=" + shares +
                '}';
    }
}

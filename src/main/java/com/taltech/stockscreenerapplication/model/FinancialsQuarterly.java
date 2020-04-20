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
@Table(name = "financials_quarterly")
public class FinancialsQuarterly {

    @Id
    @Column(name = "ticker_id")
    private String ticker_id;

    @Column(name = "current_ratio")
    private Double current_ratio;

    @Column(name = "debt_to_equity")
    private Double debt_to_equity;

    @Column(name = "net_debt")
    private Double net_debt;

    @Column(name = "quick_ratio")
    private Double quick_ratio;

    @Column(name = "assets")
    private Double assets;

    @Column(name = "debt")
    private Double debt;

    @Column(name = "current_assets")
    private Double current_assets;

    @Column(name = "eps_fy")
    private Double eps_fy;

    @Column(name = "eps_ttm")
    private Double eps_ttm;

    @Column(name = "eps_diluted_ttm")
    private Double eps_diluted_ttm;

    @Column(name = "ebitda")
    private Double ebitda;

    @Column(name = "gross_profit_mrq")
    private Double gross_profit_mrq;

    @Column(name = "gross_profit_fy")
    private Double gross_profit_fy;

    @Column(name = "revenue")
    private Double revenue;

    @Column(name = "eps_diluted_fy")
    private Double eps_diluted_fy;

    @Column(name = "annual_revenue")
    private Double annual_revenue;

    @Column(name = "income")
    private Double income;

    @Column(name = "gross_mrq")
    private Double gross_mrq;

    @Column(name = "operating_mrq")
    private Double operating_mrq;

    @Column(name = "pretax_mrq")
    private Double pretax_mrq;

    @Column(name = "net_mrq")
    private Double net_mrq;

    @Column(name = "div_paid")
    private Double div_paid;

    @Column(name = "div_per_share")
    private Double div_per_share;

    @Column(name = "roa")
    private Double roa;

    @Column(name = "roe")
    private Double roe;

    @Column(name = "shares")
    private Integer shares;

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
                ", eps_diluted=" + eps_diluted_ttm +
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

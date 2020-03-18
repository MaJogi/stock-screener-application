package com.taltech.stockscreenerapplication.model;


import lombok.*;

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
@ToString
@Table(name = "valuation")
public class Valuation {

    @Id
    private Long id;

    @Column(name = "ticker_valuation")
    @Size(min = 5, max = 5)
    private String ticker_valuation;

    @Column(name = "last")
    private Double last;

    @Column(name = "mkt_cap")
    private Double mkt_cap;

    @Column(name = "p_e")
    private Double p_e;

    @Column(name = "price_rev")
    private Double price_rev;

    @Column(name = "eps_ttm")
    private Double eps_ttm;

    @Column(name = "eps_diluted")
    private Double eps_diluted;

    @Column(name = "ev_ebitda")
    private Double ev_ebitda;

    @Column(name = "ev")
    private Double ev;

    @Column(name = "shares")
    private Double shares;
}

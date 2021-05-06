package com.taltech.stockscreenerapplication.model.statement;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class StatStandard {
    @Column(name = "symbol")
    private String symbol;

    @Column(name = "reported_currency")
    private String reportedCurrency;

    @Column(name = "filling_date") // Date.today()
    private Date fillingDate;

    @Column(name = "accepted_date")
    private Date acceptedDate;

    @Column(name = "date_or_period")
    private String datePeriod;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
    */
}

package com.taltech.stockscreenerapplication.model.statement.formula;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class FormulaConfig {

    // Administrator can create a collection of statement configurations, for specific date. For example: 31.06.2017
    @Column(name = "company_config_collection_id")
    private Long company_config_collection_id;

    @Column(name = "date_from")
    private String dateFrom;

    @Column(name = "date_to")
    private String dateTo;

    /*
    @Column(name = "symbol") // which company it belong to
    private String symbol;
     */
}

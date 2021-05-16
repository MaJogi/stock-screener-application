package com.taltech.stockscreenerapplication.model.statement;

import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public class StatRaw {
    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attribute> attributes;

    /* If there is ever need to track where did as is statements come from
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticker_id")
    private CompanyDimension companyDimension;
     */

    public StatRaw() {}
}

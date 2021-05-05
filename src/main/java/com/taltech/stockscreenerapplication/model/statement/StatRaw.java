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

    /* Seems like its not necessary
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
    */

    public StatRaw() {}
}

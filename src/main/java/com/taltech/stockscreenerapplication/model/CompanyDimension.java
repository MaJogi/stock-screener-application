package com.taltech.stockscreenerapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_dimension")
public class CompanyDimension {

    @Id
    @Column(name = "ticker_id")
    private String ticker_id;

    @Column(name = "name")
    private String name;

    @Column(name = "employees")
    private Integer employees;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FinancialsDaily financialsDaily;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private FinancialsQuarterly financialsQuarterly;

    @Override
    public String toString() {
        return "CompanyDimension{" +
                "ticker_id='" + ticker_id + '\'' +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", financialsDaily=" + financialsDaily +
                ", financialsQuarterly=" + financialsQuarterly +
                '}';
    }
}

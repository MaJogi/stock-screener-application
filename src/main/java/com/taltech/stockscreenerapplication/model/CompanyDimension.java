package com.taltech.stockscreenerapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "company_dimension")
public class CompanyDimension {

    @Id
    @Column(name = "ticker_id")
    private String ticker_id;

    @Column(name = "company_name")
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

    /*
        Trial. Maybe will be removed in the future db changes
        Company can have many of this statements
    */

    /*
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<IncomeStatRaw> incomeRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BalanceStatRaw> bilanceRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CashflowStatRaw> cashflowRawStatements = new LinkedList<>();


     */
    public CompanyDimension() { //empty constructor
    }

    @Override
    public String toString() {
        return "CompanyDimension{" +
                "ticker_id='" + ticker_id + '\'' +
                ", company_name='" + name + '\'' +
                ", employees=" + employees +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                ", financialsDaily=" + financialsDaily +
                ", financialsQuarterly=" + financialsQuarterly +
                '}';
    }
}

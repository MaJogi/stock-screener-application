package com.taltech.stockscreenerapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.configuration.BalanceStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.CashflowStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncomeStatRaw> incomeRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceStatRaw> balanceRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CashflowStatRaw> cashflowRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncomeStatConfig> incomeConfigurations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceStatConfig> balanceConfigurations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CashflowStatConfig> cashflowConfigurations = new LinkedList<>();

    public CompanyDimension() { }

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

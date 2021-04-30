package com.taltech.stockscreenerapplication.model;

import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
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
    private List<CompanyIncomeStatFormulaConfig> incomeConfigurations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CompanyBalanceStatFormulaConfig> balanceConfigurations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CompanyCashflowStatFormulaConfig> cashflowConfigurations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncomeStatStandWithValues> incomeStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CashflowStatStandWithValues> cashflowStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceStatStandWithValues> balanceStatements = new LinkedList<>();

    // This can start causing trouble
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // kui firma kustutatakse, siis kõik selle statemendid kaovad.
    private List<GroupOfStatements> groupOfStatements = new LinkedList<>();


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

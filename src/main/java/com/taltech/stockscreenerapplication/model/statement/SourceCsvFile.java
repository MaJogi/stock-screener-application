package com.taltech.stockscreenerapplication.model.statement;

import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
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
@Table(name = "source_csv_file")
public class SourceCsvFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_file_id")
    private Long sourceFileId;

    @Column(name = "source_file_name")
    private String sourceFileName;


    /* This has to work */
    // company this source file belong to

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    //@PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
     */

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IncomeStatRaw> incomeRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BalanceStatRaw> balanceRawStatements = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CashflowStatRaw> cashflowRawStatements = new LinkedList<>();

    public SourceCsvFile() {}
}

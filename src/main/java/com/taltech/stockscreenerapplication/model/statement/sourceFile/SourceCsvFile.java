package com.taltech.stockscreenerapplication.model.statement.sourceFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    // company this source file belong to
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    //@PrimaryKeyJoinColumn(name = "ticker_id") // or maybe joincolumn
    private CompanyDimension ticker_id;
     */

    private String ticker_id;

    public SourceCsvFile() {}
}

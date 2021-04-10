package com.taltech.stockscreenerapplication.model.statement.balancestatement;

import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "balance_statement_as_imported")
public class BalanceStatRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "balance_stat_raw_id")
    private Long balance_stat_raw_id;

    @Column(name = "date_or_period") // I mean quarter, year or specific date
    private String dateOrPeriod; // Q1 2017, 2018 etc

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Attribute> attributes = new LinkedList<>();

    // is it really one to one. Every balance instance can have only one parent csv file
    // (because one csv file can't have multiple balance sheets & balance sheet can't have multiple source files)
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SourceCsvFile sourceCsvFile;

    /*
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CompanyDimension company;
    */

    public BalanceStatRaw(String dateOrPeriod, List<Attribute> attributes) {
        this.dateOrPeriod = dateOrPeriod;
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add(attribute);
    }

    public Map<String, Double> getAttributesMap() {
        Map<String, Double> map = new HashMap<>();
        attributes.forEach(attr ->
                map.put(attr.getFieldName(), attr.getValue())
        );
        return map;
    }

    public Map<String, Object> getBasicFieldsMap() {
        Map<String, Object>  fieldsMap = new HashMap<>();
        fieldsMap.put("period", dateOrPeriod);
        //fieldsMap.put("anotherOne", anotherOne);

        return fieldsMap;
    }
}

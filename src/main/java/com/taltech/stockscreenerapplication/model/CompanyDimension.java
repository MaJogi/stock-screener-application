package com.taltech.stockscreenerapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companyDimension")
public class CompanyDimension {

    @Id
    @Column(name = "ticker_id")
    private long ticker_id;

    @Column(name = "name")
    private String name;

    @Column(name = "employees")
    private Integer employees;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @Override
    public String toString() {
        return "CompanyDimension{" +
                "ticker_id=" + ticker_id +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", sector='" + sector + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}

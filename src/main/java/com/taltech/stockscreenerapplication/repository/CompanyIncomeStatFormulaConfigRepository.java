package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIncomeStatFormulaConfigRepository extends JpaRepository<CompanyIncomeStatFormulaConfig, Long> {
}

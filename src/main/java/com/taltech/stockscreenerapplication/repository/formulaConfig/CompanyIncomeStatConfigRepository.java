package com.taltech.stockscreenerapplication.repository.formulaConfig;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIncomeStatConfigRepository extends JpaRepository<CompanyIncomeStatFormulaConfig, Long> {
}

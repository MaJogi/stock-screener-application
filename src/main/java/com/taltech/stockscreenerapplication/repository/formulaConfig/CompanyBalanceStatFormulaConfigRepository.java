package com.taltech.stockscreenerapplication.repository.formulaConfig;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBalanceStatFormulaConfigRepository extends JpaRepository<CompanyBalanceStatFormulaConfig, Long> {
}

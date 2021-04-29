package com.taltech.stockscreenerapplication.repository.formulaConfig;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCashflowStatFormulaConfigRepository extends JpaRepository<CompanyCashflowStatFormulaConfig, Long> {
}

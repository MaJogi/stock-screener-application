package com.taltech.stockscreenerapplication.repository.formulaConfig;

import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCashflowStatConfigRepository
        extends JpaRepository<CompanyCashflowStatFormulaConfig, Long> { }

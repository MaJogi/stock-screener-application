package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeStatRawRepository extends JpaRepository<IncomeStatRaw, Long> { }

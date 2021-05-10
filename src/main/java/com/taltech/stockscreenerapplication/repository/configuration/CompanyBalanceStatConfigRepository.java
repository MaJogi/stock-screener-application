package com.taltech.stockscreenerapplication.repository.configuration;

import com.taltech.stockscreenerapplication.model.statement.formula.BalanceStatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBalanceStatConfigRepository
        extends JpaRepository<BalanceStatConfig, Long> { }

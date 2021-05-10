package com.taltech.stockscreenerapplication.repository.configuration;

import com.taltech.stockscreenerapplication.model.statement.configuration.CashflowStatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCashflowStatConfigRepository
        extends JpaRepository<CashflowStatConfig, Long> { }

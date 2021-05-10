package com.taltech.stockscreenerapplication.repository.configuration;

import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIncomeStatConfigRepository extends JpaRepository<IncomeStatConfig, Long> {
}

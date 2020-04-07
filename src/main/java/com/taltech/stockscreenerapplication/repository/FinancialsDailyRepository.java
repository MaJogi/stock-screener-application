package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialsDailyRepository extends JpaRepository<FinancialsDaily, String> {
}

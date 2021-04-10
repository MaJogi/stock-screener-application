package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashflowStatRawRepository extends JpaRepository<CashflowStatRaw, Long> {
}

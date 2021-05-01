package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupOfStatementsRepository extends JpaRepository<GroupOfStatements, Long> {
    List<GroupOfStatements> findGroupOfStatementsByIncomeStatRawNotNullAndCashflowStatRawIsNotNullAndBalanceStatRawIsNotNullAndCompanyDimensionIs(CompanyDimension ticker_id);
}

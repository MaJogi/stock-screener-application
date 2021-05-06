package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatementsStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupOfStandardStatementsRepository extends JpaRepository<GroupOfStatementsStandard, Long> {

    List<GroupOfStatementsStandard> findAllByCompanyDimensionIs(CompanyDimension ticker_id);

    GroupOfStatementsStandard findByBalanceStat_DatePeriodIs(String dateOrPeriod);
}


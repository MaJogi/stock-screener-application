package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupOfStatementsRepository extends JpaRepository<GroupOfStatements, Long> {
    @Query(value = "SELECT * FROM group_of_statements\n" +
            "WHERE ticker_id = ?1\n" +
            "AND balance_stat_raw_balance_stat_raw_id IS NOT NULL\n" +
            "AND cashflow_stat_raw_cashflow_stat_raw_id IS NOT NULL\n" +
            "AND income_stat_raw_income_stat_raw_id IS NOT NULL", nativeQuery = true)
    List<GroupOfStatements> findGroupOfStatementsWhereEveryStatementIsPresent(String ticker_id);


}

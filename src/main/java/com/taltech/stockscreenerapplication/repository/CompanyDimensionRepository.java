package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyDimensionRepository extends JpaRepository<CompanyDimension, String> {
    @Query(value = "SELECT income_stat_raw_id from income_statement_as_imported\n" +
            "LEFT JOIN company_dimension_income_raw_statements\n" +
            "ON income_statement_as_imported.income_stat_raw_id = company_dimension_income_raw_statements.income_raw_statements_income_stat_raw_id\n" +
            "WHERE date_or_period = ?1\n" +
            "AND company_dimension_ticker_id = ?2", nativeQuery = true)
    Long findIncomeRawIdByDateOrPeriodSpecificCompany(String dateOrPeriod, String companyTicker);

    @Query(value = "SELECT cashflow_stat_raw_id from cash_flow_statement_as_imported\n" +
            "LEFT JOIN company_dimension_cashflow_raw_statements\n" +
            "ON cash_flow_statement_as_imported.cashflow_stat_raw_id = company_dimension_cashflow_raw_statements.cashflow_raw_statements_cashflow_stat_raw_id\n" +
            "WHERE date_or_period = ?1\n" +
            "AND company_dimension_ticker_id = ?2", nativeQuery = true)
    Long findCashflowRawIdByDateOrPeriodSpecificCompany(String dateOrPeriod, String companyTicker);

    @Query(value = "SELECT balance_stat_raw_id from balance_statement_as_imported\n" +
            "LEFT JOIN company_dimension_balance_raw_statements\n" +
            "ON balance_statement_as_imported.balance_stat_raw_id = company_dimension_balance_raw_statements.balance_raw_statements_balance_stat_raw_id\n" +
            "WHERE date_or_period = ?1\n" +
            "AND company_dimension_ticker_id = ?2", nativeQuery = true)
    Long findBalanceRawIdByDateOrPeriodSpecificCompany(String dateOrPeriod, String companyTicker);



    @Query(value = "SELECT group_of_stats_id from group_of_statements\n" +
            "LEFT JOIN company_dimension_group_of_statements\n" +
            "ON group_of_statements.group_of_stats_id = company_dimension_group_of_statements.group_of_statements_group_of_stats_id\n" +
            "WHERE income_stat_raw_income_stat_raw_id IS NOT NULL\n" +
            "AND cashflow_stat_raw_cashflow_stat_raw_id IS NOT NULL\n" +
            "AND balance_stat_raw_balance_stat_raw_id IS NOT NULL\n" +
            "AND company_dimension_ticker_id = ?1", nativeQuery = true)
    List<Long> findAllCompanyGroupOfStatementsWhereAllStatementsPresent(String companyTicker);

    @Query(value = "SELECT * from group_of_statements\n" +
            "WHERE income_stat_raw_income_stat_raw_id IN ?1", nativeQuery = true)
    List<GroupOfStatements> findAllCompanyGroupOfStatements(List<Long> ids);

    @Query(value = "SELECT * from group_of_statements\n" +
            "WHERE income_stat_raw_income_stat_raw_id IN ?1", nativeQuery = true)
    List<GroupOfStatements> findAllCompanyCashflowStatements(List<Long> ids);

    /*
    @Query(value = "SELECT group_of_stats_id from group_of_statements\n" +
            "LEFT JOIN company_dimension_group_of_statements\n" +
            "ON group_of_statements.group_of_stats_id = company_dimension_group_of_statements.group_of_statements_group_of_stats_id\n" +
            "WHERE group_of_statements.incomeStatRaw IS NOT NULL\n" +
            "AND group_of_statements.cashflowStatRaw IS NOT NULL\n" +
            "AND group_of_statements.balanceStatRaw IS NOT NULL\n" +
            "AND company_dimension_ticker_id = ?1", nativeQuery = true)
    List<Long> findCompanyGroupOfStatementsWhereAllStatementsPresentIds(String companyTicker);

     */



    //income_stat_raw_id, date_or_period, company_dimension_ticker_id
}


    /*
    @Transactional
    @Modifying
    @Query(value = "UPDATE financials_daily SET price = ?1 WHERE ticker_id = ?2", nativeQuery = true)
    void updatePrice(Double price, String tickerId);
    */

    /*
    @Transactional
    @Modifying
    @Query(value = "UPDATE financials_daily SET p_e = :pe WHERE ticker_id = :tickerId", nativeQuery = true)
    void updatePE(@Param("pe") Double pe, @Param("tickerId") String tickerId);

     */
/*
    SELECT income_stat_raw_id, date_or_period, company_dimension_ticker_id from income_statement_as_imported
    LEFT JOIN company_dimension_income_raw_statements
        ON income_statement_as_imported.income_stat_raw_id = company_dimension_income_raw_statements.income_raw_statements_income_stat_raw_id
        WHERE date_or_period = 'Q2 2016'
        AND company_dimension_ticker_id = 'TKM1T'

        If input -> dateOrPeriod: 'Q2 2016', companyTicker: 'TKM1T'
        Returns: income_stat_raw_id=2, date_or_period='Q2 2016', company_dimension_ticker_id='TKM1T'
 */


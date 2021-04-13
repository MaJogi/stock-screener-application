package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyDimensionRepository extends JpaRepository<CompanyDimension, String> {
    @Query(value = "SELECT income_stat_raw_id from income_statement_as_imported\n" +
            "LEFT JOIN company_dimension_income_raw_statements\n" +
            "ON income_statement_as_imported.income_stat_raw_id = company_dimension_income_raw_statements.income_raw_statements_income_stat_raw_id\n" +
            "WHERE date_or_period = ?1\n" +
            "AND company_dimension_ticker_id = ?2", nativeQuery = true)
    Long findByDateOrPeriodSpecificCompany(String dateOrPeriod, String companyTicker);

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


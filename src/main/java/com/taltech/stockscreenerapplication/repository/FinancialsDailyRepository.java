package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FinancialsDailyRepository extends JpaRepository<FinancialsDaily, String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE financials_daily SET price = ?1 WHERE ticker_id = ?2", nativeQuery = true)
    void updatePrice(Double price, String tickerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE financials_daily SET p_e = :pe WHERE ticker_id = :tickerId", nativeQuery = true)
    void updatePE(@Param("pe") Double pe, @Param("tickerId") String tickerId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE financials_daily SET div_yield = :divYield WHERE ticker_id = :tickerId", nativeQuery = true)
    void updateDividendYield(@Param("divYield") Double divYield, @Param("tickerId") String tickerId);

}

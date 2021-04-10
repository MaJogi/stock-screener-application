package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceCsvFileRepository extends JpaRepository<SourceCsvFile, String> {

    /*
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
    */




}


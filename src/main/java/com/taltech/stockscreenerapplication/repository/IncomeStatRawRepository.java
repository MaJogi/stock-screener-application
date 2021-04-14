package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeStatRawRepository extends JpaRepository<IncomeStatRaw, Long> {
    //List<IncomeStatRaw> findByDateOrPeriod(String dateOrPeriod);

}

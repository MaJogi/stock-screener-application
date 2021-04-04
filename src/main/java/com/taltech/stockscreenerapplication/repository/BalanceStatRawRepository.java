package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceStatRawRepository extends JpaRepository<BalanceStatRaw, String> { }

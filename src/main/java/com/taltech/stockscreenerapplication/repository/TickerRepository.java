package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Long> {
}

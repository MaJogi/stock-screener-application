package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.Valuation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValuationRepository extends JpaRepository<Valuation, Long> {
}

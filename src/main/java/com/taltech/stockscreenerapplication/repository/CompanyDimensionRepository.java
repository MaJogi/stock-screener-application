package com.taltech.stockscreenerapplication.repository;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDimensionRepository extends JpaRepository<CompanyDimension, String> {
}

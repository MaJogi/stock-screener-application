package com.taltech.stockscreenerapplication.batch;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyDimensionDBWriter implements ItemWriter<CompanyDimension> {

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Override
    public void write(List<? extends CompanyDimension> companyDimensions) throws Exception {
        companyDimensionRepository.saveAll(companyDimensions);
    }
}

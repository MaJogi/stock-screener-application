package com.taltech.stockscreenerapplication.batch;

import com.taltech.stockscreenerapplication.model.Valuation;
import com.taltech.stockscreenerapplication.repository.ValuationRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValuationDBWriter implements ItemWriter<Valuation> {

    @Autowired
    ValuationRepository valuationRepository;

    @Override
    public void write(List<? extends Valuation> valuations) throws Exception {

        valuationRepository.saveAll(valuations);
    }
}

package com.taltech.stockscreenerapplication.batch;

import com.taltech.stockscreenerapplication.model.FinancialsQuarterly;
import com.taltech.stockscreenerapplication.repository.FinancialsQuarterlyRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinancialsQuarterlyDBWriter implements ItemWriter<FinancialsQuarterly> {

    @Autowired
    private FinancialsQuarterlyRepository financialsQuarterlyRepository;

    @Override
    public void write(List<? extends FinancialsQuarterly> financialsQuarterlies) {
        financialsQuarterlyRepository.saveAll(financialsQuarterlies);
    }
}

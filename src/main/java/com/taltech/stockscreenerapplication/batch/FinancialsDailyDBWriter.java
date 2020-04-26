package com.taltech.stockscreenerapplication.batch;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.repository.FinancialsDailyRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FinancialsDailyDBWriter implements ItemWriter<FinancialsDaily> {

    @Autowired
    private FinancialsDailyRepository financialsDailyRepository;

    @Override
    public void write(List<? extends FinancialsDaily> financialsDailies) {
        financialsDailyRepository.saveAll(financialsDailies);
    }
}

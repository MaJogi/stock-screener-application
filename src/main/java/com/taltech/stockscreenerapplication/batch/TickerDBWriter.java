package com.taltech.stockscreenerapplication.batch;

import com.taltech.stockscreenerapplication.model.Ticker;
import com.taltech.stockscreenerapplication.repository.TickerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TickerDBWriter implements ItemWriter<Ticker> {

    @Autowired
    private TickerRepository tickerRepository;

    @Override
    public void write(List<? extends Ticker> tickers) throws Exception {
        tickerRepository.saveAll(tickers);
    }
}

package com.taltech.stockscreenerapplication.util.listener;

import com.taltech.stockscreenerapplication.service.consumer.TickerPriceConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(0)
public class TickerPriceConsumerListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickerPriceConsumerListener.class);

    @Autowired
    /* default */ TickerPriceConsumer tickerPriceConsumer;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            tickerPriceConsumer.updatePrices();
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Exception occurred when trying to update the ticker prices: {}", e.getMessage());
            }
        }
    }
}

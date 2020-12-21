package com.taltech.stockscreenerapplication.service.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Timer ;
import java.util.TimerTask ;

@Service
public class CalculatorTimer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorTimer.class);

    private final Timer timer = new Timer();

    @Autowired
    /* default */ Calculator calculator;

    public void startCalculations() {
        int calculationInterval = 15_000;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                calculator.updatePE();
                calculator.updateDividendYield();

                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Calculations are done at {}", date);
                }
            }
        }, 10L * 1000, calculationInterval);
    }
}


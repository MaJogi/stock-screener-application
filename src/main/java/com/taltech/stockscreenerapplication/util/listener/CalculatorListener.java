package com.taltech.stockscreenerapplication.util.listener;

import com.taltech.stockscreenerapplication.service.calculator.CalculatorTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class CalculatorListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    /* default */ CalculatorTimer calculatorTimer;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        calculatorTimer.startCalculations();
    }
}

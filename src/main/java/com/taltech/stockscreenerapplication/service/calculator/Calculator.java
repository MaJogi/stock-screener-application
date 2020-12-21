package com.taltech.stockscreenerapplication.service.calculator;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.model.FinancialsQuarterly;
import com.taltech.stockscreenerapplication.repository.FinancialsDailyRepository;
import com.taltech.stockscreenerapplication.repository.FinancialsQuarterlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.math3.util.Precision.round;

@Service
public class Calculator {

    @Autowired
    /* default */ FinancialsDailyRepository financialsDailyRepository;

    @Autowired
    /* default */ FinancialsQuarterlyRepository financialsQuarterlyRepository;

    public void updatePE() {
        Map<String, Double> tickerPriceMap = getTickerPriceMap();
        Map<String, Double> epsValuesMap = getEpsValuesMap();
        Map<String, Double> calculatedPeMap = doCalculations(tickerPriceMap, epsValuesMap);

        for (Map.Entry<String, Double>  ticker : calculatedPeMap.entrySet()) {
            financialsDailyRepository.updatePE(ticker.getValue(), ticker.getKey());
        }
    }

    public void updateDividendYield() {
        Map<String, Double> tickerPriceMap = getTickerPriceMap();
        Map<String, Double> dividendPaidPerShareMap = getDividendPaidPerShareMap();
        Map<String, Double> calculatedDividendYield = doCalculations(dividendPaidPerShareMap, tickerPriceMap);

        for (Map.Entry<String, Double>  ticker : calculatedDividendYield.entrySet()) {
            financialsDailyRepository.updateDividendYield(ticker.getValue(), ticker.getKey());
        }
    }

    private Map<String, Double> getTickerPriceMap() {
        Iterable<FinancialsDaily> results = financialsDailyRepository.findAll();

        Map<String, Double> tickerPriceMap = new HashMap<>();
        for (FinancialsDaily financialsDaily : results) {
            tickerPriceMap.put(financialsDaily.getTicker_id(), financialsDaily.getPrice());
        }
        return tickerPriceMap;
    }

    private Map<String, Double> getEpsValuesMap() {
        Iterable<FinancialsQuarterly> results = financialsQuarterlyRepository.findAll();

        Map<String, Double> epsValuesMap = new HashMap<>();
        for (FinancialsQuarterly financialsQuarterly : results) {
            epsValuesMap.put(financialsQuarterly.getTicker_id(), financialsQuarterly.getEps_ttm());
        }
        return epsValuesMap;
    }

    private Map<String, Double> getDividendPaidPerShareMap() {
        Iterable<FinancialsQuarterly> results = financialsQuarterlyRepository.findAll();

        Map<String, Double> dividendPaidPerShareMap = new HashMap<>();
        for (FinancialsQuarterly financialsQuarterly : results) {
            dividendPaidPerShareMap.put(financialsQuarterly.getTicker_id(), financialsQuarterly.getDiv_per_share());
        }
        return dividendPaidPerShareMap;
    }

    private static Map<String, Double> doCalculations(final Map<String, Double> firstSet, final Map<String, Double> secondSet) {
        Map<String, Double> calculatedSet = new HashMap<>();

        for (Map.Entry<String, Double> firstEntry : firstSet.entrySet()) {
            String key = firstEntry.getKey();
            Double value1 = firstEntry.getValue();
            Double value2 = secondSet.get(key);

            if (value1 != null && value2 != null && value2 != 0) {
                Double division = value1 / value2;
                Double rounded = round(division, 2);
                calculatedSet.put(key, rounded);
            }
        }
        return calculatedSet;
    }
}
package com.taltech.stockscreenerapplication;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.repository.financials.FinancialsDailyRepository;
import com.taltech.stockscreenerapplication.service.calculator.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class CalculatorTests {

    @Autowired
    /* default */ FinancialsDailyRepository financialsDailyRepository;

    @Autowired
    /* default */ Calculator calculator;

    @Test
    void doCalculationsTest() {
        Map<String, Double> firstDivisionValue = new HashMap<>();
        firstDivisionValue.put("TKM1T", 9.12);
        Map<String, Double> secondDivisionValue = new HashMap<>();
        secondDivisionValue.put("TKM1T", 3.00);

        Map<String, Double> results = calculator.doCalculations(firstDivisionValue, secondDivisionValue);

        assertThat("The result of division does not match with expected value.", results.get("TKM1T"), equalTo(3.04 ));
    }

    @Test
    void removeInvalidValuesTest() {
        Map<String, Double> firstDivisionValue = new HashMap<>();
        firstDivisionValue.put("TKM1T", 9.12);
        firstDivisionValue.put("ARC1T", null);
        firstDivisionValue.put("LHV1T", 9.12);
        firstDivisionValue.put("BLT1T", 9.12);
        Map<String, Double> secondDivisionValue = new HashMap<>();
        secondDivisionValue.put("TKM1T", 3.00);
        secondDivisionValue.put("ARC1T", 5.11);
        secondDivisionValue.put("LHV1T", null);
        secondDivisionValue.put("BLT1T", 0.00);

        Map<String, Double> results = calculator.doCalculations(firstDivisionValue, secondDivisionValue);

        assertThat("The size of Map with correct values does not match with the expected size.", results.size(), equalTo(1));
    }

    @Test
    void verifyTickerValueMapSizeTest() {
        Map<String, Double> tickerValues = calculator.getTickerPriceMap();
        int companiesCount = 16;

        assertThat("The size of ticker value Map does not match with the expected.", tickerValues.size(), equalTo(companiesCount));
    }

    @Test
    void verifyEpsValueMapSizeTest() {
        Map<String, Double> epsValues = calculator.getEpsValuesMap();
        int companiesCount = 16;

        assertThat("The size of eps value Map does not match with the expected.", epsValues.size(), equalTo(companiesCount));
    }

    @Test
    void verifyDividendPaidByShareMapSizesTest() {
        Map<String, Double> dividendPaidPerShareValues = calculator.getDividendPaidPerShareMap();
        int companiesCount = 16;

        assertThat("The size of dividend paid per share value Map does not match with the expected.", dividendPaidPerShareValues.size(), equalTo(companiesCount));
    }

    @Test
    void updateValueTest() {
        Map<String, Double> calculatedValue = new HashMap<>();
        calculatedValue.put("TKM1T", 9.12);

        financialsDailyRepository.updatePE(calculatedValue.get("TKM1T"), "TKM1T");
        FinancialsDaily financialsDaily = financialsDailyRepository.findById("TKM1T").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThat("The database PE record is not updated to the expected value.", financialsDaily.getP_e(), equalTo(9.12));
    }
}

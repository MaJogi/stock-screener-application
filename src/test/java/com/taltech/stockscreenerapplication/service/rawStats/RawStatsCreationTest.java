package com.taltech.stockscreenerapplication.service.rawStats;

import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class RawStatsCreationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawStatsCreation.class);
    private static RawStatsCreation rawStatsCreation;

    @BeforeEach
    void init() {
        LOGGER.info("@BeforeEach - executes before each test method in this class");
        rawStatsCreation = new RawStatsCreation();
    }

    @Test
    void parseNumToDoubleSuccess() {
        List<String> dataLine = Arrays.asList("1", "1,52", "1,62", "16169012",
                "185919616116216,12", "166185919616116216,1216177273377347737");
        List<Double> resultLine = Arrays.asList(1d, 1.52, 1.62, 16169012d,
                185919616116216.12, 166185919616116216.1216177273377347737);

        for (int i = 0; i < dataLine.size(); i++) {
            Double result = RawStatsCreation.parseNumToDouble(dataLine, i);
            Assert.assertEquals(resultLine.get(i), result); // expected, actual
        }
    }
    @Test
    void parseNumToDoubleParseException() {
        List<String> dataLine = Arrays.asList("", "two hundred", "&/)%#¤");
        List<Double> resultLine = Arrays.asList(-1d, -1d, -1d);

        for (int i = 0; i < dataLine.size(); i++) {
            Double result = RawStatsCreation.parseNumToDouble(dataLine, i);
            Assert.assertEquals(resultLine.get(i), result); // expected, actual
        }
    }

    @Test
    void iterateDataLinesAndCreateFinStatementAttrs() {
        List<List<String>> statementListAttributesWithData = new LinkedList<>();
        List<Attribute> currentPeriodAttributes = new LinkedList<>();
        int i = 1;
        
        statementListAttributesWithData.add(
                new LinkedList<>(Arrays.asList("Cash and cash equivalents()", "5,774", "32,375")));
        statementListAttributesWithData.add(
                new LinkedList<>(Arrays.asList("Trade and other receivables-", "12,139", "15,396")));
        statementListAttributesWithData.add(
                new LinkedList<>(Arrays.asList("      Inventories      ", "69,579", "70,186")));
        statementListAttributesWithData.add(
                new LinkedList<>(Arrays.asList("Total current,     assets", "87,492", "117,957")));

        RawStatsCreation.iterateDataLinesAndCreateFinStatementAttrs(statementListAttributesWithData,
                currentPeriodAttributes, i);
        
        Assert.assertEquals(currentPeriodAttributes.get(0).getFieldName(), "Cash and cash equivalents");
        Assert.assertEquals(currentPeriodAttributes.get(0).getValue(), 5.774, 1);

        Assert.assertEquals(currentPeriodAttributes.get(1).getFieldName(), "Trade and other receivables");
        Assert.assertEquals(currentPeriodAttributes.get(1).getValue(), 12.139, 1);

        Assert.assertEquals(currentPeriodAttributes.get(2).getFieldName(), "Inventories");
        Assert.assertEquals(currentPeriodAttributes.get(2).getValue(), 69.579, 1);

        Assert.assertEquals(currentPeriodAttributes.get(3).getFieldName(), "Total current assets");
        Assert.assertEquals(currentPeriodAttributes.get(3).getValue(), 87.492, 1);
    }

    @Test
    void createNewFinStatementForSpecPeriod() {
        // should be tested via controller
    }

    @Test
    void customIncomeRawGet() {
        IncomeStatRaw incomeStatRaw = new IncomeStatRaw();
        rawStatsCreation.currentRawIncomeList.add(incomeStatRaw);

        Assert.assertEquals(rawStatsCreation.customIncomeRawGet(0), incomeStatRaw);
        Assert.assertNotNull(rawStatsCreation.customIncomeRawGet(0));
        Assert.assertNull(rawStatsCreation.customIncomeRawGet(1));
    }

    @Test
    void customCashflowRawGet() {
        CashflowStatRaw cashflowStatRaw = new CashflowStatRaw();
        rawStatsCreation.currentRawCashflowList.add(cashflowStatRaw);

        Assert.assertEquals(rawStatsCreation.customCashflowRawGet(0), cashflowStatRaw);
        Assert.assertNotNull(rawStatsCreation.customCashflowRawGet(0));
        Assert.assertNull(rawStatsCreation.customCashflowRawGet(1));
    }

    @Test
    void customBalanceRawGet() {
        BalanceStatRaw balanceStatRaw = new BalanceStatRaw();
        rawStatsCreation.currentRawBalanceList.add(balanceStatRaw);

        Assert.assertEquals(rawStatsCreation.customBalanceRawGet(0), balanceStatRaw);
        Assert.assertNotNull(rawStatsCreation.customBalanceRawGet(0));
        Assert.assertNull(rawStatsCreation.customBalanceRawGet(1));
    }

    @Test
    void findMaxLengthOfStatementsInFile() {
        rawStatsCreation.currentRawIncomeList.add(new IncomeStatRaw());
        rawStatsCreation.currentRawIncomeList.add(new IncomeStatRaw());
        rawStatsCreation.currentRawIncomeList.add(new IncomeStatRaw());
        rawStatsCreation.currentRawIncomeList.add(new IncomeStatRaw());

        rawStatsCreation.currentRawCashflowList.add(new CashflowStatRaw());
        rawStatsCreation.currentRawCashflowList.add(new CashflowStatRaw());
        rawStatsCreation.currentRawCashflowList.add(new CashflowStatRaw());
        rawStatsCreation.currentRawCashflowList.add(new CashflowStatRaw());

        rawStatsCreation.currentRawBalanceList.add(new BalanceStatRaw());
        rawStatsCreation.currentRawBalanceList.add(new BalanceStatRaw());

        int maxLength = rawStatsCreation.findMaxLengthOfStatementsInFile();
        Assert.assertEquals(maxLength, 4);
    }

    @Test
    void createGroupsOfStatementsForCompany() {
        // Requires repository action to test. Maybe should be tested in controller.
    }
}
package com.taltech.stockscreenerapplication.service.StatementsToDb;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class StatementsToDbHelperImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatementsToDbHelperImpl.class);

    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;

    @Autowired
    private IncomeStatRawRepository incomeStatRawRepository;

    public List<IncomeStatRaw> currentCsvIncomeRawList;
    public List<CashflowStatRaw> currentCsvCashflowRawList;
    public List<BalanceStatRaw> currentCsvBalanceRawList;

    public StatementsToDbHelperImpl() {
        currentCsvIncomeRawList = new LinkedList<>();
        currentCsvCashflowRawList = new LinkedList<>();
        currentCsvBalanceRawList = new LinkedList<>();
    }

    public static double parseNumToDouble(List<String> dataLine, int j) {
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number valueNum = format.parse(dataLine.get(j));
            return valueNum.doubleValue();
        }
        catch (ParseException e) {
            LOGGER.error("PARSEEXCEPTION <-------------------, value is set to -1");
            return -1;
        }
    }

    public static void iterateDataLinesAndCreateFinStatementAttrs(List<List<String>> incomeListAttributesWithData,
                                                                  List<Attribute> currentPeriodAttributes, int i){
        //[Revenue (note: 16), 164,645, 150,534, 315,333, 287,384]
        for (List<String> dataLine : incomeListAttributesWithData) {
            Attribute attr = new Attribute();
            attr.setFieldName(dataLine.get(0).replace(',', ' ')
                    .replace('(', ' ')
                    .replace(')', ' ')
                    .replace('-', ' ')
                    .replaceAll("\\s+", " ") // replace extra whitespaces with one.
                    .trim()
                    );

            // Parsing "," to "."
            double valueDouble = parseNumToDouble(dataLine, i);
            attr.setValue(valueDouble);

            // adding new attribute to current attributes list
            currentPeriodAttributes.add(attr);
        }
    }

    public void createNewCashflowFinStatementForSpecPeriod(List<String> cashflowListDateEntries,
                                                           List<List<String>> cashflowListAttributesWithData,
                                                           CompanyDimension company,
                                                           SourceCsvFile newSourceFile) {
        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : cashflowListDateEntries) {
            // Creating raw cashflow statement object for specific period (Q2 2017)
            CashflowStatRaw newCashflowStatRaw = new CashflowStatRaw();

            // Setting current period for raw cashflow statement
            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newCashflowStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(cashflowListAttributesWithData, currentPeriodAttributes, i);
            newCashflowStatRaw.setAttributes(currentPeriodAttributes);
            cashflowStatRawRepository.save(newCashflowStatRaw);
            // new
            currentCsvCashflowRawList.add(newCashflowStatRaw);
            // end new
            company.getCashflowRawStatements().add(newCashflowStatRaw);

            newSourceFile.getCashflowRawStatements().add(newCashflowStatRaw);

            i++;
        }
    }

    public void createNewFinStatementForSpecPeriod(List<String> finStatementListDateEntries,
                                                   List<List<String>> finStatementListAttributesWithData,
                                                   CompanyDimension company) {
        // TODO instead of different method for each one

        /*
        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : finStatementListDateEntries) {
            // Creating raw income statement object for specific period (Q2 2017)

            // how to choose type
            CashflowStatRaw newFinStatementRaw = new CashflowStatRaw();

            // Setting current period for raw income statement
            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newFinStatementRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(finStatementListAttributesWithData, currentPeriodAttributes, i);
            newFinStatementRaw.setAttributes(currentPeriodAttributes);

            cashflowStatRawRepository.save(newFinStatementRaw);



            company.get().getCashflowRawStatements().add(newFinStatementRaw);



            i++;
        }

         */
    }

    public void createNewBalanceFinStatementForSpecPeriod(List<String> balanceListDateEntries,
                                                          List<List<String>> balanceListAttributesWithData,
                                                          CompanyDimension company,
                                                          SourceCsvFile newSourceFile) {
        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : balanceListDateEntries) {
            // Creating raw balance statement object for specific period (Q2 2017)
            BalanceStatRaw newBalanceStatRaw = new BalanceStatRaw();

            // Setting current period for raw income statement
            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newBalanceStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(balanceListAttributesWithData, currentPeriodAttributes, i);
            newBalanceStatRaw.setAttributes(currentPeriodAttributes);

            balanceStatRawRepository.save(newBalanceStatRaw);

            // new
            currentCsvBalanceRawList.add(newBalanceStatRaw);
            // end new

            company.getBalanceRawStatements().add(newBalanceStatRaw);

            newSourceFile.getBalanceRawStatements().add(newBalanceStatRaw);

            i++;
        }
    }

    public void createNewIncomeFinStatementForSpecPeriod(List<String> incomeListDateEntries,
                                                         List<List<String>> incomeListAttributesWithData,
                                                         CompanyDimension company,
                                                         SourceCsvFile newSourceFile) {

        // Making sure object used as singleton is clean and nothing from previous csv file is present this time.
        // Must be done!
        currentCsvIncomeRawList = new LinkedList<>();
        currentCsvCashflowRawList = new LinkedList<>();
        currentCsvBalanceRawList = new LinkedList<>();

        int i = 1;
        for (String dateEntry : incomeListDateEntries) {
            IncomeStatRaw newIncomeStatRaw = new IncomeStatRaw();

            LOGGER.info("Working with date/period: {} <---------", dateEntry);
            newIncomeStatRaw.setDateOrPeriod(dateEntry);
            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(incomeListAttributesWithData, currentPeriodAttributes, i);
            newIncomeStatRaw.setAttributes(currentPeriodAttributes);
            incomeStatRawRepository.save(newIncomeStatRaw);
            // new
            currentCsvIncomeRawList.add(newIncomeStatRaw);
            // end new
            company.getIncomeRawStatements().add(newIncomeStatRaw);
            newSourceFile.getIncomeRawStatements().add(newIncomeStatRaw);

            i++;
        }
    }

    public IncomeStatRaw customIncomeRawGet(int i) {
        IncomeStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentCsvIncomeRawList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    public CashflowStatRaw customCashflowRawGet(int i) {
        CashflowStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentCsvCashflowRawList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    public BalanceStatRaw customBalanceRawGet(int i) {
        BalanceStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentCsvBalanceRawList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    List<Integer> listInts;
    public int findMaxAmountOfSpecificStatementsInCsvFile() {
        listInts = new LinkedList<>();
        listInts.add(currentCsvIncomeRawList.size()); // 4
        listInts.add(currentCsvCashflowRawList.size()); // 2
        listInts.add(currentCsvBalanceRawList.size()); // 2
        int maxLength = Collections.max(listInts);
        LOGGER.info("MaxLength: {}", maxLength);
        return maxLength;
    }

    @Override
    public String toString() {
        return "StatementsToDbHelperImpl{" +
                "cashflowStatRawRepository=" + cashflowStatRawRepository +
                ", balanceStatRawRepository=" + balanceStatRawRepository +
                ", incomeStatRawRepository=" + incomeStatRawRepository +
                ", currentCsvIncomeRawList=" + currentCsvIncomeRawList +
                ", currentCsvCashflowRawList=" + currentCsvCashflowRawList +
                ", currentCsvBalanceRawList=" + currentCsvBalanceRawList +
                '}';
    }
}

package com.taltech.stockscreenerapplication.service.rawStats;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.StatRaw;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.sourceFile.SourceCsvFile;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStatementsRepository;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import com.taltech.stockscreenerapplication.service.csvreader.Statement;
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
public class RawStatsCreation {

    private static final Logger LOGGER = LoggerFactory.getLogger(RawStatsCreation.class);

    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;

    @Autowired
    private IncomeStatRawRepository incomeStatRawRepository;

    @Autowired
    private GroupOfStatementsRepository groupOfStatementsRepository;

    public List<IncomeStatRaw> currentRawIncomeList;
    public List<CashflowStatRaw> currentRawCashflowList;
    public List<BalanceStatRaw> currentRawBalanceList;

    public RawStatsCreation() {
        currentRawIncomeList = new LinkedList<>();
        currentRawCashflowList = new LinkedList<>();
        currentRawBalanceList = new LinkedList<>();
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

    public void createNewFinStatementForSpecPeriod(List<String> finStatementListDateEntries,
                                                   List<List<String>> finStatementListAttributesWithData,
                                                   CompanyDimension company,
                                                   SourceCsvFile newSourceFile,
                                                   Statement statType) {

        // Making sure object used as singleton is clean and nothing from previous csv file is present this time.
        // It should be done only once per file. And Income list is first we encounter in controller.
        if (statType == Statement.Statement_income) {
            currentRawIncomeList = new LinkedList<>();
            currentRawCashflowList = new LinkedList<>();
            currentRawBalanceList = new LinkedList<>();
        }

        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : finStatementListDateEntries) {
            // Creating raw statement object for specific period (Q2 2017)
            StatRaw newFinStatementRaw = null;
            switch (statType) {
                case Statement_balance:
                    newFinStatementRaw = new BalanceStatRaw();
                    break;
                case Statement_income:
                    newFinStatementRaw = new IncomeStatRaw();
                    break;
                case Statement_cashflow:
                    newFinStatementRaw = new CashflowStatRaw();
                    break;
            }

            // Setting current period for raw income statement
            if (newFinStatementRaw == null) { return;}
            newFinStatementRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(finStatementListAttributesWithData, currentPeriodAttributes, i);
            newFinStatementRaw.setAttributes(currentPeriodAttributes);

            switch (statType) {
                case Statement_balance:
                    balanceStatRawRepository.save((BalanceStatRaw) newFinStatementRaw);
                    currentRawBalanceList.add((BalanceStatRaw) newFinStatementRaw);
                    company.getBalanceRawStatements().add((BalanceStatRaw) newFinStatementRaw);
                    //newSourceFile.getBalanceRawStatements().add((BalanceStatRaw) newFinStatementRaw);
                    break;
                case Statement_cashflow:
                    cashflowStatRawRepository.save((CashflowStatRaw) newFinStatementRaw);
                    currentRawCashflowList.add((CashflowStatRaw) newFinStatementRaw);
                    company.getCashflowRawStatements().add((CashflowStatRaw) newFinStatementRaw);
                    //newSourceFile.getCashflowRawStatements().add((CashflowStatRaw) newFinStatementRaw);
                    break;
                case Statement_income:
                    incomeStatRawRepository.save((IncomeStatRaw) newFinStatementRaw);
                    currentRawIncomeList.add((IncomeStatRaw) newFinStatementRaw);
                    company.getIncomeRawStatements().add((IncomeStatRaw) newFinStatementRaw);
                    //newSourceFile.getIncomeRawStatements().add((IncomeStatRaw) newFinStatementRaw);
                    break;
            }
            i++;
        }
    }

    public IncomeStatRaw customIncomeRawGet(int i) {
        IncomeStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentRawIncomeList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    public CashflowStatRaw customCashflowRawGet(int i) {
        CashflowStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentRawCashflowList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    public BalanceStatRaw customBalanceRawGet(int i) {
        BalanceStatRaw currentCsvStatement = null;
        try {
            currentCsvStatement = currentRawBalanceList.get(i);
        }
        catch (Exception ignored) { }

        return currentCsvStatement;
    }

    List<Integer> listInts;
    public int findMaxLengthOfStatementsInFile() {
        listInts = new LinkedList<>();
        listInts.add(currentRawIncomeList.size()); // 4
        listInts.add(currentRawCashflowList.size()); // 2
        listInts.add(currentRawBalanceList.size()); // 2
        int maxLength = Collections.max(listInts);
        LOGGER.info("MaxLength: {}", maxLength);
        return maxLength;
    }

    public void createGroupsOfStatementsForCompany(int maxLength, CompanyDimension company) {
        // Creating GroupsOfStatements
        for (int i = 0; i < maxLength; i++) {
            GroupOfStatements groupOfStatements = new GroupOfStatements();
            groupOfStatements.setIncomeStatRaw(customIncomeRawGet(i));
            groupOfStatements.setCashflowStatRaw(customCashflowRawGet(i));
            groupOfStatements.setBalanceStatRaw(customBalanceRawGet(i));

            //company.getGroupOfStatements().add(groupOfStatements);
            groupOfStatements.setCompanyDimension(company);
            groupOfStatementsRepository.save(groupOfStatements);
        }

        // End creating GroupsOfStatements
    }

    @Override
    public String toString() {
        return "StatementsToDbHelperImpl{" +
                "cashflowStatRawRepository=" + cashflowStatRawRepository +
                ", balanceStatRawRepository=" + balanceStatRawRepository +
                ", incomeStatRawRepository=" + incomeStatRawRepository +
                ", currentCsvIncomeRawList=" + currentRawIncomeList +
                ", currentCsvCashflowRawList=" + currentRawCashflowList +
                ", currentCsvBalanceRawList=" + currentRawBalanceList +
                '}';
    }
}

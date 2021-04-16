package com.taltech.stockscreenerapplication.service.csvreader;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class CsvReaderImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);
    private static CsvReaderImpl csvReaderImpl;
    @BeforeAll
    static void setup() {
        LOGGER.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        LOGGER.info("@BeforeEach - executes before each test method in this class");
        csvReaderImpl = new CsvReaderImpl();
    }

    @AfterEach
    void tearDown() {
        LOGGER.info("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        LOGGER.info("@AfterAll - executed after all test methods.");
    }

    //@Disabled("Not implemented yet")
    @Test
    void createCsvParser() {
        Assert.assertNotNull(csvReaderImpl.parser);
        Assert.assertEquals(';', csvReaderImpl.parser.getSeparator());
    }

    @Test
    void createCsvReader() {
        Assert.assertNull(csvReaderImpl.csvReader);

        String fileName = "tkm-2017_q2_CSV_modified_by_frontend";
        String stringPath = String.format("src/main/resources/csv/%s.csv", fileName);
        Path myPath = Paths.get(stringPath);
        Reader reader = null;

        try {
            reader = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOGGER.info("{}", e.getMessage());
        }

        // custom reader configuration
        csvReaderImpl.createCsvReader(reader);

        Assert.assertNotNull(csvReaderImpl.csvReader);

        //Assert.assertEquals(..., csvReaderImpl.csvReader.);
    }

    @Test
    void isCurrentRowStatementBlock() {
        List<String> lineBalance = new LinkedList<>(
                Arrays.asList("<balance_sheet>", "", ""));
        List<String> lineCashFlow = new LinkedList<>(
                Arrays.asList("<cash_flow_statement>", "", ""));
        List<String> lineIncome = new LinkedList<>(
                Arrays.asList("<income_statement>", "", ""));
        List<String> line4 = new LinkedList<>(
                Arrays.asList("</balance_sheet>", "", ""));
        List<String> line5 = new LinkedList<>(
                Arrays.asList("</cash_flow_statement>", "", ""));
        List<String> line6 = new LinkedList<>(
                Arrays.asList("</income_statement>", "", ""));

        List<String> line7 = new LinkedList<>(
                Arrays.asList("Date_information", "Note", "Q3 2017", "Q3 2016"));
        List<String> line8 = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "2", "5,774", "32,372"));
        List<String> line9 = new LinkedList<>(
                Arrays.asList("Revenue", "16", "164,645", "150,534"));
        List<String> line10 = new LinkedList<>(
                Arrays.asList("Net profit", "", "8379", "8,39"));

        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(null));

        Assert.assertTrue(csvReaderImpl.isCurrentRowStatementBlock(lineBalance));
        Assert.assertTrue(csvReaderImpl.isCurrentRowStatementBlock(lineCashFlow));
        Assert.assertTrue(csvReaderImpl.isCurrentRowStatementBlock(lineIncome));

        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line4));
        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line5));
        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line6));

        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line7));
        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line8));
        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line9));
        Assert.assertFalse(csvReaderImpl.isCurrentRowStatementBlock(line10));
    }

    @Test
    void determineCurrentSpecificStatementBlock() {
        List<String> lineBalance = new LinkedList<>(
                Arrays.asList("<balance_sheet>", "", ""));
        List<String> lineCashFlow = new LinkedList<>(
                Arrays.asList("<cash_flow_statement>", "", ""));
        List<String> lineIncome = new LinkedList<>(
                Arrays.asList("<income_statement>", "", ""));

        // Check if error doesn't happen
        csvReaderImpl.determineCurrentSpecificStatementBlock(null);

        Assert.assertEquals(null, csvReaderImpl.currentStatement);

        csvReaderImpl.determineCurrentSpecificStatementBlock(lineIncome);
        Assert.assertEquals(Statement.Statement_income , csvReaderImpl.currentStatement);
        csvReaderImpl.determineCurrentSpecificStatementBlock(lineBalance);
        Assert.assertEquals(Statement.Statement_balance , csvReaderImpl.currentStatement);
        csvReaderImpl.determineCurrentSpecificStatementBlock(lineCashFlow);
        Assert.assertEquals(Statement.Statement_cashflow , csvReaderImpl.currentStatement);


    }

    @Test
    void isEndingStatementBlock() {
        List<String> lineBalance = new LinkedList<>(
                Arrays.asList("<balance_sheet>", "", ""));
        List<String> lineCashFlow = new LinkedList<>(
                Arrays.asList("<cash_flow_statement>", "", ""));
        List<String> lineIncome = new LinkedList<>(
                Arrays.asList("<income_statement>", "", ""));
        List<String> lineEndingBalance = new LinkedList<>(
                Arrays.asList("</balance_sheet>", "", ""));
        List<String> lineEndingCashFlow = new LinkedList<>(
                Arrays.asList("</cash_flow_statement>", "", ""));
        List<String> lineEndingIncome = new LinkedList<>(
                Arrays.asList("</income_statement>", "", ""));

        List<String> line7 = new LinkedList<>(
                Arrays.asList("Date_information", "Note", "Q3 2017", "Q3 2016"));
        List<String> line8 = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "2", "5,774", "32,372"));
        List<String> line9 = new LinkedList<>(
                Arrays.asList("Revenue", "16", "164,645", "150,534"));
        List<String> line10 = new LinkedList<>(
                Arrays.asList("Net profit", "", "8379", "8,39"));

        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(null));

        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(lineBalance));
        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(lineCashFlow));
        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(lineIncome));

        Assert.assertTrue(csvReaderImpl.isEndingStatementBlock(lineEndingBalance));
        Assert.assertTrue(csvReaderImpl.isEndingStatementBlock(lineEndingCashFlow));
        Assert.assertTrue(csvReaderImpl.isEndingStatementBlock(lineEndingIncome));

        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(line7));
        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(line8));
        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(line9));
        Assert.assertFalse(csvReaderImpl.isEndingStatementBlock(line10));
    }

    @Test
    void doesHeaderColContainNote() {
        List<String> withNote = new LinkedList<>(
                Arrays.asList("Date_information", "Note", ""));
        List<String> withOutNote = new LinkedList<>(
                Arrays.asList("Date_information", "smthelse", ""));

        Assert.assertTrue(csvReaderImpl.doesHeaderColContainNote(withNote));
        Assert.assertFalse(csvReaderImpl.doesHeaderColContainNote(withOutNote));
        Assert.assertFalse(csvReaderImpl.doesHeaderColContainNote(null));

    }

    @Test
    void addFieldToCurrentStatement() {
        List<String> periodOrDateLine = new LinkedList<>(
                Arrays.asList("Date_information", "Note", "Q3 2017", "Q3 2016"));
        List<String> balanceLine = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "2", "5,774", "32,372"));
        List<String> incomeLine = new LinkedList<>(
                Arrays.asList("Revenue", "16", "164,645", "150,534"));
        List<String> cashflowLine = new LinkedList<>(
                Arrays.asList("Net profit", "", "8379", "8,39"));

        // initializing lists to escape nullpointerexception
        csvReaderImpl.balanceList = new ArrayList<>();
        csvReaderImpl.cashflowList = new ArrayList<>();
        csvReaderImpl.incomeList = new ArrayList<>();

        Assert.assertFalse(csvReaderImpl.incomeList.contains(incomeLine));
        csvReaderImpl.currentStatement = Statement.Statement_income;
        csvReaderImpl.incomeList.add(incomeLine);
        Assert.assertTrue(csvReaderImpl.incomeList.contains(incomeLine));

        Assert.assertFalse(csvReaderImpl.cashflowList.contains(cashflowLine));
        csvReaderImpl.currentStatement = Statement.Statement_cashflow;
        csvReaderImpl.cashflowList.add(cashflowLine);
        Assert.assertTrue(csvReaderImpl.cashflowList.contains(cashflowLine));

        Assert.assertFalse(csvReaderImpl.balanceList.contains(balanceLine));
        csvReaderImpl.currentStatement = Statement.Statement_balance;
        csvReaderImpl.balanceList.add(balanceLine);
        Assert.assertTrue(csvReaderImpl.balanceList.contains(balanceLine));
    }

    @Test
    void addNoteToFieldIfNecessary() {
        List<String> balanceLine = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "2", "5,774", "32,372"));
        List<String> balanceLine2 = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "3", "6,77", "52,3"));
        csvReaderImpl.foundNoteColumn = true;

        // If note column is found
        csvReaderImpl.addNoteToFieldIfNecessary(balanceLine);

        Assert.assertEquals("Cash and cash equivalents (note: 2)", balanceLine.get(0));

        // If note column isn't found
        csvReaderImpl.foundNoteColumn = false;

        csvReaderImpl.addNoteToFieldIfNecessary(balanceLine2);
        Assert.assertEquals("Cash and cash equivalents", balanceLine2.get(0));
    }

    @Test
    void removeUnnecessaryCols() {
        List<List<String>> testFinStatementList = new ArrayList<>();
    }

    @Test
    void determineUnnecessaryCols() {

        /*
        determineAndRemoveUnnecessaryCols(cashflowList);
        determineAndRemoveUnnecessaryCols(balanceList);
        determineAndRemoveUnnecessaryCols(incomeList);
        */

    }

    @Test
    void determineAndRemoveUnnecessaryCols() {
    }

    @Test
    void readCsvAndReturnLists() {
    }

    @Test
    void createReaderAndUseReadingMethod() {
    }
}
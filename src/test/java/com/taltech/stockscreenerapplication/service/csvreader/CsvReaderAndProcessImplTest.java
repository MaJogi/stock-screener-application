package com.taltech.stockscreenerapplication.service.csvreader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
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

class CsvReaderAndProcessImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderAndProcessImpl.class);
    private static CsvReaderAndProcessImpl csvReaderAndProcessImpl;
    @BeforeAll
    static void setup() {
        LOGGER.info("@BeforeAll - executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        LOGGER.info("@BeforeEach - executes before each test method in this class");
        csvReaderAndProcessImpl = new CsvReaderAndProcessImpl();
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
        Assert.assertNotNull(csvReaderAndProcessImpl.parser);
        Assert.assertEquals(';', csvReaderAndProcessImpl.parser.getSeparator());
    }

    @Test
    void createCsvReader() {
        Assert.assertNull(csvReaderAndProcessImpl.csvReader);

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
        csvReaderAndProcessImpl.createCsvReader(reader);

        Assert.assertNotNull(csvReaderAndProcessImpl.csvReader);

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

        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(null));

        Assert.assertTrue(csvReaderAndProcessImpl.isCurrentRowStatementBlock(lineBalance));
        Assert.assertTrue(csvReaderAndProcessImpl.isCurrentRowStatementBlock(lineCashFlow));
        Assert.assertTrue(csvReaderAndProcessImpl.isCurrentRowStatementBlock(lineIncome));

        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line4));
        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line5));
        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line6));

        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line7));
        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line8));
        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line9));
        Assert.assertFalse(csvReaderAndProcessImpl.isCurrentRowStatementBlock(line10));
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
        csvReaderAndProcessImpl.determineCurrentSpecificStatementBlock(null);

        Assert.assertEquals(null, csvReaderAndProcessImpl.currentStatement);

        csvReaderAndProcessImpl.determineCurrentSpecificStatementBlock(lineIncome);
        Assert.assertEquals(Statement.Statement_income , csvReaderAndProcessImpl.currentStatement);
        csvReaderAndProcessImpl.determineCurrentSpecificStatementBlock(lineBalance);
        Assert.assertEquals(Statement.Statement_balance , csvReaderAndProcessImpl.currentStatement);
        csvReaderAndProcessImpl.determineCurrentSpecificStatementBlock(lineCashFlow);
        Assert.assertEquals(Statement.Statement_cashflow , csvReaderAndProcessImpl.currentStatement);


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

        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(null));

        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(lineBalance));
        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(lineCashFlow));
        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(lineIncome));

        Assert.assertTrue(csvReaderAndProcessImpl.isEndingStatementBlock(lineEndingBalance));
        Assert.assertTrue(csvReaderAndProcessImpl.isEndingStatementBlock(lineEndingCashFlow));
        Assert.assertTrue(csvReaderAndProcessImpl.isEndingStatementBlock(lineEndingIncome));

        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(line7));
        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(line8));
        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(line9));
        Assert.assertFalse(csvReaderAndProcessImpl.isEndingStatementBlock(line10));
    }

    @Test
    void doesHeaderColContainNote() {
        List<String> withNote = new LinkedList<>(
                Arrays.asList("Date_information", "Note", ""));
        List<String> withOutNote = new LinkedList<>(
                Arrays.asList("Date_information", "smthelse", ""));

        Assert.assertTrue(csvReaderAndProcessImpl.doesHeaderColContainNote(withNote));
        Assert.assertFalse(csvReaderAndProcessImpl.doesHeaderColContainNote(withOutNote));
        Assert.assertFalse(csvReaderAndProcessImpl.doesHeaderColContainNote(null));

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
        csvReaderAndProcessImpl.balanceList = new ArrayList<>();
        csvReaderAndProcessImpl.cashflowList = new ArrayList<>();
        csvReaderAndProcessImpl.incomeList = new ArrayList<>();

        Assert.assertFalse(csvReaderAndProcessImpl.incomeList.contains(incomeLine));
        csvReaderAndProcessImpl.currentStatement = Statement.Statement_income;
        csvReaderAndProcessImpl.incomeList.add(incomeLine);
        Assert.assertTrue(csvReaderAndProcessImpl.incomeList.contains(incomeLine));

        Assert.assertFalse(csvReaderAndProcessImpl.cashflowList.contains(cashflowLine));
        csvReaderAndProcessImpl.currentStatement = Statement.Statement_cashflow;
        csvReaderAndProcessImpl.cashflowList.add(cashflowLine);
        Assert.assertTrue(csvReaderAndProcessImpl.cashflowList.contains(cashflowLine));

        Assert.assertFalse(csvReaderAndProcessImpl.balanceList.contains(balanceLine));
        csvReaderAndProcessImpl.currentStatement = Statement.Statement_balance;
        csvReaderAndProcessImpl.balanceList.add(balanceLine);
        Assert.assertTrue(csvReaderAndProcessImpl.balanceList.contains(balanceLine));
    }

    @Test
    void addNoteToFieldIfNecessary() {
        List<String> balanceLine = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "2", "5,774", "32,372"));
        List<String> balanceLine2 = new LinkedList<>(
                Arrays.asList("Cash and cash equivalents", "3", "6,77", "52,3"));
        csvReaderAndProcessImpl.foundNoteColumn = true;

        // If note column is found
        csvReaderAndProcessImpl.addNoteToFieldIfNecessary(balanceLine);

        Assert.assertEquals("Cash and cash equivalents (note: 2)", balanceLine.get(0));

        // If note column isn't found
        csvReaderAndProcessImpl.foundNoteColumn = false;

        csvReaderAndProcessImpl.addNoteToFieldIfNecessary(balanceLine2);
        Assert.assertEquals("Cash and cash equivalents", balanceLine2.get(0));
    }

    @Test
    void removeUnnecessaryCols() {
        List<Integer> emptyLineOrNoteLineStatement = new LinkedList<>(Arrays.asList(1, 4, 5));

        // LinkedList is dynamic and arraylist is static and can't be manipulated (add, remove etc)
        List<List<String>> testFinStatementList = new LinkedList<>();
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Date_information", "Note", "6 months 2017", "6 months 2016", "", ""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Net profit","","8,379","8,39","",""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Income tax on dividends","15","6,371","5,219","",""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Interest expense","","383","431","",""))));

        List<List<String>> expectedFinStatList = new LinkedList<>(Arrays.asList(
                Arrays.asList("Date_information", "6 months 2017", "6 months 2016"),
                Arrays.asList("Net profit","8,379","8,39"),
                Arrays.asList("Income tax on dividends","6,371","5,219"),
                Arrays.asList("Interest expense","383","431")
        ));

        csvReaderAndProcessImpl.removeUnnecessaryCols(testFinStatementList, emptyLineOrNoteLineStatement);
        Assert.assertTrue(testFinStatementList.equals(expectedFinStatList));

    }

    @Test
    void determineUnnecessaryCols() {

        List<List<String>> testFinStatementList = new LinkedList<>();
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Date_information", "Note", "6 months 2017", "6 months 2016", "", ""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Net profit","","8,379","8,39","",""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Income tax on dividends","15","6,371","5,219","",""))));
        testFinStatementList.add(new LinkedList<>((Arrays.asList("Interest expense","","383","431","",""))));

        List<Integer> expectedResultList = new LinkedList<>(Arrays.asList(1, 4, 5));
        List<Integer> resultList = new LinkedList<>();
        /*
        determineAndRemoveUnnecessaryCols(cashflowList);
        determineAndRemoveUnnecessaryCols(balanceList);
        determineAndRemoveUnnecessaryCols(incomeList);
        */
        csvReaderAndProcessImpl.determineUnnecessaryCols(testFinStatementList, resultList);
        Assert.assertTrue(expectedResultList.equals(resultList));

    }

    @Test
    void determineAndRemoveUnnecessaryCols() {
        // Already tested individually
    }

    @Test
    void readCsvAndReturnLists() {
        // Global with specific file which values i know

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();

        String stringPath = String.format("src/main/resources/csv/%s.csv", "tkm-2017_q2_CSV_modified_by_frontend");
        Path myPath = Paths.get(stringPath);

        List<List<List<String>>> result = null;

        try {
            Reader reader = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_QUOTES)
                    .build();

            result = csvReaderAndProcessImpl.readCsvAndReturnLists(csvReader);
        }
        catch (Exception e) {
            LOGGER.info("{}", e.getMessage());
        }


        Assert.assertEquals(19, result.get(0).size());
        Assert.assertEquals(32, result.get(1).size());
        Assert.assertEquals(26, result.get(2).size());

        // we can test more things later

    }

    @Test
    void createReaderAndUseReadingMethod() {
        List<List<List<String>>> result = null;

        String fileName = "tkm-2017_q2_CSV_modified_by_frontend";
        try {
            result = csvReaderAndProcessImpl.createReaderAndUseReadingMethod(fileName);
        }
        catch (Exception e) {

        }

        Assert.assertEquals(19, result.get(0).size());
        Assert.assertEquals(32, result.get(1).size());
        Assert.assertEquals(26, result.get(2).size());

    }

    @Test
    void initListsAndDefaultReadProperties() {
        Assert.assertNull(csvReaderAndProcessImpl.balanceList);
        Assert.assertNull(csvReaderAndProcessImpl.cashflowList);
        Assert.assertNull(csvReaderAndProcessImpl.incomeList);
        csvReaderAndProcessImpl.initListsAndDefaultReadProperties();
        Assert.assertNotNull(csvReaderAndProcessImpl.balanceList);
        Assert.assertNotNull(csvReaderAndProcessImpl.cashflowList);
        Assert.assertNotNull(csvReaderAndProcessImpl.incomeList);
        Assert.assertEquals(Statement.Statement_notinitialized, csvReaderAndProcessImpl.currentStatement);
        Assert.assertFalse(csvReaderAndProcessImpl.foundNoteColumn);
    }
}
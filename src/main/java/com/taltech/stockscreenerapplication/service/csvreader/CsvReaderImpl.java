package com.taltech.stockscreenerapplication.service.csvreader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class CsvReaderImpl implements IReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    CSVParser parser;
    CSVReader csvReader;
    List<List<String>> balanceList;
    List<List<String>> cashflowList;
    List<List<String>> incomeList;

    public CsvReaderImpl() {
        LOGGER.info("Default CsvReaderImpl constructor called");
        createCsvParser();
    }

    public void createCsvParser() {
        this.parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
    }
    public void createCsvReader(Reader reader) {
        this.csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_QUOTES)
                .build();
    }

    public boolean isCurrentRowStatementBlock(List<String> line) {
        if (line == null) {
            return false;
        }

        return line.get(0).toLowerCase().contains("<income_statement>")
                || line.get(0).toLowerCase().contains("<cash_flow_statement>")
                || line.get(0).toLowerCase().contains("<balance_sheet>");
    }

    public void determineCurrentSpecificStatementBlock(List<String> line) {
        if (line == null) {
            return;
        }
        if (line.get(0).toLowerCase().contains("<income_statement>")) {
            this.currentStatement = Statement.Statement_income;
        }
        else if (line.get(0).toLowerCase().contains("<cash_flow_statement>")) {
            this.currentStatement = Statement.Statement_cashflow;
        }
        else if (line.get(0).toLowerCase().contains("<balance_sheet>")) {
            this.currentStatement = Statement.Statement_balance;
        }
    }

    public boolean isEndingStatementBlock(List<String> line) {
        if (line == null) {
            return false;
        }
        return line.get(0).contains("</income_statement>")
                || line.get(0).contains("</cash_flow_statement>")
                || line.get(0).contains("</balance_sheet>");
    }

    Statement currentStatement;
    boolean foundNoteColumn;

    public boolean doesHeaderColContainNote(List<String> line) {
        if (line == null) {
            return false;
        }
        String stringToSearch = "note";
        if (line.get(1).toLowerCase().contains(stringToSearch)) {
            return true;
            // Make note column empty to ignore it later
            //line[1] = "";
        }
        return false;
    }

    public void addFieldToCurrentStatement(List<String> line) {
        if (currentStatement == Statement.Statement_income) {
            incomeList.add(line);
        }
        else if (currentStatement == Statement.Statement_cashflow) {
            cashflowList.add(line);
        }
        else if (currentStatement == Statement.Statement_balance) {
            balanceList.add(line);
        }
    }

    public void addNoteToFieldIfNecessary(List<String> line) {
        if (foundNoteColumn && !line.get(1).equals("")) {
            line.set(0, line.get(0) + " " + "(note: " + line.get(1) + ")");
            //line[1] = "";
        }
    }

    // Removing unnecessary columns for clean file, than can be persisted in database.
    public void removeUnnecessaryCols(List<List<String>> finStatementList, List<Integer> emptyLineOrNoteLineStatement) {
        LOGGER.info("Starting removing empty && note columns");
        for (List<String> row : finStatementList) {
            int tempCounter = 0;
            for (int index : emptyLineOrNoteLineStatement) {
                index = index - tempCounter;
                row.remove(index);
                tempCounter++;
            }
        }
    }

    public void determineUnnecessaryCols(List<List<String>> specificStatementList, List<Integer> emptyLineOrNoteIndexList) {
        int colCounter = 0;
        // Looking which header to remove
        for (String columnName : specificStatementList.get(0)) {
            if (columnName.toLowerCase().trim().equals("note")  ||
                    columnName.trim().equals("")) {
                emptyLineOrNoteIndexList.add(colCounter);
            }
            colCounter++;
        }
        LOGGER.info("Column indexes we want to get rid of: {}", emptyLineOrNoteIndexList);
    }

    public void determineAndRemoveUnnecessaryCols(List<List<String>> list) {
        List<Integer> emptyOrNoteLineIndexList = new ArrayList<>();
        determineUnnecessaryCols(list, emptyOrNoteLineIndexList);
        removeUnnecessaryCols(list, emptyOrNoteLineIndexList);
    }

    public void initListsAndDefaultReadProperties() {
        balanceList = new ArrayList<>();
        cashflowList = new ArrayList<>();
        incomeList = new ArrayList<>();

        currentStatement = Statement.Statement_notinitialized;
        foundNoteColumn = false;
    }

    // Similarly, we can abstract readNext() which reads a supplied .csv line by line:
    public List<List<List<String>>> readCsvAndReturnLists(CSVReader csvReader) throws Exception {
        // Default initialization
        initListsAndDefaultReadProperties();

        String[] lineArray;
        List<String> line;

        LOGGER.info("Trying to start reading csv file <----------");
        while ((lineArray = csvReader.readNext()) != null) {
            line = new LinkedList<>(Arrays.asList(lineArray));

            // check if empty line. This will remove lines like: , , , ,
            if (line.get(0).isEmpty()) { continue; }

            if (!foundNoteColumn && csvReader.getLinesRead() < 2) {
                // Mõtle hiljem kuidas teha nii, et see kontroll tehakse ainult ühe korra
                foundNoteColumn = doesHeaderColContainNote(line);
            }

            // 1st step: We have to check, if we have something inside <>
            if (isCurrentRowStatementBlock(line)) {
                // Check which financial statement we have: <income_statement>, <balance_sheet>, <cash_flow_statement>
                determineCurrentSpecificStatementBlock(line);
                continue;
            }

            // Skip statement closing tag
            if (isEndingStatementBlock(line)) { continue; }

            /*
            // 2th step: Remove everything inside parantheses (CURRENTLY DEACTIVATED)
            String paranthesis = ")";
            String regexTarget = "\\(([^\\)]+)\\)"; // matches any character inside parantheses
            String replacement = ""; // empty
            if (line[0].contains(paranthesis)) {
                line[0] = line[0].replaceAll(regexTarget, replacement).trim();
            }
            */

            addNoteToFieldIfNecessary(line);

            // 3rd step:: If Statement is of type x, insert into dependent list y
            addFieldToCurrentStatement(line);
        }
        csvReader.close();

        // Determining and removing unnecessary columns
        determineAndRemoveUnnecessaryCols(cashflowList);
        determineAndRemoveUnnecessaryCols(balanceList);
        determineAndRemoveUnnecessaryCols(incomeList);

        // Creating a list to hold all statements
        List<List<List<String>>> combinedLists = new ArrayList<>();
        combinedLists.add(incomeList);
        combinedLists.add(cashflowList);
        combinedLists.add(balanceList);

        return combinedLists;
    }

    public List<List<List<String>>> createReaderAndUseReadingMethod(String fileName) throws Exception {
        LOGGER.info("Starting using reader");

        String stringPath = String.format("src/main/resources/csv/%s.csv", fileName);
        Path myPath = Paths.get(stringPath);
        LOGGER.info("Path at which csv is found {}", myPath);

        Reader reader = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);

        LOGGER.info("Initial reader done");

        // custom reader configuration
        createCsvReader(reader);

        return readCsvAndReturnLists(csvReader);
    }
}

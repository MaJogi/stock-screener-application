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

    /*
    public Table bushTable;
    public String[] animals;
    public double[] cuteness;
    public Table cuteAnimals;

    public CsvReader() {
        animals = new String[]{"bear", "cat", "giraffe"};
        cuteness = new double[]{90.1, 84.3, 99.7};
    }



    public void readExampleTable() {
        System.out.println("Starting reading exampleTable");

        cuteAnimals =
                Table.create("Cute Animals")
                        .addColumns(
                                StringColumn.create("Animal types", animals),
                                DoubleColumn.create("rating", cuteness));

        System.out.println("Ending reading exampleTable");
    }



    public void readCsvFile() throws IOException {
        System.out.println("starting reading csv");
        //bushTable = Table.read().csv("C:\\Users\\Marko\\Desktop\\Ülikool\\6 semester\\CSV\\tal-2019_q3_CSV.csv");

        // Good old way to read and format data (kirjuta, miks olemasolevad vahendid ei sobinud)
        String pathToCsv = "C:\\Users\\Marko\\Desktop\\Ülikool\\6 semester\\CSV\\tal-2019_q3_CSV.csv";
        File csvFile = new File(pathToCsv);

        BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            // Andmete puhastamine!!!
            // Nüüd on vaja midagi teha <income_statement>; ; ; ;
            System.out.println(data);

            // Date_information; Q3 2019;Q3 2018;Jan-Sep 2019;Jan-Sep 2018
            // Revenue (Note 3);287771;283609;722744;723173      (Tuleb eemaldada (Note ...), kasutades regexi?)
            //



            // do something with the data
        }
        csvReader.close();


        System.out.println("ending reading csv");
    }

     */

    /*
    public List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = new ArrayList<>();
        list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }
    */
    CSVParser parser;
    CSVReader csvReader;
    List<List<String>> bilanceList;
    List<List<String>> cashflowList;
    List<List<String>> incomeList;

    public CsvReaderImpl() {
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
        return line.get(0).contains("<income_statement>") || line.get(0).contains("<cash_flow_statement>")
                || line.get(0).contains("<balance_sheet>");
    }

    public void determineCurrentSpecificStatementBlock(List<String> line) {
        if (line.get(0).contains("<income_statement>")) {
            this.currentStatement = Statement.Statement_income;
        }
        else if (line.get(0).contains("<cash_flow_statement>")) {
            this.currentStatement = Statement.Statement_cashflow;
        }
        else if (line.get(0).contains("<balance_sheet>")) {
            this.currentStatement = Statement.Statement_balance;
        }
    }

    public boolean isEndingStatementBlock(List<String> line) {
        return line.get(0).contains("</income_statement>") || line.get(0).contains("</cash_flow_statement>")
                || line.get(0).contains("</balance_sheet>");
    }

    Statement currentStatement;
    boolean foundNoteColumn;

    public boolean doesHeaderColContainNote(List<String> line) {
        // This is needed to get rid of note column later
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
            bilanceList.add(line);
        }
    }

    public void addNoteToFieldIfNecessary(List<String> line) {
        if (foundNoteColumn && !line.get(1).equals("")) {
            line.set(0, line.get(0) + " " + "(note: " + line.get(1) + ")");
            //line[1] = "";
        }
    }

    public void removeUnnecessaryCols(List<List<String>> cashflowList, List<Integer> emptyLineOrNoteLineCashFlow) {
        for (List<String> row : cashflowList) {

            int tempHelperInt = 0;
            for (int index : emptyLineOrNoteLineCashFlow) {
                index = index - tempHelperInt;
                row.remove(index);
                tempHelperInt++;
            }
        }
    }

    public void determineUnnecessaryCols(List<List<String>> specificStatementList, List<Integer> emptyLineOrNoteList) {
        LOGGER.info("Starting removing empty && note columns");
        int colCounter = 0;
        // Looking which header to remove
        for (String columnName : specificStatementList.get(0)) {
            if (columnName.toLowerCase().trim().equals("note")  ||
                    columnName.trim().equals("")) {
                emptyLineOrNoteList.add(colCounter);
            }
            colCounter++;
        }
        LOGGER.info("Column indexes we want to get rid of: {}", emptyLineOrNoteList);
    }

    public void determineAndRemoveUnnecessaryCols(List<List<String>> list) {
        List<Integer> emptyLineOrNoteResultList = new ArrayList<>();
        determineUnnecessaryCols(list, emptyLineOrNoteResultList);
        removeUnnecessaryCols(list, emptyLineOrNoteResultList);
    }

    // Similarly, we can abstract readNext() which reads a supplied .csv line by line:
    public List<List<List<String>>> readCsvAndReturnLists(CSVReader csvReader) throws Exception {

        // Default initialization
        bilanceList = new ArrayList<>();
        cashflowList = new ArrayList<>();
        incomeList = new ArrayList<>();

        currentStatement = Statement.Statement_notinitialized;
        foundNoteColumn = false;

        String[] lineArray;
        List<String> line;

        while ((lineArray = csvReader.readNext()) != null) {
            line = new LinkedList<>(Arrays.asList(lineArray));

            // check if empty line. This will remove lines like: , , , ,
            if (line.get(0).isEmpty()) { continue; }

            if (!foundNoteColumn) {
                // Mõtle hiljem kuidas teha nii, et see kontroll tehakse ainult ühe korra
                foundNoteColumn = doesHeaderColContainNote(line);
            }

            // 1st step: We have to check, if we have something inside <>
            // Check which financial statement we have: <income_statement>, <balance_sheet>, <cash_flow_statement>

            if (isCurrentRowStatementBlock(line)) {
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
        determineAndRemoveUnnecessaryCols(bilanceList);
        determineAndRemoveUnnecessaryCols(incomeList);

        // Creating a list to hold all statements
        List<List<List<String>>> combinedLists = new ArrayList<>();
        combinedLists.add(incomeList);
        combinedLists.add(cashflowList);
        combinedLists.add(bilanceList);

        // Kindlasti front endi peal kuvamisel tuleks kontrollida, kas saadud list
        // (või mõni muu collection) on tühi!

        return combinedLists;

        // After that, we should add this information to database.
        // For example, if we upload first ever csv file about TKM firm, then
        // 1) Database creates source_csv_file table entry:
        // insert into source_csv_file (source_file_id: 1, source_file_name: "tkm-2017_q2_CSV")
        // 2) For example, we found only bilance and cashflow statement
        // insert into balance_stat_as_imported (...)
    }

    public void insertFieldToRightDatebase() {
    }

    public List<List<List<String>>> createReaderAndUseReadingMethod() throws Exception {
        LOGGER.info("Starting using reader");
        /*
        URL pathToCsv = ClassLoader.getSystemResource("csv/twoColumn.csv");
        LOGGER.info("{}", pathToCsv);
        System.out.println(ClassLoader.getSystemResource("csv/twoColumn.csv"));
         */

        //String fileName = "src/main/resources/csv/tal-2019_q3_CSV.csv";
        //String fileName = "src/main/resources/csv/tkm-2017_q2_CSV.csv";

        String fileName = "src/main/resources/csv/tkm-2017_q2_CSV_modified_by_frontend.csv";
        Path myPath = Paths.get(fileName);
        LOGGER.info("Path at which csv is found {}", myPath);

        /*
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("csv/twoColumn.csv").toURI()));
        */
        Reader reader = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);

        LOGGER.info("Initial reader done");

        // custom reader configuration
        createCsvReader(reader);

        return readCsvAndReturnLists(csvReader);
    }
}

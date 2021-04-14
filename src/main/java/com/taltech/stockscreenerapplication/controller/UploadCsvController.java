package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.Constants;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.repository.*;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
/*@RequestMapping("/upload")*/
public class UploadCsvController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;
    @Autowired
    private IncomeStatRawRepository incomeStatRawRepository;
    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private SourceCsvFileRepository sourceCsvFileRepository;

    /* Examples:

    @GetMapping
    public Iterable<CompanyDimension> getCompanies() {

        return companyDimensionRepository.findAll();
    }

    @GetMapping("/{tickerId}")
    public CompanyDimension getCompany(@PathVariable final String tickerId) {

        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + tickerId));
    }

     */

/*
    @GetMapping("/") // default mapping without additional arguments. Right now reading in csv
    public void getUploadPage() {
        // Is there even necessary to return smth on standard get?
        // Maybe we will return company information for which we need to upload data

    }

 */


//    // This will accept CSV file that was inserted with React frontend
//    @PostMapping
//    public Iterable<BalanceStatRaw> postDefaultPageItems() {
//        CsvReaderImpl readerImpl = new CsvReaderImpl();
//        try {
//            List<List<List<String>>> result = readerImpl.createReaderAndUseReadingMethod();
//        }
//        catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//
//        // now we want to save every statement and their period data to database.
//
//
//        return null;
//    }

    /*
    @PostMapping(value = "/{userId}/tickers", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> saveTicker(@PathVariable final Long userId, @RequestBody final AddTickerRequest addTickerRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE + userId));
        CompanyDimension companyDimension = companyDimensionRepository.findById(addTickerRequest.getTickerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + addTickerRequest.getTickerId()));

        Set<CompanyDimension> tickers = user.getTickers();
        tickers.add(companyDimension);
        userRepository.save(user);

        return ResponseEntity
                .status(201)
                .body(new MessageResponse("Ticker added successfully!"));
    }
     */


    // This will accept CSV file that was inserted with React frontend as Json string
    // It will be in unclean format!
    // tagastab clean faili jsonina, et olla kindel, et andmed loeti õigesti
    @PostMapping(value="csvUpload", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MessageResponse> saveFormDataToDb(@PathVariable final String ticker, @RequestBody final String csvFile) {
        CsvReaderImpl readerImpl = new CsvReaderImpl();
        List<List<List<String>>> result = null;
        try {
            // Nr n source file, which contains 1 up to 3 statements
            result = readerImpl.createReaderAndUseReadingMethod();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        // now we want to save every statement and their period data to database.
        /*
        1. Andmed on kindlal kujul csv formaadis.
        1. 1. Index 0 on alati: Income, Index 1: cashflow, Index 2: bilance
        2. Luuakse uus SourceCsvFile üksus
        3. Luuakse incomeStatRaw objekt
        4. Luuakse incomeStatRaw objekti jaoks Attribute objektid (nt 20 tk)
        5. Need incomeStatRaw atribuudid on võimalik kätte saada läbi getAttributesMap'i
        5.1 või alternatiivina luuakse repositooriumis eraldi getAttributes meetod, mille abil saadakse kätte
        5.1 atribuutide id'd, mis kuuluvad kindla firma esitatud aruandele ning millel on kindla perioodi väärtus
        6. (Tehakse kõik sama läbi teiste finantsaruannetega)
        7.

         */

        if (result == null) {
            LOGGER.error("Result entity contains null, which should actually be a list of three different lists");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong with reading in values from CSV file"));
        }


        // 2. Luuakse uus SourceCsvFile üksus

        SourceCsvFile newSourceFile = new SourceCsvFile();
        newSourceFile.setSourceFileName(Constants.FILENAME);
        //String insertNewFileQuery = String.format("insert into source_csv_file (source_file_name) values %s", Constants.FILENAME);
        sourceCsvFileRepository.save(newSourceFile);

        // 3. Luuakse balanceStatRaw objekt

        // get all incomeList rows of particular file
        // first row will always contain dates for second column.
        List<List<String>> incomeList = result.get(0);
        List<String> firstRow = incomeList.get(0);
        LOGGER.info("Size of the list is {} <-----------", firstRow.size());
        List<String> incomeListDateEntries = firstRow.subList(1, firstRow.size() - 1);
        List<List<String>> incomeListAttributesWithData = incomeList.subList(1, incomeList.size());

        int i = 1; // starting from first value column
        for (String dateEntry : incomeListDateEntries) {
            // Creating raw income statement object for specific period (Q2 2017)
            IncomeStatRaw newIncomeStatRaw = new IncomeStatRaw();
            // setting source file
            //newIncomeStatRaw.setSourceCsvFile(newSourceFile);

            // Setting current period for raw income statement
            newIncomeStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            for (List<String> dataLine : incomeListAttributesWithData) {
                //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
                //[Revenue (note: 16), 164,645, 150,534, 315,333, 287,384]

                Attribute attr = new Attribute();
                attr.setFieldName(dataLine.get(0));
                LOGGER.info("This might break something <- Parsing to Double instead of double");
                attr.setValue(Double.parseDouble(dataLine.get(i)));

                // saving newly created attribute to db
                currentPeriodAttributes.add(attr);
                //attributeRepository.save(attr);
            }
            newIncomeStatRaw.setAttributes(currentPeriodAttributes);
            incomeStatRawRepository.save(newIncomeStatRaw);
            i++;
        }


        List<List<String>> cashflowList = result.get(1);
        List<List<String>> bilanceList = result.get(2);


        return null;
    }


    // now we want to save every statement and their period data to database.
        /*
        1. Andmed on kindlal kujul csv formaadis.
        1. 1. Index 0 on alati: Income, Index 1: cashflow, Index 2: bilance
        2. Luuakse uus SourceCsvFile üksus
        3. Luuakse incomeStatRaw objekt
        4. Luuakse incomeStatRaw objekti jaoks Attribute objektid (nt 20 tk)
        6. (Tehakse kõik sama läbi teiste finantsaruannetega)
        7.

         */

    @GetMapping("/test")
    public ResponseEntity<MessageResponse> testing() {
        LOGGER.info("STARTING A test method from controller");
        CsvReaderImpl readerImpl = new CsvReaderImpl();
        List<List<List<String>>> result = null;
        try {
            // Nr n source file, which contains 1 up to 3 statements
            result = readerImpl.createReaderAndUseReadingMethod();
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        if (result == null) {
            LOGGER.error("Result entity contains null, which should actually be a list of three different lists");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong with reading in values from CSV file"));
        }

        // 2. Luuakse uus SourceCsvFile üksus

        SourceCsvFile newSourceFile = new SourceCsvFile();
        newSourceFile.setSourceFileName(Constants.FILENAME);

        // we need to find a firm, to upload a csv file to its name.
        Optional<CompanyDimension> company = companyDimensionRepository.findById(Constants.TESTFIRM);

        newSourceFile.setTicker_id(company.get());
        //String insertNewFileQuery = String.format("insert into source_csv_file (source_file_name) values %s", Constants.FILENAME);
        sourceCsvFileRepository.save(newSourceFile);

        // get lists for all statements rows of particular file
        // Example nr 1 (readme)
        List<List<String>> incomeList = result.get(0);
        List<List<String>> cashFlowList = result.get(1);
        List<List<String>> bilanceList = result.get(2);
        LOGGER.info("Bilance list has {} entities",  bilanceList.size());

        // first rows of all income statements (header)
        // Ex: [Date_information (note: Note), Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]

        List<String> firstIncomeStatRow = incomeList.get(0);
        List<String> firstCashflowRow = cashFlowList.get(0);
        List<String> firstBilanceRow = bilanceList.get(0);
        LOGGER.info("Size of the list is {} <-----------", firstIncomeStatRow.size());
        LOGGER.info("Size of the list is {} <-----------", firstCashflowRow.size());
        LOGGER.info("Size of the list is {} <-----------", firstBilanceRow.size());

        // Pure date lists of specific financial statements
        // Ex: [Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        List<String> incomeListDateEntries = firstIncomeStatRow.subList(1, firstIncomeStatRow.size());
        List<String> cashflowListDateEntries = firstCashflowRow.subList(1, firstCashflowRow.size());

        List<String> bilanceListDateEntries = firstBilanceRow.subList(1, firstBilanceRow.size());

        // Specific financial statement list of attributes with data
        // Ex: [Revenue (note: 16), 164,645, 150,534, 315,333, 287,384],
        //     [Other operating income, 259, 684, 741, 951] ...
        List<List<String>> incomeListAttributesWithData = incomeList.subList(1, incomeList.size());
        List<List<String>> cashflowListAttributesWithData = cashFlowList.subList(1, cashFlowList.size());
        List<List<String>> bilanceListAttributesWithData = bilanceList.subList(1, bilanceList.size());

        createNewIncomeFinStatementForSpecPeriod(incomeListDateEntries, incomeListAttributesWithData, company);
        createNewCashflowFinStatementForSpecPeriod(cashflowListDateEntries, cashflowListAttributesWithData, company);
        createNewBilanceFinStatementForSpecPeriod(bilanceListDateEntries, bilanceListAttributesWithData, company);

        companyDimensionRepository.save(company.get());

        return ResponseEntity
                .status(201)
                .body(new MessageResponse("Database seems to be populated successfully, check database"));
    }

    public double parseNumToDouble(List<String> dataLine, int j) {
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

    public void iterateDataLinesAndCreateFinStatementAttrs(List<List<String>> incomeListAttributesWithData, List<Attribute> currentPeriodAttributes, int i){
        //[Revenue (note: 16), 164,645, 150,534, 315,333, 287,384]
        for (List<String> dataLine : incomeListAttributesWithData) {
            Attribute attr = new Attribute();
            attr.setFieldName(dataLine.get(0));

            // Parsing "," to "."
            double valueDouble = parseNumToDouble(dataLine, i);
            attr.setValue(valueDouble);

            // saving new attribute to db
            currentPeriodAttributes.add(attr);
        }
    }

    public void createNewFinStatementForSpecPeriod(List<String> finStatementListDateEntries,
                                                           List<List<String>> finStatementListAttributesWithData,
                                                           Optional<CompanyDimension> company) {
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

    public void createNewCashflowFinStatementForSpecPeriod(List<String> cashflowListDateEntries,
                                                           List<List<String>> cashflowListAttributesWithData,
                                                           Optional<CompanyDimension> company) {
        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : cashflowListDateEntries) {
            // Creating raw income statement object for specific period (Q2 2017)
            CashflowStatRaw newCashflowStatRaw = new CashflowStatRaw();

            // Setting current period for raw income statement
            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newCashflowStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(cashflowListAttributesWithData, currentPeriodAttributes, i);
            newCashflowStatRaw.setAttributes(currentPeriodAttributes);

            cashflowStatRawRepository.save(newCashflowStatRaw);
            company.get().getCashflowRawStatements().add(newCashflowStatRaw);
            i++;
        }
    }

    public void createNewBilanceFinStatementForSpecPeriod(List<String> bilanceListDateEntries,
                                                           List<List<String>> bilanceListAttributesWithData,
                                                           Optional<CompanyDimension> company) {
        // starting from first value column
        int i = 1;
        //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        for (String dateEntry : bilanceListDateEntries) {
            // Creating raw income statement object for specific period (Q2 2017)
            BalanceStatRaw newBalanceStatRaw = new BalanceStatRaw();

            // Setting current period for raw income statement
            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newBalanceStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            iterateDataLinesAndCreateFinStatementAttrs(bilanceListAttributesWithData, currentPeriodAttributes, i);
            newBalanceStatRaw.setAttributes(currentPeriodAttributes);

            balanceStatRawRepository.save(newBalanceStatRaw);
            company.get().getBilanceRawStatements().add(newBalanceStatRaw);
            i++;
        }
    }

    public void createNewIncomeFinStatementForSpecPeriod(List<String> incomeListDateEntries,
                                                           List<List<String>> incomeListAttributesWithData,
                                                           Optional<CompanyDimension> company) {
        int i = 1;
        for (String dateEntry : incomeListDateEntries) {
            IncomeStatRaw newIncomeStatRaw = new IncomeStatRaw();

            LOGGER.info("Working with DATE_OR_PERIOD: {} <---------", dateEntry);
            newIncomeStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();

            iterateDataLinesAndCreateFinStatementAttrs(incomeListAttributesWithData, currentPeriodAttributes, i);

            newIncomeStatRaw.setAttributes(currentPeriodAttributes);
            incomeStatRawRepository.save(newIncomeStatRaw);
            company.get().getIncomeRawStatements().add(newIncomeStatRaw);
            i++;
        }
    }

    @GetMapping("/{tickerId}/incomeStatements") // localhost:0000/TKM1T
    public List<IncomeStatRaw> getCompanyRawIncomeStats(@PathVariable final String tickerId) {
        Optional<CompanyDimension> company = companyDimensionRepository.findById(tickerId);

        List<IncomeStatRaw> listOfRawIncomeStatements = company.get().getIncomeRawStatements();

        return listOfRawIncomeStatements;
    }

    @GetMapping("/{tickerId}/income/{dateOrPeriod}") // localhost:0000/TKM1T
    public IncomeStatRaw getCompanySpecificTimeRawIncomeStats(@PathVariable final String tickerId, @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawIncomeStats with parameters -> tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        Long incomeStatementIdWithSpecificDate = companyDimensionRepository.findByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);
        Optional<IncomeStatRaw> incomeStatement = incomeStatRawRepository.findById(incomeStatementIdWithSpecificDate);

        return incomeStatement.get();
    }

    @GetMapping("/{tickerId}/cashflowStatements") // localhost:0000/TKM1T
    public List<CashflowStatRaw> getCompanyRawCashflowStats(@PathVariable final String tickerId) {
        Optional<CompanyDimension> company = companyDimensionRepository.findById(tickerId);

        List<CashflowStatRaw> listOfRawCashflowStatements = company.get().getCashflowRawStatements();
        for (CashflowStatRaw statement : listOfRawCashflowStatements) {
            LOGGER.info("{}", statement.getCashflow_raw_id());
        }

        return listOfRawCashflowStatements;
    }

    @GetMapping("/{tickerId}/cashflow/{dateOrPeriod}") // localhost:0000/TKM1T
    public CashflowStatRaw getCompanySpecificTimeRawCashflowStat(@PathVariable final String tickerId, @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawIncomeStats with parameters -> tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        Long cashflowStatementIdWithSpecificDate = companyDimensionRepository.findByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);
        Optional<CashflowStatRaw> cashflowStatement = cashflowStatRawRepository.findById(cashflowStatementIdWithSpecificDate);

        return cashflowStatement.get();
    }

    // TODO Other cashflow & bilance statement requests


    /*
    @GetMapping("/{tickerId}/income/{dateOrPeriod}") // localhost:0000/TKM1T
    public List<IncomeStatRaw> getCompanySpecificTimeRawIncomeStats(@PathVariable final String tickerId, @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawIncomeStats with parameters -> tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        List<IncomeStatRaw> incomeStatementIdWithSpecificDate = companyDimensionRepository.findByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);

        return incomeStatementIdWithSpecificDate;
    }
     */




    // This will get tickedId from url, which will be available there from previous page and will
    // be forwarded to this moment
    @GetMapping("/{tickerId}")
    public CompanyDimension getCompany(@PathVariable final String tickerId) {

        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id: " + tickerId));
    }

}

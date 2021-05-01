package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStatementsRepository;
import com.taltech.stockscreenerapplication.repository.SourceCsvFileRepository;
import com.taltech.stockscreenerapplication.service.StatementsToDb.StatementsToDbHelperImpl;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderAndProcessImpl;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/readCsv")
public class ReadLocalCsvController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderAndProcessImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private SourceCsvFileRepository sourceCsvFileRepository;

    /*
    @Autowired // creates a singleton. I don't need a singleton.
    private StatementsToDbHelperImpl statementsToDbHelper;
     */
    @Autowired
    public StatementsToDbHelperImpl statementsToDbHelper;

    @Autowired
    private GroupOfStatementsRepository groupOfStatementsRepository;

    /* Advanced request example:
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


//    // This will accept CSV file that was inserted with React frontend as Json string or pure csv and return json result.
//    // It will be in unclean format!
//    @PostMapping(value="csvUpload", consumes = "application/json", produces = "application/json")
//    public ResponseEntity<MessageResponse> saveFormDataToDb(@PathVariable final String ticker, @RequestBody final String csvFile) {
//        CsvReaderImpl readerImpl = new CsvReaderImpl();
//        List<List<List<String>>> result = null;
//        try {
//            // Nr n source file, which contains 1 up to 3 statements
//            result = readerImpl.createReaderAndUseReadingMethod("tkm-2017_q2_CSV_modified_by_frontend.csv");
//        }
//        catch (Exception e) {
//            LOGGER.error(e.getMessage());
//        }
//
//        // now we want to save every statement and their period data to database.
//        /*
//        1. Andmed on kindlal kujul csv formaadis.
//        1. 1. Index 0 on alati: Income, Index 1: cashflow, Index 2: balance
//        2. Luuakse uus SourceCsvFile üksus
//        3. Luuakse incomeStatRaw objekt
//        4. Luuakse incomeStatRaw objekti jaoks Attribute objektid (nt 20 tk)
//        5. Need incomeStatRaw atribuudid on võimalik kätte saada läbi getAttributesMap'i
//        5.1 või alternatiivina luuakse repositooriumis eraldi getAttributes meetod, mille abil saadakse kätte
//        5.1 atribuutide id'd, mis kuuluvad kindla firma esitatud aruandele ning millel on kindla perioodi väärtus
//        6. (Tehakse kõik sama läbi teiste finantsaruannetega)
//        7.
//
//        */
//
//        if (result == null) {
//            LOGGER.error("Result entity contains null, which should actually be a list of three different lists");
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Something went wrong with reading in values from CSV file"));
//        }
//
//
//        // 2. Luuakse uus SourceCsvFile üksus
//
//        SourceCsvFile newSourceFile = new SourceCsvFile();
//        newSourceFile.setSourceFileName(Constants.FILENAME);
//        //String insertNewFileQuery = String.format("insert into source_csv_file (source_file_name) values %s", Constants.FILENAME);
//        sourceCsvFileRepository.save(newSourceFile);
//
//        // 3. Luuakse balanceStatRaw objekt
//
//        // get all incomeList rows of particular file
//        // first row will always contain dates for second column.
//        List<List<String>> incomeList = result.get(0);
//        List<String> firstRow = incomeList.get(0);
//        LOGGER.info("Size of the list is {} <-----------", firstRow.size());
//        List<String> incomeListDateEntries = firstRow.subList(1, firstRow.size() - 1);
//        List<List<String>> incomeListAttributesWithData = incomeList.subList(1, incomeList.size());
//
//        int i = 1; // starting from first value column
//        for (String dateEntry : incomeListDateEntries) {
//            // Creating raw income statement object for specific period (Q2 2017)
//            IncomeStatRaw newIncomeStatRaw = new IncomeStatRaw();
//            // setting source file
//            //newIncomeStatRaw.setSourceCsvFile(newSourceFile);
//
//            // Setting current period for raw income statement
//            newIncomeStatRaw.setDateOrPeriod(dateEntry);
//
//            List<Attribute> currentPeriodAttributes = new LinkedList<>();
//            for (List<String> dataLine : incomeListAttributesWithData) {
//                //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
//                //[Revenue (note: 16), 164,645, 150,534, 315,333, 287,384]
//
//                Attribute attr = new Attribute();
//                attr.setFieldName(dataLine.get(0));
//                LOGGER.info("This might break something <- Parsing to Double instead of double");
//                attr.setValue(Double.parseDouble(dataLine.get(i)));
//
//                // saving newly created attribute to db
//                currentPeriodAttributes.add(attr);
//                //attributeRepository.save(attr);
//            }
//            newIncomeStatRaw.setAttributes(currentPeriodAttributes);
//            incomeStatRawRepository.save(newIncomeStatRaw);
//            i++;
//        }
//
//
//        List<List<String>> cashflowList = result.get(1);
//        List<List<String>> balanceList = result.get(2);
//
//
//        return null;
//    }

    @GetMapping("/") // default mapping without additional arguments. Right now reading in csv
    public ResponseEntity<MessageResponse> getDefaultPage() {
        return ResponseEntity
                .status(200)
                .body(new MessageResponse("Welcome to readCsvController homepage. " +
                        "Read Usage guide to know how this controller can be used"));
    }

    /*
    1. Andmed on kindlal kujul csv formaadis.
    1. 1. Index 0: Income, Index 1: cashflow, Index 2: balance
    2. Luuakse uus SourceCsvFile üksus
    3. Luuakse incomeStatRaw objekt
    4. Luuakse incomeStatRaw objekti jaoks Attribute objektid (nt 20 tk) ja lisatakse sellele.
    6. (Tehakse kõik sama läbi teiste finantsaruannetega)
    7. Salvestatakse company uuendatud aruannetega
    */

    // TKM1T
    @GetMapping("/readAndSaveToDb/{ticker}/{fileName}")
    public ResponseEntity<MessageResponse> readAndSaveToDb(@PathVariable String ticker, @PathVariable String fileName) {
        boolean fileAlreadyExits = sourceCsvFileRepository
                .existsBySourceFileName("src/main/resources/csv/" + fileName + ".csv");

        if (fileAlreadyExits){
            LOGGER.error("File is already read in database");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            "File is already read in database"));
        }

        LOGGER.info("Starting reading in csv file");
        CsvReaderAndProcessImpl readerImpl = new CsvReaderAndProcessImpl();
        List<List<List<String>>> result = null;
        try {
            result = readerImpl.createReaderAndUseReadingMethod(fileName);
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

        // we need to find a firm, to upload a csv file to its name.
        CompanyDimension company = companyDimensionRepository
                .findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        LOGGER.info("Company name:::::::::::. {}", company.getName());

        // get lists for all statements rows of particular file
        // Example nr 1 (readme)
        List<List<String>> incomeList = result.get(0);
        List<List<String>> cashFlowList = result.get(1);
        List<List<String>> balanceList = result.get(2);

        // first rows of all income statements (header)
        // Ex: [Date_information (note: Note), Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]

        List<String> firstIncomeStatRow = incomeList.get(0);
        List<String> firstCashflowRow = cashFlowList.get(0);
        List<String> firstBalanceRow = balanceList.get(0);
        LOGGER.info("Size of the list is {} <-----------", firstIncomeStatRow.size());
        LOGGER.info("Size of the list is {} <-----------", firstCashflowRow.size());
        LOGGER.info("Size of the list is {} <-----------", firstBalanceRow.size());

        // Pure date lists of specific financial statements
        // Ex: [Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        List<String> incomeListDateEntries = firstIncomeStatRow.subList(1, firstIncomeStatRow.size());
        List<String> cashflowListDateEntries = firstCashflowRow.subList(1, firstCashflowRow.size());
        List<String> balanceListDateEntries = firstBalanceRow.subList(1, firstBalanceRow.size());

        // Specific financial statement list of attributes with data
        // Ex: [Revenue (note: 16), 164,645, 150,534, 315,333, 287,384],
        //     [Other operating income, 259, 684, 741, 951] ...
        List<List<String>> incomeListAttributesWithData = incomeList.subList(1, incomeList.size());
        List<List<String>> cashflowListAttributesWithData = cashFlowList.subList(1, cashFlowList.size());
        List<List<String>> balanceListAttributesWithData = balanceList.subList(1, balanceList.size());

        // 2. Luuakse uus SourceCsvFile üksus

        SourceCsvFile newSourceFile = new SourceCsvFile();
        newSourceFile.setSourceFileName(String.format("src/main/resources/csv/%s.csv", fileName));

        //statementsToDbHelper = new StatementsToDbHelperImpl();

        statementsToDbHelper.createNewIncomeFinStatementForSpecPeriod(incomeListDateEntries,
                incomeListAttributesWithData, company, newSourceFile);
        statementsToDbHelper.createNewCashflowFinStatementForSpecPeriod(cashflowListDateEntries,
                cashflowListAttributesWithData, company, newSourceFile);
        statementsToDbHelper.createNewBalanceFinStatementForSpecPeriod(balanceListDateEntries,
                balanceListAttributesWithData, company, newSourceFile);

        // new
        // length of largest collection of statements
        List<Integer> listInts = new LinkedList<>();
        listInts.add(statementsToDbHelper.currentCsvIncomeRawList.size()); // 4
        listInts.add(statementsToDbHelper.currentCsvCashflowRawList.size()); // 2
        listInts.add(statementsToDbHelper.currentCsvBalanceRawList.size()); // 2
        int maxLength = Collections.max(listInts);
        LOGGER.info("MaxLength: {}", maxLength);

        // Creating GroupsOfStatements
        for (int i = 0; i < maxLength; i++) {
            GroupOfStatements groupOfStatements = new GroupOfStatements();
            try {
                groupOfStatements.setIncomeStatRaw(statementsToDbHelper.currentCsvIncomeRawList.get(i));
            }
            catch (Exception ignored) { }
            try {
                groupOfStatements.setCashflowStatRaw(statementsToDbHelper.currentCsvCashflowRawList.get(i));
            }
            catch (Exception ignored) { }
            try {
                groupOfStatements.setBalanceStatRaw(statementsToDbHelper.currentCsvBalanceRawList.get(i));
            }
            catch (Exception ignored) { }

            /*
            groupOfStatements.setIncomeStatRaw(statementsToDbHelper.tempIncomeRawList.get(i));
            groupOfStatements.setCashflowStatRaw(statementsToDbHelper.tempCashflowRawList.get(i));
            groupOfStatements.setBalanceStatRaw(statementsToDbHelper.tempBalanceRawList.get(i));
             */

            //company.getGroupOfStatements().add(groupOfStatements);
            groupOfStatements.setCompanyDimension(company);
            groupOfStatementsRepository.save(groupOfStatements);

        }



        // End creating GroupsOfStatements

        companyDimensionRepository.save(company);

        newSourceFile.setTicker_id(ticker);
        sourceCsvFileRepository.save(newSourceFile);

        CompanyDimension com = companyDimensionRepository
                .findById(ticker).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        LOGGER.info("Thats how many company have incomestatements now: {} ", com.getIncomeRawStatements().size());
        LOGGER.info("Thats how many company have cashflowstatements now: {} ", com.getCashflowRawStatements().size());
        LOGGER.info("Thats how many company have balancestatements now: {} ", com.getBalanceRawStatements().size());

        return ResponseEntity
                .status(200)
                .body(new MessageResponse("Database seems to be populated successfully, check database"));
    }
}

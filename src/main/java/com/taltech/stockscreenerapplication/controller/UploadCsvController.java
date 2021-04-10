package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.Constants;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.SourceCsvFile;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
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
import java.util.*;

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
            newIncomeStatRaw.setSourceCsvFile(newSourceFile);

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

        // we need to find a firm, to upload a csv file to its name.

        Optional<CompanyDimension> company = companyDimensionRepository.findById(Constants.TESTFIRM);

        newSourceFile.setTicker_id(company.get());
        //String insertNewFileQuery = String.format("insert into source_csv_file (source_file_name) values %s", Constants.FILENAME);
        sourceCsvFileRepository.save(newSourceFile);

        // 3. Luuakse balanceStatRaw objekt

        // get all incomeList rows of particular file
        // first row will always contain dates for second column.
        List<List<String>> incomeList = result.get(0);
        List<String> firstRow = incomeList.get(0);
        LOGGER.info("Size of the list is {} <-----------", firstRow.size());
        List<String> incomeListDateEntries = firstRow.subList(1, firstRow.size());
        List<List<String>> incomeListAttributesWithData = incomeList.subList(1, incomeList.size());

        int i = 1; // starting from first value column
        for (String dateEntry : incomeListDateEntries) {
            // Creating raw income statement object for specific period (Q2 2017)
            IncomeStatRaw newIncomeStatRaw = new IncomeStatRaw();
            // setting source file
            newIncomeStatRaw.setSourceCsvFile(newSourceFile);

            // Setting current period for raw income statement
            LOGGER.info("Working with DATEORPERIOD: {} <---------", dateEntry);
            newIncomeStatRaw.setDateOrPeriod(dateEntry);

            List<Attribute> currentPeriodAttributes = new LinkedList<>();
            for (List<String> dataLine : incomeListAttributesWithData) {
                //[Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
                //[Revenue (note: 16), 164,645, 150,534, 315,333, 287,384]

                Attribute attr = new Attribute();
                attr.setFieldName(dataLine.get(0));

                // additional parsing , to .
                try {
                    NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
                    Number valueNum = format.parse(dataLine.get(i));
                    double valueDouble = valueNum.doubleValue();

                    LOGGER.info("This might break something <- Parsing to Double instead of double");
                    attr.setValue(valueDouble);
                }
                catch (ParseException e) {
                    LOGGER.error("PARSEEXCEPTION <-------------------");
                }
                // saving newly created attribute to db
                currentPeriodAttributes.add(attr);
                //attributeRepository.save(attr);
            }
            newIncomeStatRaw.setAttributes(currentPeriodAttributes);
            incomeStatRawRepository.save(newIncomeStatRaw);

            /*
            company.get().getIncomeRawStatements().add(newIncomeStatRaw);
            */

            i++;
        }
        // saving modified company, now with newly added rawStatements
        companyDimensionRepository.save(company.get());

        return ResponseEntity
                .status(201)
                .body(new MessageResponse("testing seems to work, check database"));
    }

    @GetMapping("/test/1")
    public List<Attribute> income1testing() {
        long one = 1;
        Optional<IncomeStatRaw> incomeStat1 = incomeStatRawRepository.findById(one);
        List<Attribute> incomeStat1Attributes = incomeStat1.get().getAttributes();
        return incomeStat1Attributes;
    }

    @GetMapping("/test/2")
    public List<Attribute> income2testing() {
        long two = 2;
        Optional<IncomeStatRaw> incomeStat2 = incomeStatRawRepository.findById(two);
        List<Attribute> incomeStat1Attributes = incomeStat2.get().getAttributes();
        return incomeStat1Attributes;
    }

    /*
    @GetMapping("/getTKM1TCompanyIncomeStatementsRaw")
    public List<IncomeStatRaw> getCompanyIncomeStatRaw() {
        Optional<CompanyDimension> company = companyDimensionRepository.findById("TKM1T");
        List<IncomeStatRaw> listOfRawIncomeStatements = company.get().getIncomeRawStatements();

        return listOfRawIncomeStatements;
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

package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.Constants;
import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.sourceFile.SourceCsvFile;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.SourceCsvFileRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
import com.taltech.stockscreenerapplication.service.csvreader.Statement;
import com.taltech.stockscreenerapplication.service.rawStats.RawStatsCreation;
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

import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private SourceCsvFileRepository sourceCsvFileRepository;

    @Autowired
    public RawStatsCreation statementsToDbHelper;

    @GetMapping("/readAndSave/{ticker}/{fileName}")
    public ResponseEntity<MessageResponse> readAndSaveToDb(@PathVariable String ticker, @PathVariable String fileName) {
        boolean fileAlreadyExits = sourceCsvFileRepository
                .existsBySourceFileName(Constants.UPLOAD_LOCATION + fileName + ".csv");

        if (fileAlreadyExits){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(
                            "File is already in the database. No need to add it a second time."));
        }

        LOGGER.info("Starting reading in csv file");
        CsvReaderImpl readerImpl = new CsvReaderImpl();
        List<List<List<String>>> result = null;
        try {
            result = readerImpl.createReaderAndUseReadingMethod(fileName);
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        if (result == null) { // Kas viia see meetod helper klassi? Throw exception.
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Something went wrong with reading in values from CSV file." +
                            " Result contains null"));
        }

        // we need to find a firm, to upload a csv file to its name.
        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker);

        // get lists for all statements rows of particular file
        List<List<String>> incomeList = result.get(0);
        List<List<String>> cashFlowList = result.get(1);
        List<List<String>> balanceList = result.get(2);

        // first rows of all income statements (header)
        // Ex: [Date_information (note: Note), Q2 2017, Q2 2016, 6 months 2017, 6 months 2016]
        List<String> firstIncomeStatRow = incomeList.get(0);
        List<String> firstCashflowRow = cashFlowList.get(0);
        List<String> firstBalanceRow = balanceList.get(0);

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

        // New source file object is created.
        SourceCsvFile newSourceFile = new SourceCsvFile();
        newSourceFile.setSourceFileName(String.format(Constants.UPLOADED_FILE_LOCATION, fileName));


        statementsToDbHelper.createNewFinStatementForEachPeriod(incomeListDateEntries,
                incomeListAttributesWithData, company, Statement.Statement_income);
        statementsToDbHelper.createNewFinStatementForEachPeriod(cashflowListDateEntries,
                cashflowListAttributesWithData, company, Statement.Statement_cashflow);
        statementsToDbHelper.createNewFinStatementForEachPeriod(balanceListDateEntries,
                balanceListAttributesWithData, company, Statement.Statement_balance);

        int maxLength = statementsToDbHelper.findMaxLengthOfStatementsInFile();
        statementsToDbHelper.createGroupsOfStatementsForCompany(maxLength, company);

        companyDimensionRepository.save(company);

        newSourceFile.setTicker_id(ticker);
        sourceCsvFileRepository.save(newSourceFile);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse("As is statements are stored to database successfuly."));
    }

    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }

}

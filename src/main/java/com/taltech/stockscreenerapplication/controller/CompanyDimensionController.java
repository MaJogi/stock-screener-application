package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderAndProcessImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyDimensionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderAndProcessImpl.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private IncomeStatRawRepository incomeStatRawRepository;

    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;


    @GetMapping
    public Iterable<CompanyDimension> getCompanies() {
        return companyDimensionRepository.findAll();
    }

    @GetMapping("/{tickerId}")
    public CompanyDimension getCompany(@PathVariable final String tickerId) {
        return findCompanyByIdWithExceptionHelper(tickerId);
    }

    //  Maybe Iterable<IncomeStatRaw>
    @GetMapping("/{tickerId}/incomeStatements") // localhost:8080/companies/TKM1T/incomeStatements
    public List<IncomeStatRaw> getCompanyRawIncomeStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getIncomeRawStatements();
    }

    @GetMapping("/{tickerId}/income/{dateOrPeriod}") // localhost:8080/companies/TKM1T
    public IncomeStatRaw getCompanySpecificTimeRawIncomeStats(@PathVariable final String tickerId,
                                                              @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawCashflowStats with parameters ->" +
                " tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        Long incomeStatementIdWithSpecificDate = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);

        return incomeStatRawRepository.findById(incomeStatementIdWithSpecificDate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find income financial statement with Id: " + incomeStatementIdWithSpecificDate));
    }


    @GetMapping("/{tickerId}/cashflowStatements") // localhost:8080/companies/TKM1T/cashflowStatements
    public List<CashflowStatRaw> getCompanyRawCashflowStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getCashflowRawStatements();
    }

    @GetMapping("/{tickerId}/cashflow/{dateOrPeriod}") // localhost:8080/companies/TKM1T/cashflow/{dateOrPeriod}
    public CashflowStatRaw getCompanySpecificTimeRawBalanceStat(@PathVariable final String tickerId,
                                                                @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawIncomeStats with parameters -> " +
                "tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        Long cashflowStatementIdWithSpecificDate = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);

        return cashflowStatRawRepository.findById(cashflowStatementIdWithSpecificDate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find cashflow financial statement with id: " + cashflowStatementIdWithSpecificDate));
    }

    @GetMapping("/{tickerId}/balanceStatements") // localhost:8080/companies/TKM1T/balanceStatements
    public List<BalanceStatRaw> getCompanyRawBalanceStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getBalanceRawStatements();
    }

    @GetMapping("/{tickerId}/balance/{dateOrPeriod}") // localhost:8080/companies/TKM1T/balance/{dateOrPeriod}
    public BalanceStatRaw getCompanySpecificTimeRawCashflowStat(@PathVariable final String tickerId,
                                                                @PathVariable final String dateOrPeriod) {
        LOGGER.info("Starting method getCompanySpecificTimeRawBalanceStats with parameters -> " +
                "tickerId: {} and dateOrPeriod: {}", tickerId, dateOrPeriod);
        Long balanceStatementIdWithSpecificDate = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(dateOrPeriod, tickerId);

        return balanceStatRawRepository.findById(balanceStatementIdWithSpecificDate)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        , "Unable to find balance financial statement with id: " + balanceStatementIdWithSpecificDate));
    }

    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }

    @GetMapping("/{tickerId}/getFullCombinationsIds")
    public List<Long> getFullCombinationOfStatementsIds(@PathVariable final String tickerId) {
        return companyDimensionRepository.findAllCompanyGroupOfStatementsWhereAllStatementsPresent(tickerId);
    }

    /* This functionality is done in GroupOfStatementsController */
    /*
    @GetMapping("/{tickerId}/getFullCombinations")
    public List<GroupOfStatements> getFullCombinationOfStatements(@PathVariable final String tickerId) {

        List<Long> listOfIds = companyDimensionRepository.findAllCompanyGroupOfStatementsWhereAllStatementsPresent(tickerId);
        //List<GroupOfStatements> allCompanyGroupOfStatements =
        List<GroupOfStatements> searchedCompanyGroupOfStatements = companyDimensionRepository.findAllCompanyGroupOfStatements(listOfIds);
        return searchedCompanyGroupOfStatements;
    }
     */
}

package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import com.taltech.stockscreenerapplication.service.csvreader.CsvReaderImpl;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderImpl.class);

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
    // localhost:8080/companies/TKM1T/incomeStatements
    @GetMapping("/{tickerId}/incomeStatements")
    public List<IncomeStatRaw> getCompanyRawIncomeStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getIncomeRawStatements();
    }

    // localhost:8080/companies/TKM1T/income/"Q3 2017"
    @GetMapping("/{tickerId}/income/{dateOrPeriod}")
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

    // localhost:8080/companies/TKM1T/cashflowStatements
    @GetMapping("/{tickerId}/cashflowStatements")
    public List<CashflowStatRaw> getCompanyRawCashflowStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getCashflowRawStatements();
    }

    // localhost:8080/companies/TKM1T/cashflow/{dateOrPeriod}
    @GetMapping("/{tickerId}/cashflow/{dateOrPeriod}")
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

    // localhost:8080/companies/TKM1T/balanceStatements
    @GetMapping("/{tickerId}/balanceStatements")
    public List<BalanceStatRaw> getCompanyRawBalanceStats(@PathVariable final String tickerId) {
        CompanyDimension company = findCompanyByIdWithExceptionHelper(tickerId);

        return company.getBalanceRawStatements();
    }

    // localhost:8080/companies/TKM1T/balance/{dateOrPeriod}
    @GetMapping("/{tickerId}/balance/{dateOrPeriod}")
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

    @GetMapping("/{tickerId}/getFullCombinationsIds")
    public List<Long> getFullCombinationOfStatementsIds(@PathVariable final String tickerId) {
        return companyDimensionRepository.findAllCompanyGroupOfStatementsWhereAllStatementsPresent(tickerId);
    }

    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }
}

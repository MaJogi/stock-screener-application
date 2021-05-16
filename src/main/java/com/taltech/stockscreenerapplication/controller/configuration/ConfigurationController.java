package com.taltech.stockscreenerapplication.controller.configuration;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.configuration.BalanceStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.CashflowStatConfig;
import com.taltech.stockscreenerapplication.model.statement.configuration.IncomeStatConfig;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.configuration.CompanyBalanceStatConfigRepository;
import com.taltech.stockscreenerapplication.service.configuration.ConfigCreation;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.BalanceMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.CashflowMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.request.statementMapping.IncomeMappingRequest;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private CompanyBalanceStatConfigRepository companyBalanceStatConfigRepository;

    @PostMapping(value = "/create/{ticker}/income",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleIncomeStatementMapping(
            @PathVariable String ticker, @RequestBody final IncomeMappingRequest incomeRequest) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker);

        IncomeStatConfig testIncomeConfig = new IncomeStatConfig();
        ConfigCreation.setIncomeConfigObjectFields(testIncomeConfig, incomeRequest);
        company.getIncomeConfigurations().add(testIncomeConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if income configuration containing new formulas has been created"));
    }

    @PostMapping(value = "/create/{ticker}/cashflow",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleCashflowStatementMapping(
            @PathVariable String ticker, @RequestBody final CashflowMappingRequest cashflowRequest) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker);

        CashflowStatConfig testCashflowConfig = new CashflowStatConfig();
        ConfigCreation.setCashflowConfigObjectFields(testCashflowConfig, cashflowRequest);
        company.getCashflowConfigurations().add(testCashflowConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if cashflow configuration containing new formulas has been created"));
    }

    @PostMapping(value = "/create/{ticker}/balance",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> singleBalanceStatementMapping(
            @PathVariable String ticker, @RequestBody final BalanceMappingRequest balanceRequest) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker);

        BalanceStatConfig testBalanceConfig = new BalanceStatConfig();
        ConfigCreation.setBalanceConfigObjectFields(testBalanceConfig, balanceRequest);
        company.getBalanceConfigurations().add(testBalanceConfig);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Mapping created, check if balance configuration containing new formulas has been created"));
    }

    @PutMapping(value = "/update/{ticker}/balance/{id}",
            produces = "application/json", consumes = "application/json")
    public ResponseEntity<MessageResponse> updatBalanceConfigMapping(
            @PathVariable String ticker, @RequestBody final BalanceMappingRequest balanceRequest,
            @PathVariable Long id) {

        CompanyDimension company = findCompanyByIdWithExceptionHelper(ticker);

        BalanceStatConfig balanceConfig = companyBalanceStatConfigRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find balance configuration with id: " + id));
        ConfigCreation.setBalanceConfigObjectFields(balanceConfig, balanceRequest);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "Balance Configuration updated"));
    }





    public CompanyDimension findCompanyByIdWithExceptionHelper(String tickerId) {
        return companyDimensionRepository.findById(tickerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with tickerId: " + tickerId));
    }
}


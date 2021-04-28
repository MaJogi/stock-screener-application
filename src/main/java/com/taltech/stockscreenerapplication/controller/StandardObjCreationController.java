package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
import com.taltech.stockscreenerapplication.repository.BalanceStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CashflowStatRawRepository;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.IncomeStatRawRepository;
import com.taltech.stockscreenerapplication.service.formulaToValue.StandardStatementCreationHelper;
import com.taltech.stockscreenerapplication.util.payload.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/standardStatementCreation")
public class StandardObjCreationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardObjCreationController.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private IncomeStatRawRepository incometatRawRepository;

    @Autowired
    private CashflowStatRawRepository cashflowStatRawRepository;

    @Autowired
    private BalanceStatRawRepository balanceStatRawRepository;

    // See peaks olema tegelikult POST stiilis. GET variant annaks lihtsalt ette vormi mustandi
    // kus saab valida ka vajaliku perioodi et just selle raw andmeid kuvada.
    @GetMapping("/{ticker}/createIncomeStatementFromFormula/forPeriod/{period_or_date}/")
    public ResponseEntity<MessageResponse> incomeMappingFromFormula(@PathVariable String ticker,
                                                                    @PathVariable String period_or_date) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        // Alternate way to get desiredRawIncome
        // List<IncomeStatRaw> rawIncomeStatements = company.getIncomeRawStatements();

        Long desiredRawIncomeStatId = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(period_or_date, ticker);

        IncomeStatRaw incomeStatementRaw = incometatRawRepository.findById(desiredRawIncomeStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find incomeStatementRaw with id: " + desiredRawIncomeStatId));;

        List<Attribute> attributesWithValues = incomeStatementRaw.getAttributes();
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        // sulgudes on see, mis objekti jaoks need valemid tehakse
        StandardEvaluationContext stContext  = new StandardEvaluationContext(incomeStatement);

        // stContext.setVariable("Revenue", 150);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig = company.getIncomeConfigurations().get(0);

        standardStatementCreationHelper.createIncomeStrings(rightCompanyIncomeConfig);

        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(incomeStandardFieldFormulas,
                parser, stContext);

        company.getIncomeStatements().add(incomeStatement);
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (income) returned. Check if result is correct"));
    }

    @GetMapping("/{ticker}/createCashflowStatementFromFormula/forPeriod/{period_or_date}/")
    public ResponseEntity<MessageResponse> cashflowMappingFromFormula(@PathVariable String ticker,
                                                                      @PathVariable String period_or_date) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        CashflowStatStandWithValues cashflowStatement = new CashflowStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        // Alternate way to get desiredRawCashflow
        //List<CashflowStatRaw> rawCashflowStatements = company.getCashflowRawStatements();

        // Miks läbi companyDimensionRepository? Sest seal olen juba kord realiseerinud selle findByDate... meetodi :D
        Long desiredRawCashflowStatId = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(period_or_date, ticker);

        CashflowStatRaw cashflowStatementRaw = cashflowStatRawRepository.findById(desiredRawCashflowStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find cashflowStatementRaw with id: " + desiredRawCashflowStatId));

        List<Attribute> attributesWithValues = cashflowStatementRaw.getAttributes();

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        // sulgudes on see, mis objekti jaoks need valemid tehakse
        StandardEvaluationContext stContext  = new StandardEvaluationContext(cashflowStatement);

        // stContext.setVariable("Revenue", 150);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        CompanyCashflowStatFormulaConfig rightCompanyCashflowConfig = company.getCashflowConfigurations().get(0);

        standardStatementCreationHelper.createCashflowStrings(rightCompanyCashflowConfig);
        List<String> cashflowStandardFieldFormulas = standardStatementCreationHelper.getCashflowStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(cashflowStandardFieldFormulas,
                parser, stContext);

        company.getCashflowStatements().add(cashflowStatement);
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (cashflow) returned. Check if result is correct"));
    }

    @GetMapping("/{ticker}/createBalanceStatementFromFormula/forPeriod/{period_or_date}/") // without ending "/" it is losing part of date!
    public ResponseEntity<MessageResponse> balanceMappingFromFormula(@PathVariable String ticker,
                                                                     @PathVariable String period_or_date) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));

        //List<BalanceStatRaw> rawBalanceStatements = company.getBalanceRawStatements();
        LOGGER.info("2");
        LOGGER.info("{}", period_or_date);
        Long desiredRawBalanceStatId = companyDimensionRepository
                .findBalanceRawIdByDateOrPeriodSpecificCompany(period_or_date, ticker);
        LOGGER.info("{}", desiredRawBalanceStatId);
        LOGGER.info("3");
        BalanceStatRaw balanceStatementRaw = balanceStatRawRepository.findById(desiredRawBalanceStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find balanceStatementRaw with id: " + desiredRawBalanceStatId));
        LOGGER.info("{}", balanceStatementRaw);
        LOGGER.info("4");
        List<Attribute> attributesWithValues = balanceStatementRaw.getAttributes();

        LOGGER.info("5");
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        // sulgudes on see, mis objekti jaoks need # tehakse
        StandardEvaluationContext stContext  = new StandardEvaluationContext(balanceStatement);

        // stContext.setVariable("Revenue", 150);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        LOGGER.info("6");
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString().replaceAll("\\s+", "_"));
        }

        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = company.getBalanceConfigurations().get(0);
        LOGGER.info("7");
        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);
        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(balanceStandardFieldFormulas,
                parser, stContext);
        LOGGER.info("8");
        company.getBalanceStatements().add(balanceStatement);
        LOGGER.info("9");
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }
}

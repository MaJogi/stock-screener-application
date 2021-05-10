package com.taltech.stockscreenerapplication.controller.groupOfStatements;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.balance.BalanceStatStandard;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandard;
import com.taltech.stockscreenerapplication.model.statement.formula.BalanceStatConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CashflowStatConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.FormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.IncomeStatConfig;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatementsStandard;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.income.IncomeStatStandard;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStandardStatementsRepository;
import com.taltech.stockscreenerapplication.repository.GroupOfStatementsRepository;
import com.taltech.stockscreenerapplication.service.groupOfStandardStats.StandardGroupOfStatementsCreationHelper;
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

import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/groupOfStandardStatements")
public class GroupOfStatementsStandardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupOfStatementsStandardController.class);

    @Autowired
    GroupOfStandardStatementsRepository groupOfStandardStatementsRepository;

    @Autowired
    GroupOfStatementsRepository groupOfStatementsRepository;

    @Autowired
    CompanyDimensionRepository companyDimensionRepository;

    @GetMapping
    public Iterable<GroupOfStatementsStandard> getGroupOfStatements() {
        return groupOfStandardStatementsRepository.findAll();
    }

    @GetMapping("/{id}")
    public GroupOfStatementsStandard getGroupOfStatement(@PathVariable final Long id) {
        return groupOfStandardStatementsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find groupOfStandardStatement with id: " + id));
    }

    @GetMapping("/for/{ticker_id}")
    public Iterable<GroupOfStatementsStandard> getGroupsOfStandardStatementsForCompany(
            @PathVariable final String ticker_id) {

        CompanyDimension company = findCompanyWithExceptionHandler(ticker_id);

        return groupOfStandardStatementsRepository.findAllByCompanyDimensionIs(company);
    }


    // without ending "/" it is losing part of date!
    @GetMapping("/{ticker}/create/forPeriod/{balance_date}/")
    public ResponseEntity<MessageResponse> createGroupOfStandardStatementsWithConfigurations(@PathVariable String ticker,
                                                                                             @PathVariable String balance_date) {
        StandardGroupOfStatementsCreationHelper standardGroupOfStatementsCreationHelper = new StandardGroupOfStatementsCreationHelper();

        // Creating empty standardStatements to populate them after.
        BalanceStatStandard balanceStatement = new BalanceStatStandard();
        CashflowStatStandard cashflowStatement = new CashflowStatStandard();
        IncomeStatStandard incomeStatement = new IncomeStatStandard();

        CompanyDimension company = findCompanyWithExceptionHandler(ticker);

        // Now we need to get statementGroup, which balance date is {balance_date}
        List<GroupOfStatements> allOfcompanyRawGroupOfStatements = groupOfStatementsRepository
                .findGroupOfStatementsWhereEveryStatementIsPresent(company.getTicker_id());

        GroupOfStatements rightRawGroupOfStatements = StandardGroupOfStatementsCreationHelper
                .findRightGroupOfStatements(allOfcompanyRawGroupOfStatements, balance_date);

        if (rightRawGroupOfStatements == null){
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find group of raw statement that is generated from csv file. Check if balance" +
                                    "date is correct"));
        }


        // now we need to find as is statements, which are going to be used later.
        BalanceStatRaw balanceStatementRaw = rightRawGroupOfStatements.getBalanceStatRaw();
        CashflowStatRaw cashflowStatementRaw = rightRawGroupOfStatements.getCashflowStatRaw();
        IncomeStatRaw incomeStatementRaw = rightRawGroupOfStatements.getIncomeStatRaw();

        if (balanceStatementRaw == null || cashflowStatementRaw == null || incomeStatementRaw == null) {
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find any raw statements. Check if company has imported csv files"));
        }

        // we will get as is statements fields with values for this period (balance_date)
        List<Attribute> balanceAttributesWithValues = balanceStatementRaw.getAttributes();
        List<Attribute> cashflowAttributesWithValues = cashflowStatementRaw.getAttributes();
        List<Attribute> incomeAttributesWithValues = incomeStatementRaw.getAttributes();

        for (Attribute attr : balanceAttributesWithValues) {
            LOGGER.info(attr.toString());
        }
        for (Attribute attr : cashflowAttributesWithValues) {
            LOGGER.info(attr.toString());
        }
        for (Attribute attr : incomeAttributesWithValues) {
            LOGGER.info(attr.toString());
        }


        standardGroupOfStatementsCreationHelper.createStContextes(balanceStatement, cashflowStatement, incomeStatement);

        standardGroupOfStatementsCreationHelper.populateContextesWithValues(balanceAttributesWithValues,
                cashflowAttributesWithValues, incomeAttributesWithValues);


        List<BalanceStatConfig> companyBalanceConfigs = company.getBalanceConfigurations();   //
        List<FormulaConfig> companyCashflowConfigs = new LinkedList<>(company.getCashflowConfigurations()); // all this has to be put in helper class
        List<FormulaConfig> companyIncomeConfigs = new LinkedList<>(company.getIncomeConfigurations());     //


        BalanceStatConfig rightCompanyBalanceConfig =
                (BalanceStatConfig) StandardGroupOfStatementsCreationHelper
                        .findRightBalanceConfig(companyBalanceConfigs, balance_date);

        if (rightCompanyBalanceConfig == null) {
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find suitable balance configuration. Does it even exits?"));
        }

        // I am looking at a collection id property of balance configuration, to find other (2) related configurations
        // that i am going to use later, to generate standard statements (group of them).
        Long companyConfigCollectionId = rightCompanyBalanceConfig.getCompany_config_collection_id();

        CashflowStatConfig cashflowConfig = (CashflowStatConfig) StandardGroupOfStatementsCreationHelper
                .findRightConfig(companyCashflowConfigs, companyConfigCollectionId);
        IncomeStatConfig incomeConfig = (IncomeStatConfig) StandardGroupOfStatementsCreationHelper
                .findRightConfig(companyIncomeConfigs, companyConfigCollectionId);


        if (cashflowConfig == null || incomeConfig == null) {
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find suitable cashflow Or income configuration object. " +
                                    "Does two of them even exist?"));
        }

        // new way to do all business logic in standardStatementCreationHelper.
        standardGroupOfStatementsCreationHelper.createBalanceStatement(rightCompanyBalanceConfig);
        standardGroupOfStatementsCreationHelper.createCashflowStatement(cashflowConfig);
        standardGroupOfStatementsCreationHelper.createIncomeStatement(incomeConfig);


        // Setting dependency, so I can get later on information, which config was used.
        balanceStatement.setBalance_stat_formula_id(rightCompanyBalanceConfig);
        cashflowStatement.setCashflow_stat_formula_id(cashflowConfig);
        incomeStatement.setIncome_stat_formula_id(incomeConfig);

        standardGroupOfStatementsCreationHelper.setDateToEachStatement(balanceStatement, cashflowStatement,
                incomeStatement, rightRawGroupOfStatements);

        //This is now the place to create new GroupOfStandardStatements with newly generated standard statements.
        GroupOfStatementsStandard groupOfStandardStatements =
                standardGroupOfStatementsCreationHelper.createGroupUsingPreviouslyFoundData(balanceStatement,
                        cashflowStatement, incomeStatement, company);

        // Check if groupOfStandardStatement with that balance id already exits
        GroupOfStatementsStandard possibleExistingGroupOfStatements =
                groupOfStandardStatementsRepository.findByBalanceStat_DatePeriodIs(balance_date);

        if (possibleExistingGroupOfStatements != null) {
            LOGGER.info("GROUP of standard statement already exits with that balance date. " +
                    "Updating values with current configurations");
            possibleExistingGroupOfStatements.setBalanceStat(balanceStatement);
            possibleExistingGroupOfStatements.setCashflowStat(cashflowStatement);
            possibleExistingGroupOfStatements.setIncomeStat(incomeStatement);
            groupOfStandardStatementsRepository.save(possibleExistingGroupOfStatements);
        }
        else {
            // Will add each new standard statement separately to company. (without a group)
            standardGroupOfStatementsCreationHelper.addStandardStatementsToRightCompanyLists(company, balanceStatement, cashflowStatement, incomeStatement);
            groupOfStandardStatementsRepository.save(groupOfStandardStatements);
            LOGGER.info("Created brand new group of standard statements");
        }

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }

    public CompanyDimension findCompanyWithExceptionHandler(String ticker) {
        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        LOGGER.info("FOUND COMPANY: {}", company);
        return company;
    }

}


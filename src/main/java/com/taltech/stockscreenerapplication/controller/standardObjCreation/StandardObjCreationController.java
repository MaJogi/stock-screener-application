package com.taltech.stockscreenerapplication.controller.standardObjCreation;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.attribute.Attribute;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatRaw;
import com.taltech.stockscreenerapplication.model.statement.balancestatement.BalanceStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatRaw;
import com.taltech.stockscreenerapplication.model.statement.cashflow.CashflowStatStandWithValues;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyBalanceStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyCashflowStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.groupOfStatements.GroupOfStatementsStandard;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatRaw;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
import com.taltech.stockscreenerapplication.repository.*;
import com.taltech.stockscreenerapplication.service.formulaToValue.StandardStatementCreationHelper;
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
@RequestMapping("/standardStatementCreation")
public class StandardObjCreationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardObjCreationController.class);

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    private GroupOfStatementsRepository groupOfStatementsRepository;

    @Autowired
    private GroupOfStandardStatementsRepository groupOfStandardStatementsRepository;

    /* Outdated, now we use mapping, which creates all of them (statements) at once
    @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */

    /*
    @GetMapping("/{ticker}/createIncomeStatementFromFormula/forPeriod/{period_or_date}/")
    public ResponseEntity<MessageResponse> incomeMappingFromFormula(@PathVariable String ticker,
                                                                    @PathVariable String period_or_date) {
        //SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();

        CompanyDimension company = findCompanyWithExceptionHandler(ticker);
        // Alternate way to get desiredRawIncome
        // List<IncomeStatRaw> rawIncomeStatements = company.getIncomeRawStatements();

        Long desiredRawIncomeStatId = companyDimensionRepository
                .findIncomeRawIdByDateOrPeriodSpecificCompany(period_or_date, ticker);

        IncomeStatRaw incomeStatementRaw = incometatRawRepository.findById(desiredRawIncomeStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find incomeStatementRaw with id: " + desiredRawIncomeStatId));

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

        // VANA
        // See on see koht, kus PEAKS PROGRAMM JUBA TEADMA, mis confi kasutada, mis perioodi puhul.
        CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig = company.getIncomeConfigurations().get(0);
        // VANA END

        standardStatementCreationHelper.createIncomeStrings(rightCompanyIncomeConfig);

        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(incomeStandardFieldFormulas,
                stContext);

        company.getIncomeStatements().add(incomeStatement);
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (income) returned. Check if result is correct"));
    }
    */

    /* Outdated, now we use mapping, which creates all of them (statements) at once
    @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */
    /*
    @GetMapping("/{ticker}/createCashflowStatementFromFormula/forPeriod/{period_or_date}/")
    public ResponseEntity<MessageResponse> cashflowMappingFromFormula(@PathVariable String ticker,
                                                                      @PathVariable String period_or_date) {
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        CashflowStatStandWithValues cashflowStatement = new CashflowStatStandWithValues();

        CompanyDimension company = findCompanyWithExceptionHandler(ticker);
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
                stContext);

        company.getCashflowStatements().add(cashflowStatement);
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (cashflow) returned. Check if result is correct"));
    }
     */

    // without ending "/" it is losing part of date!
    /* Outdated, now we use mapping, which creates all of them (statements) at once
    @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */
    /*
    @GetMapping("/{ticker}/createBalanceStatementFromFormula/forPeriod/{period_or_date}/")
    public ResponseEntity<MessageResponse> balanceMappingFromFormula(@PathVariable String ticker,
                                                                     @PathVariable String period_or_date) {
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();

        CompanyDimension company = findCompanyWithExceptionHandler(ticker);

        //List<BalanceStatRaw> rawBalanceStatements = company.getBalanceRawStatements();
        Long desiredRawBalanceStatId = companyDimensionRepository
                .findBalanceRawIdByDateOrPeriodSpecificCompany(period_or_date, ticker);
        BalanceStatRaw balanceStatementRaw = balanceStatRawRepository.findById(desiredRawBalanceStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find balanceStatementRaw with id: " + desiredRawBalanceStatId));
        List<Attribute> attributesWithValues = balanceStatementRaw.getAttributes();

        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.toString());
        }

        // sulgudes on see, mis objekti jaoks need # tehakse
        StandardEvaluationContext stContext  = new StandardEvaluationContext(balanceStatement);

        // stContext.setVariable("Revenue", 150);
        for (Attribute attr : attributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        for (Attribute attr : attributesWithValues) {
            LOGGER.info(attr.getFieldName().replaceAll("\\s+", "_"));
        }

        List<CompanyBalanceStatFormulaConfig> companyBalanceConfigs = company.getBalanceConfigurations();
        // Hetkel on nt periodOrDate "30.06.2017"
        // On olemas 31.12.2015 kuni 31.12.2016 ja teine conf 01.01.2017 - 31.12.2019

        // This can be some default value also
        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = null;
        Date dateObject = new Date();
        try {
            dateObject = new SimpleDateFormat("dd.MM.yyyy").parse(period_or_date);
            System.out.println(period_or_date +"\t" + dateObject );
        }
        catch (ParseException e) {
            LOGGER.info("ParseExceiption!");
        }

        for (CompanyBalanceStatFormulaConfig currentConfig : companyBalanceConfigs) {
            try {
                Date dateFrom = new SimpleDateFormat("dd.MM.yyyy").parse(currentConfig.getDateFrom());
                Date dateTo = new SimpleDateFormat("dd.MM.yyyy").parse(currentConfig.getDateTo());

                // convert date to calendar
                Calendar c = Calendar.getInstance();
                c.setTime(dateTo);
                c.add(Calendar.HOUR, 24);

                // convert calendar to date
                Date dateToOne = c.getTime();

                if (dateObject.after(dateFrom) && dateObject.before(dateToOne) ) {
                    rightCompanyBalanceConfig = currentConfig;
                    LOGGER.info("Right company balanceConfig id is: {}", rightCompanyBalanceConfig);
                    break;
                }
            }
            catch (ParseException e) {
                LOGGER.info("ParseException!");
            }

        }


        if (rightCompanyBalanceConfig == null) {
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find suitable balanceConfig. Does it even exist?"));
        }

        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);
        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(balanceStandardFieldFormulas,
                stContext);

        company.getBalanceStatements().add(balanceStatement);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }
     */

    // without ending "/" it is losing part of date!
    @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/")
    public ResponseEntity<MessageResponse> standardGroupMappingFromFormulaConfigurations(@PathVariable String ticker,
                                                                     @PathVariable String balance_date) {
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();

        // Creating empty standardStatements to populate them after.
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();
        CashflowStatStandWithValues cashflowStatement = new CashflowStatStandWithValues();
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();

        CompanyDimension company = findCompanyWithExceptionHandler(ticker);

        // Now we need to get statementGroup, which balance date is {balance_date}
        List<GroupOfStatements> allOfcompanyRawGroupOfStatements = groupOfStatementsRepository
                .findGroupOfStatementsByIncomeStatRawNotNullAndCashflowStatRawIsNotNullAndBalanceStatRawIsNotNullAndCompanyDimensionIs(company);

        GroupOfStatements rightRawGroupOfStatements = StandardStatementCreationHelper
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


        standardStatementCreationHelper.createStContextes(balanceStatement, cashflowStatement, incomeStatement);

        standardStatementCreationHelper.populateContextesWithValues(balanceAttributesWithValues,
                cashflowAttributesWithValues, incomeAttributesWithValues);


        List<CompanyBalanceStatFormulaConfig> companyBalanceConfigs = company.getBalanceConfigurations();
        List<CompanyCashflowStatFormulaConfig> companyCashflowConfigs = company.getCashflowConfigurations();
        List<CompanyIncomeStatFormulaConfig> companyIncomeConfigs = company.getIncomeConfigurations();


        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = StandardStatementCreationHelper
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

        CompanyCashflowStatFormulaConfig cashflowConfig = StandardStatementCreationHelper
                .findRightCashflowConfig(companyCashflowConfigs, companyConfigCollectionId);
        CompanyIncomeStatFormulaConfig incomeConfig = StandardStatementCreationHelper
                .findRightIncomeConfig(companyIncomeConfigs, companyConfigCollectionId);

        if (cashflowConfig == null || incomeConfig == null) {
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse(
                            "Couldn't find suitable cashflow Or income configuration object. Does it even exist?"));
        }

        // new way to do all business logic in standardStatementCreationHelper.
        standardStatementCreationHelper.createBalanceStatement(rightCompanyBalanceConfig);

        // here continues same process with other statements
        // cashflow
        standardStatementCreationHelper.createCashflowStrings(cashflowConfig);
        List<String> cashflowStandardFieldFormulas = standardStatementCreationHelper.getCashflowStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(cashflowStandardFieldFormulas,
                standardStatementCreationHelper.getStContextCashflow());
        //income
        standardStatementCreationHelper.createIncomeStrings(incomeConfig);
        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(incomeStandardFieldFormulas,
                standardStatementCreationHelper.getStContextIncome());


        // Setting dependency, so I can get later on information, which config was used.
        balanceStatement.setBalance_stat_formula_id(rightCompanyBalanceConfig);
        cashflowStatement.setCashflow_stat_formula_id(cashflowConfig);
        incomeStatement.setIncome_stat_formula_id(incomeConfig);

        setDateToEachStatement(balanceStatement, cashflowStatement,
                incomeStatement, rightRawGroupOfStatements);

        // Will add each new standard statement separately to company. (without a group)
        addStandardStatementsToRightCompanyLists(company, balanceStatement, cashflowStatement, incomeStatement);

        //This is now the place to create new GroupOfStandardStatements with newly generated standard statements.
        GroupOfStatementsStandard groupOfStandardStatements =
                createGroupUsingPreviouslyFoundData(balanceStatement, cashflowStatement, incomeStatement, company);

        groupOfStandardStatementsRepository.save(groupOfStandardStatements);
        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }

    public void setDateToEachStatement(BalanceStatStandWithValues balanceStatement,
                                       CashflowStatStandWithValues cashflowStatement,
                                       IncomeStatStandWithValues incomeStatement,
                                       GroupOfStatements rightRawGroupOfStatements) {
        // Setting peroid values from raw statement to standard statement
        balanceStatement.setDateOrPeriod(rightRawGroupOfStatements.getBalanceStatRaw().getDateOrPeriod());
        cashflowStatement.setDateOrPeriod(rightRawGroupOfStatements.getCashflowStatRaw().getDateOrPeriod());
        incomeStatement.setDateOrPeriod(rightRawGroupOfStatements.getIncomeStatRaw().getDateOrPeriod());
    }

    public void addStandardStatementsToRightCompanyLists(CompanyDimension company,
                                                         BalanceStatStandWithValues balanceStatement,
                                                         CashflowStatStandWithValues cashflowStatement,
                                                         IncomeStatStandWithValues incomeStatement){
        company.getBalanceStatements().add(balanceStatement);
        company.getCashflowStatements().add(cashflowStatement);
        company.getIncomeStatements().add(incomeStatement);
    }

    public GroupOfStatementsStandard createGroupUsingPreviouslyFoundData(BalanceStatStandWithValues balanceStatement,
                                                                         CashflowStatStandWithValues cashflowStatement,
                                                                         IncomeStatStandWithValues incomeStatement,
                                                                         CompanyDimension company) {
        GroupOfStatementsStandard groupOfStandardStatements = new GroupOfStatementsStandard();
        groupOfStandardStatements.setBalanceStat(balanceStatement);
        groupOfStandardStatements.setCashflowStat(cashflowStatement);
        groupOfStandardStatements.setIncomeStat(incomeStatement);
        groupOfStandardStatements.setCompanyDimension(company);
        return groupOfStandardStatements;
    }

    public CompanyDimension findCompanyWithExceptionHandler(String ticker) {
        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));
        LOGGER.info("FOUND COMPANY: {}", company);
        return company;
    }
}

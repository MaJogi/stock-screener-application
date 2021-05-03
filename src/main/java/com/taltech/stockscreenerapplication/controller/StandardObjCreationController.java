package com.taltech.stockscreenerapplication.controller;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatements;
import com.taltech.stockscreenerapplication.model.statement.GroupOfStatementsStandard;
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
import com.taltech.stockscreenerapplication.repository.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
    private GroupOfStatementsRepository groupOfStatementsRepository;

    @Autowired
    private GroupOfStandardStatementsRepository groupOfStandardStatementsRepository;

    /* Outdated, nüüd on kasutusel @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */
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

        // VANA
        // TODO: See on see koht, kus PEAKS PROGRAMM JUBA TEADMA, mis confi kasutada, mis perioodi puhul.
        CompanyIncomeStatFormulaConfig rightCompanyIncomeConfig = company.getIncomeConfigurations().get(0);
        // VANA END

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

    /* Outdated, nüüd on kasutusel @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */
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

    /* Outdated, nüüd on kasutusel @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") */
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


        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);
        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(balanceStandardFieldFormulas,
                parser, stContext);

        company.getBalanceStatements().add(balanceStatement);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }

    @GetMapping("/{ticker}/createGroupOfStandardStatements/forPeriod/{balance_date}/") // without ending "/" it is losing part of date!
    public ResponseEntity<MessageResponse> standardGroupMappingFromFormulaConfigurations(@PathVariable String ticker,
                                                                     @PathVariable String balance_date) {

        // Initializing Spel parser and helper class to help controller do its job
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardStatementCreationHelper standardStatementCreationHelper = new StandardStatementCreationHelper();

        // Creating empty standardStatements to populate them after.
        BalanceStatStandWithValues balanceStatement = new BalanceStatStandWithValues();
        CashflowStatStandWithValues cashflowStatement = new CashflowStatStandWithValues();
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();

        CompanyDimension company = companyDimensionRepository.findById(ticker)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find company with ticker: " + ticker));


        // Nüüd oleks vaja leida statementGroup, mille bilansi objekti date on {balance_date}
        List<GroupOfStatements> companyFullRawGroupOfStatements = groupOfStatementsRepository
                .findGroupOfStatementsByIncomeStatRawNotNullAndCashflowStatRawIsNotNullAndBalanceStatRawIsNotNullAndCompanyDimensionIs(company);
        GroupOfStatements rightRawGroupOfStatements = null;
        for (GroupOfStatements rawGroupOfStatements : companyFullRawGroupOfStatements) {
            if (rawGroupOfStatements.getBalanceStatRaw().getDateOrPeriod().equals(balance_date)) {
                rightRawGroupOfStatements = rawGroupOfStatements;
                break;
            }
        }

        // nüüd on vaja teha leida as is aruanded, mida hiljem kasutatakse.
        BalanceStatRaw balanceStatementRaw = rightRawGroupOfStatements.getBalanceStatRaw();
        CashflowStatRaw cashflowStatementRaw = rightRawGroupOfStatements.getCashflowStatRaw();
        IncomeStatRaw incomeStatementRaw = rightRawGroupOfStatements.getIncomeStatRaw();

        /*
        Long desiredRawBalanceStatId = companyDimensionRepository
                .findBalanceRawIdByDateOrPeriodSpecificCompany(balance_date, ticker);
        BalanceStatRaw balanceStatementRaw = balanceStatRawRepository.findById(desiredRawBalanceStatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find balanceStatementRaw with id: " + desiredRawBalanceStatId));
         */

        // saadakse kätte as is aruannete kirjed koos kindla perioodi väärtusega.
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

        // Spring expression language'i spetsiifiline context, tänu millele saab kasutada selle valemite mootorit.
        StandardEvaluationContext stContext  = new StandardEvaluationContext(balanceStatement);
        StandardEvaluationContext stContext2  = new StandardEvaluationContext(cashflowStatement);
        StandardEvaluationContext stContext3  = new StandardEvaluationContext(incomeStatement);

        // Atribuutide lisamine konteksti, et neid saaks hiljem muutujatena kasutada arvutustes
        // stContext.setVariable("Revenue", 150);
        for (Attribute attr : balanceAttributesWithValues) {
            stContext.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        for (Attribute attr : balanceAttributesWithValues) {
            LOGGER.info(attr.getFieldName().replaceAll("\\s+", "_"));
        }

        for (Attribute attr : cashflowAttributesWithValues) {
            stContext2.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        for (Attribute attr : cashflowAttributesWithValues) {
            LOGGER.info(attr.getFieldName().replaceAll("\\s+", "_"));
        }

        for (Attribute attr : incomeAttributesWithValues) {
            stContext3.setVariable(attr.getFieldName().replaceAll("\\s+", "_"), attr.getValue());
        }
        for (Attribute attr : incomeAttributesWithValues) {
            LOGGER.info(attr.getFieldName().replaceAll("\\s+", "_"));
        }


        List<CompanyBalanceStatFormulaConfig> companyBalanceConfigs = company.getBalanceConfigurations();
        // Hetkel on periodOrDate nt "30.06.2017"
        // On olemas 31.12.2015 kuni 31.12.2016 ja teine conf 01.01.2017 - 31.12.2019

        // Nüüd tuleb vastavalt bilansi ajale leida korrektne configuratsioon, mida kasutada standartse bilansiaruande
        // tegemise jaoks
        CompanyBalanceStatFormulaConfig rightCompanyBalanceConfig = null; // This can be some default value also

        Date dateObject = new Date();
        try {
            dateObject = new SimpleDateFormat("dd.MM.yyyy").parse(balance_date);
            System.out.println(balance_date +"\t" + dateObject );
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

        // Luuakse dünaamiliselt valemid, mille läbi saadakse hiljem standartsele bilansi aruandele väärtused
        // Lisatakse need vajalikku listi
        standardStatementCreationHelper.createBalanceStrings(rightCompanyBalanceConfig);

        // Nüüd saadakse kätte dünaamiliselt tehtud valemid
        List<String> balanceStandardFieldFormulas = standardStatementCreationHelper.getBalanceStandardFieldFormulas();

        // Ettevalmistus on tehtud. Nüüd käivitatakse meetod, mis kasutades muutujatega valemeid teevad valmis balanceStatemendi.
        standardStatementCreationHelper.createValuesForStatementFromFormulas(balanceStandardFieldFormulas,
                parser, stContext);

        // Lisan eraldi ettevõtte alla standartse rea väljaspool gruppi kolmest standartsest aruandest.
        company.getBalanceStatements().add(balanceStatement);

        // Vaatan, mis on selle ettevõtte bilansi konfiguratsiooni id, et leida veel kaks aruannet, mis on
        // Seotud selle konfiguratsiooni grupiga, et hiljem saaks seda kolmikut koos kasutada.
        Long companyConfigCollectionId = rightCompanyBalanceConfig.getCompany_config_collection_id();


        // Siin leitakse üles veel 2 aruande konfiguratsiooni, mis kuuluvad samasse kollektsiooni, kuhu kuulub eelmine
        // balance konfiguratsioon
        List<CompanyCashflowStatFormulaConfig> companyCashflowStatFormulaConfigs = company.getCashflowConfigurations();
        List<CompanyIncomeStatFormulaConfig> companyIncomeStatFormulaConfigs = company.getIncomeConfigurations();

        CompanyCashflowStatFormulaConfig cashflowConfig = null;
        CompanyIncomeStatFormulaConfig incomeConfig = null;
        for (CompanyCashflowStatFormulaConfig config : companyCashflowStatFormulaConfigs) {
            if (config.getCompany_config_collection_id() == companyConfigCollectionId) {
                cashflowConfig = config;
            }
            break;
        }

        for (CompanyIncomeStatFormulaConfig config : companyIncomeStatFormulaConfigs) {
            if (config.getCompany_config_collection_id() == companyConfigCollectionId) {
                incomeConfig = config;
            }
            break;
        }

        // Siin hakkab pihta kõik sama tegevus, mis ennegi juba teiste aruannetega.

        // cashflow
        standardStatementCreationHelper.createCashflowStrings(cashflowConfig);
        List<String> cashflowStandardFieldFormulas = standardStatementCreationHelper.getCashflowStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(cashflowStandardFieldFormulas,
                parser, stContext2);

        company.getCashflowStatements().add(cashflowStatement);

        //income
        standardStatementCreationHelper.createIncomeStrings(incomeConfig);
        List<String> incomeStandardFieldFormulas = standardStatementCreationHelper.getIncomeStandardFieldFormulas();
        standardStatementCreationHelper.createValuesForStatementFromFormulas(incomeStandardFieldFormulas,
                parser, stContext3);

        company.getIncomeStatements().add(incomeStatement);

        // Setting dependency so I can get later on information, which config was used.
        balanceStatement.setBalance_stat_formula_id(rightCompanyBalanceConfig);
        cashflowStatement.setCashflow_stat_formula_id(cashflowConfig);
        incomeStatement.setIncome_stat_formula_id(incomeConfig);

        // Setting peroid values from raw statement to standard statement
        balanceStatement.setDateOrPeriod(rightRawGroupOfStatements.getBalanceStatRaw().getDateOrPeriod());
        cashflowStatement.setDateOrPeriod(rightRawGroupOfStatements.getCashflowStatRaw().getDateOrPeriod());
        incomeStatement.setDateOrPeriod(rightRawGroupOfStatements.getIncomeStatRaw().getDateOrPeriod());


        //This is now the place to create new GroupOfStandardStatements
        GroupOfStatementsStandard groupOfStandardStatements = new GroupOfStatementsStandard();
        groupOfStandardStatements.setBalanceStat(balanceStatement);
        groupOfStandardStatements.setCashflowStat(cashflowStatement);
        groupOfStandardStatements.setIncomeStat(incomeStatement);
        groupOfStandardStatements.setCompanyDimension(company);
        groupOfStandardStatementsRepository.save(groupOfStandardStatements);

        companyDimensionRepository.save(company);

        return ResponseEntity
                .status(200)
                .body(new MessageResponse(
                        "GET method (balance) returned. Check if result is correct"));
    }
}

package com.taltech.stockscreenerapplication.service.formulaToValue;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.statement.formula.CompanyIncomeStatFormulaConfig;
import com.taltech.stockscreenerapplication.model.statement.incomestatement.IncomeStatStandWithValues;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FormulaToValueConverter {
    // Class takes formula input, uses spring expression parsing magic and creates standardvalue objekt and saves it to db (using repo).

    CompanyDimension company;

    @Autowired
    private CompanyDimensionRepository companyDimensionRepository;

    public void createNecessaryObjects() {
        this.company = companyDimensionRepository.findById("TKM1T").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find company by id:"));
    }


    public static void main(String args[]) {
        /*
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();

        Expression exp2 = parser.parseExpression("2 + 2");
        double message2 = (int) exp2.getValue();

        Expression exp3 = parser.parseExpression("5 * 5");
        double message3 = (int) exp3.getValue();

        Expression exp4 = parser.parseExpression("5 * 4");
        double message4 = (int) exp4.getValue();

        Expression exp5 = parser.parseExpression("(5 + 5) * 20");
        double message5 = (int) exp5.getValue();



        System.out.println(message);
        System.out.println(message2);
        System.out.println(message3);
        System.out.println(message4);
        System.out.println(message5);
         */

        CompanyIncomeStatFormulaConfig incomeStatementFormulas = new CompanyIncomeStatFormulaConfig();
        IncomeStatStandWithValues incomeStatement = new IncomeStatStandWithValues();

        StandardEvaluationContext stContext  = new StandardEvaluationContext(incomeStatement); // sulgudes on see, mis objekti jaoks need # tehakse
        stContext.setVariable("Revenue", 150);
        stContext.setVariable("Other_revenue", 10);

        SpelExpressionParser parser = new SpelExpressionParser();

        /*
        Expression expression = parser.parseExpression("revenue=#Revenue  + #Other_revenue");
        expression.getValue(stContext);
         */
        SpelExpression expression = parser.parseRaw("revenue=#Revenue  + #Other_revenue");
        expression.getValue(stContext);
        System.out.println("Income statement revenue is :  " + incomeStatement.getRevenue());

        /*
        expression = parser.parseRaw("telNo=#EmpTelephoneNo");
        expression.getValue(stContext);
        System.out.println("Employee's Telephone number : " + employee.getTelNo());


        expression = parser.parseRaw("positionGrade=#EmpPositionGrade");
        expression.getValue(stContext);
        System.out.println("Employee's Position Grade :  " + employee.getPositionGrade());


        expression = parser.parseRaw("yearsOfExp=#EmpExperience");
        expression.getValue(stContext);
        System.out.println("Employee's Years Of Exp : " + employee.getYearsOfExp());


        expression = parser.parseRaw("message=#EmpMessage");
        expression.getValue(stContext);
        System.out.println("Message for employee : " + employee.getMessage());
         */

    }


    public FormulaToValueConverter() {
        createNecessaryObjects();
    }
}

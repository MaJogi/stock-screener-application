package com.taltech.stockscreenerapplication.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// TODO
// REMOVE THIS AND ADD THOSE VALUES TO ALLOWEDDATEANDPERIODVALUES TABLE WITH INSERT INTO METHOD
// in data.sql file

public class AllowedDateAndPeriodValues {
    public static List<String> allowedBalanceDateValues = new LinkedList<>(
            Arrays.asList("") // + custom value dd-mm-yyyy, yyyy
    );
    public static List<String> allowedCashflowDateValues = new LinkedList<>(
            Arrays.asList("Q1 xxxx", "Q2 xxxx", "Q3 xxxx", "Q4 xxxx",
                            "3 months yyyy", "6 months yyyy", "9 months yyyy", "12 months yyyy")// + yyyy
    );
    public static List<String> allowedIncomeDateValues = new LinkedList<>(
            Arrays.asList("Q1 xxxx", "Q2 xxxx", "Q3 xxxx", "Q4 xxxx",
                    "3 months yyyy", "6 months yyyy", "9 months yyyy", "12 months yyyy")// + yyyy
    );
}

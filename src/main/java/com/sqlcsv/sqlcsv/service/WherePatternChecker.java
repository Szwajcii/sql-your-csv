package com.sqlcsv.sqlcsv.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WherePatternChecker {
    private final String SINGLEWHEREPATTERN = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*(or|and)?\\s*";
    Pattern pattern = Pattern.compile(SINGLEWHEREPATTERN);
    Matcher matcher;
    private String columnName;
    private String operator;
    private String condition;
    private String linkingOperator;

    WherePatternChecker(String inputPattern){
        matcher = pattern.matcher(inputPattern);
    }


    public String getColumnName() {
        return columnName;
    }
    public String getOperator() {
        return operator;
    }

    public String getCondition() {
        return condition;
    }

    public boolean evaluatePattern() {
        if (matcher.find()) {
            columnName = matcher.group(1);
            operator = matcher.group(2);
            condition = matcher.group(3);
            linkingOperator = matcher.group(4);
            return true;
        }
        return false;
    }

    public boolean hasAdditionalConditions(){
        return linkingOperator != null;
    }


    public String getNextLinkingOperator() {
        return linkingOperator;
    }
}

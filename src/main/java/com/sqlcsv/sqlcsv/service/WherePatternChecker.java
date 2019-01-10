package com.sqlcsv.sqlcsv.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WherePatternChecker {
    private final String SINGLEWHEREPATTERN = "^(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*(.*)";
    private String columnName;
    private String operator;
    private String condition;
    private String additionalConditions;

    public String getColumnName() {
        return columnName;
    }
    public String getOperator() {
        return operator;
    }

    public String getCondition() {
        return condition;
    }

    public String getAdditionalConditions() {
        return additionalConditions;
    }

    public boolean evaluatePattern(String inputPattern) {
        Pattern pattern = Pattern.compile(SINGLEWHEREPATTERN);
        Matcher matcher = pattern.matcher(inputPattern);
        if (matcher.matches()) {
            columnName = matcher.group(1);
            operator = matcher.group(2);
            condition = matcher.group(3);
            additionalConditions = matcher.group(4);
            return true;
        }
        return false;
    }

    public boolean hasAdditionalConditions(){
        return !additionalConditions.isEmpty();
    }

    //TODO
    public String getNextLinkingOperator() {
        return "AND";
    }
}

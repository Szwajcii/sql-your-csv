package com.sqlcsv.sqlcsv.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectService {
    private String fileName;

    private WherePatternChecker patternChecker;

    public SelectService(String filename, String inputPattern){
        this.fileName = filename;
        patternChecker = new WherePatternChecker(inputPattern);
    }

    public List<String> readHeaders(){
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            return stream.limit(1).flatMap(s -> Arrays.stream(s.split(","))).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> evaluateWhereCondition(){
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            return stream.skip(1).filter(getPredicates()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Predicate<String> getPredicates(){
        if (patternChecker.evaluatePattern()){
            String columnName = patternChecker.getColumnName();
            String operation = patternChecker.getOperator();
            String condition = patternChecker.getCondition();
            Integer index = getIndex(columnName);

            if (index != null) {
                Predicate<String> predicate = buildPredicate(index, operation, condition);
                if (patternChecker.hasAdditionalConditions()){
                    String linkingOperator = patternChecker.getNextLinkingOperator();
                    return joinPredicates(predicate, getPredicates(), linkingOperator);
                } else
                    return predicate;
            }
        }
        return null;
    }

    private Predicate<String> joinPredicates(Predicate<String> predicate1, Predicate<String> predicate2, String operator){
        if (operator.equalsIgnoreCase("or")){
            return predicate1.or(predicate2);
        } else
            return predicate1.and(predicate2);
    }


    private Integer getIndex(String columnName){
        List<String> headers = readHeaders();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equals(columnName))
                return i;
        }
        return null;
    }

    private Predicate<String> buildPredicate(Integer index, String operation, String condition) {
        return s -> choosePredicate(operation, condition).test(s.split(",")[index]);
    }

    private Predicate<String> choosePredicate(String operation, String condition) {
        if (operation.equals("like")) {
            return s -> s.equals(condition);
        }
        return null;
    }
}
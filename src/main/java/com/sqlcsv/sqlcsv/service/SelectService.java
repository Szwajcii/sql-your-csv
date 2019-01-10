package com.sqlcsv.sqlcsv.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectService {
    private String fileName;

    public SelectService(String filename){
        this.fileName = filename;
    }

    public List<String> readHeaders(){
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            return stream.limit(1).flatMap(s -> Arrays.stream(s.split(","))).collect(Collectors.toList());
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> selectWhere(String whereClause){
        String wherePattern = "^(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*(.*)"; // column_name like wow
        Pattern pattern = Pattern.compile(wherePattern);
        Matcher matcher = pattern.matcher(whereClause);

        if (matcher.find()) {
            String columnName = matcher.group(1);
            String operation = matcher.group(2);
            String condition = matcher.group(3);

            Integer index = getIndex(columnName);

            if (index != null) {
                try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                    return filterStream(stream, index, operation, condition).collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private Integer getIndex(String columnName){
        List<String> headers = readHeaders();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equals(columnName))
                return i;
        }
        return null;
    }

    private Stream<String> filterStream(Stream<String> stream, Integer index, String operation, String condition) {
        return stream.skip(1).filter(s -> isConditionTrue(s.split(",")[index], operation, condition));
    }

    private boolean isConditionTrue(String valueFromTable, String operation, String condition) {
        boolean result = false;


        switch(operation) {

            case "like":
                result = isElementLike(valueFromTable, condition);
                break;
            case ">":
                result = isElementBigger(valueFromTable, condition);
                break;
            case "<":
                result = isElementSmaller(valueFromTable, condition);
                break;
            case "=":
                result = isElementEqual(valueFromTable, condition);
                break;
            case "<>":
                result = isNotEqual(valueFromTable, condition);
                break;
        }

        return result;
    }

    private List<String> getColumnsToSearch(String query) {

        List<String> columnsName = new ArrayList<>();

        String[] queryArray = query.split(" ");

        for(int i = 1; i < queryArray.length; i++){
            if(!queryArray[i].equalsIgnoreCase("FROM")) {
                columnsName.add(queryArray[i]);
            }
        }

        return columnsName;
    }


    private boolean isElementSmaller(String valueFromTable, String condition) {
        return Integer.valueOf(valueFromTable) < Integer.valueOf(condition);
    }

    private boolean isElementBigger(String valueFromTable, String condition) {
        return Integer.valueOf(valueFromTable) > Integer.valueOf(condition);
    }

    private boolean isElementEqual(String valueFromTable, String condition) {
        return valueFromTable.equalsIgnoreCase(condition);
    }

    private boolean isNotEqual(String valueFromTable, String condition) {
        return !valueFromTable.equalsIgnoreCase(condition);
    }

    private boolean isElementLike(String valueFromTable, String condition) {

        if(condition.startsWith("%")) { // '%szawa'
            return valueFromTable.endsWith(condition.substring(1, condition.length()));
        } else if (condition.endsWith("%")) { // 'War%'
            return valueFromTable.startsWith(condition.substring(0, condition.length() - 1));
        }

        return false;
    }

}
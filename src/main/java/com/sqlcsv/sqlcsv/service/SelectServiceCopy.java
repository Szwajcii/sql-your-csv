package com.sqlcsv.sqlcsv.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectServiceCopy {

    private String fileName;

    public SelectServiceCopy(String fileName) {
        this.fileName = fileName;
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

    private List<Predicate> getPredicatesFromQuery(List<String[]> conditionList) {
        List<Predicate> predicateList = new ArrayList<>();

        for(int i = 0; i < conditionList.size(); i++){

            switch (operation) {
                case "like":
                    Predicate<String> predicateLike = p -> {
                        if(conditionList.get(i)[3].startsWith("%")) { // '%szawa'
                            return p.endsWith(condition.substring(1, condition.length()));
                        } else if (condition.endsWith("%")) { // 'War%'
                            return p.startsWith(condition.substring(0, condition.length() - 1));
                        } else { // 'Warszawa'
                            return p.equals(condition);
                        }
                    };
                    predicateList.add(predicateLike);
                    break;
                case ">":
                    Predicate<String> predicateBigger = p -> Integer.valueOf(p) > Integer.valueOf(condition);
                    predicateList.add(predicateBigger);
                    break;
                case "<":
                    Predicate<String> predicateSmaller = p -> Integer.valueOf(p) < Integer.valueOf(condition);
                    predicateList.add(predicateSmaller);
                    break;
                case "=":
                    Predicate<String> predicateEquals = p -> p.equals(condition);
                    predicateList.add(predicateEquals);
                    break;
                case "<>":
                    Predicate<String> predicateNotEquals = p -> !p.equals(condition);
                    predicateList.add(predicateNotEquals);
                    break;

            }

        }

        return null;
    }

}

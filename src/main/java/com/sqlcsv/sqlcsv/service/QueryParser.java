package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryParser {


    public List<String> parseQuerytoList(String s) {
        return Arrays.stream(s.split(" ")).collect(Collectors.toList());
    }

    public List<String> getColumnsFromSelect(List<String> query) {

        List<String> columns = new ArrayList<>();

        for(int i = 1; i < query.size(); i++) {

            if(!query.get(i).equalsIgnoreCase("FROM")){
                columns.add(query.get(i));
            } else {
                break;
            }
        }

        return columns;
    }


    public List<Integer> getColumnsIndexes(List<String> columns, Table table){

        List<Integer> columnsIndexes = new ArrayList<>();

        if(!columns.contains("*")) {
            for (String column : columns) {
                columnsIndexes.add(table.getRows().get(0).getData().indexOf(column));
            }
        } else {
            for(String element : table.getRows().get(0).getData()) {
                columnsIndexes.add(table.getRows().get(0).getData().indexOf(element));
            }
        }

        return columnsIndexes;
    }


}

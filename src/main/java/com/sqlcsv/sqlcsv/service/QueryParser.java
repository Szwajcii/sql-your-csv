package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Row;
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

        for (int i = 1; i < query.size(); i++) {

            if (!query.get(i).equalsIgnoreCase("FROM")) {
                columns.add(query.get(i));
            } else {
                break;
            }
        }

        return columns;
    }


    public List<Integer> getColumnsIndexes(List<String> columns, Table table) {

        List<Integer> columnsIndexes = new ArrayList<>();

        if (!columns.contains("*")) {
            for (String column : columns) {
                columnsIndexes.add(table.getRows().get(0).getData().indexOf(column));
            }
        } else {
            for (String element : table.getRows().get(0).getData()) {
                columnsIndexes.add(table.getRows().get(0).getData().indexOf(element));
            }
        }

        return columnsIndexes;
    }

    public List<String> getColumnsValues(Integer index, Table table) {

        List<String> columnValues = new ArrayList<>();

        for (Row row : table.getRows()) {
            for (int j = 0; j < row.getData().size(); j++) {
                if (j == index)
                    columnValues.add(row.getData().get(j));
            }
        }
        return columnValues;
    }

    public List<List<String>> getColumnsFromIndexes(List<Integer> indexes, Table table) {

        List<List<String>> results = new ArrayList<>();

        for (Integer index : indexes) {
            results.add(getColumnsValues(index, table));
        }

        return results;
    }


    public void queryParserHandler(String s, Table table) {

        List<String> query = parseQuerytoList(s);

        String fileName = query.get(query.size() - 1);

        List<String> columnsFromSelect = getColumnsFromSelect(query);
        List<Integer> columnsIndexes = getColumnsIndexes(columnsFromSelect, table);
        List<List<String>> result = getColumnsFromIndexes(columnsIndexes, table);


        for(List<String> row : result) {
            System.out.println(row.toString());
        }

    }


    public String getFileName(List<String> query) {

        String fileName = "";

        for(int i = 0; i < query.size(); i++){
            if(query.get(i).equalsIgnoreCase("FROM")){
                fileName = query.get(i) + 1;
            }
        }
        return fileName;
    }


}
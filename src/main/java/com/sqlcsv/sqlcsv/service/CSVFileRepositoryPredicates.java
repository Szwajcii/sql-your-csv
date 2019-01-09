package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Table;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CSVFileRepositoryPredicates {
    public static Predicate<Table> isSameName(String name) {
        return p -> p.getName().equals(name);
    }

    public static List<Table> filterCSVFiles(List<Table> csvFiles, Predicate<Table> predicate) {
        return csvFiles.stream().filter(predicate).collect(Collectors.toList());
    }
}

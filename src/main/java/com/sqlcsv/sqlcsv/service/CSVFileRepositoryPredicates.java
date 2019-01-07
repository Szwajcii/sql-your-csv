package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.CSVFile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Component
public class CSVFileRepositoryPredicates {
    public static Predicate<CSVFile> isSameName(String name) {
        return p -> p.getName().equals(name);
    }

    public static List<CSVFile> filterCSVFiles(List<CSVFile> csvFiles, Predicate<CSVFile> predicate) {
        return csvFiles.stream().filter(predicate).collect(Collectors.toList());
    }
}

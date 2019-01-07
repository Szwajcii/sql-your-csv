package com.sqlcsv.sqlcsv.repository;

import com.sqlcsv.sqlcsv.model.CSVFile;

import java.util.List;
import java.util.Optional;

public interface CSVFileRepositoryInterface {
    CSVFile save(CSVFile file);
    Optional<CSVFile> getById(int id);
    List<CSVFile> getAll();
    boolean deleteById(int id);
    List<CSVFile> findByName(String name);

}

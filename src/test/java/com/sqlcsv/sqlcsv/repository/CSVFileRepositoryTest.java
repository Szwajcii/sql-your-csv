package com.sqlcsv.sqlcsv.repository;

import com.sqlcsv.sqlcsv.model.Table;
import com.sqlcsv.sqlcsv.model.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVFileRepositoryTest {

    @Test
    void testAddingFile_ListShouldHaveItOnFirstIndex() {
        CSVFileRepository repo = new CSVFileRepository();
        List<Row> csvFileData = new ArrayList<>();
        List<String> columnData = new ArrayList<>();
        List<String> entityData = new ArrayList<>();
        columnData.add("columnOne");
        columnData.add("ColumnTwo");
        entityData.add("columnOneValue");
        entityData.add("columnTwoValue");
        Row columnRow = new Row(columnData);
        Row entityRow = new Row(entityData);
        csvFileData.add(columnRow);
        csvFileData.add(entityRow);
        Table newFile = new Table("testFile.csv", csvFileData);

        repo.save(newFile);
        assertTrue(repo.getById(0).isPresent());
        Table savedFile = repo.getById(0).get();

        assertEquals(newFile, savedFile);
    }

}
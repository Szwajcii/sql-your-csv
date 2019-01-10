package com.sqlcsv.sqlcsv.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectServiceTest {
    private SelectService selectService;
    private final String FILEPATH = "src/main/resources/csv/test.csv";

    @BeforeEach
    void buildUp(){
            selectService = new SelectService(FILEPATH);
    }

    @Test
    void testGettingCorrectHeadersFromFile() {
        List<String> expected = new ArrayList<> (Arrays.asList("first_name", "last_name", "age", "city"));
        List<String> result = selectService.readHeaders();
        assertEquals(expected, result);
    }

    @Test
    void testGetOneRowWithComparingStringsThatIsInCSV() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String whereStatement = "city like Warszawa";
        List<String> result = selectService.evaluateWhereCondition(whereStatement);
        assertEquals(expected, result);
    }

    @Test
    void testGetNoRowsWithComparingStrings() {
        List<String> expected = new ArrayList<>();
        String whereStatement = "city like Budapest";
        List<String> result = selectService.evaluateWhereCondition(whereStatement);
        assertEquals(expected, result);
    }
}
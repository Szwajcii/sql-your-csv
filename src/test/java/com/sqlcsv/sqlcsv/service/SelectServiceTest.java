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

}
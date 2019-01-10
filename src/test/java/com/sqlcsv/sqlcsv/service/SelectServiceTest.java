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
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetNoRowsWithComparingStrings() {
        List<String> expected = new ArrayList<>();
        String whereStatement = "city like Budapest";
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetElementsWhichStartsSimilarly() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String whereStatement = "city like %szawa";
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetElementsWhichEndsSimilarly() {
        selectService = new SelectService(FILEPATH, "city like War%");
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetBiggerElementThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Adam,Mickiewicz,60,Paryż","Krzysztof,Krawczyk,60,Warszawa"));
        String whereStatement = "age > 50";
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);

    }

    @Test
    void testGetSmallerElementsThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Bob,Ross,40,New York", "Jack,Daniels,18,London", "Adam,Małysz,40,Wisła", "Sam,Samotny,22,Warszawa"));
        String whereStatement = "age < 50";
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetTheSameElementsAsGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa"));
        String whereStatement = "last_name = Krawczyk";
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

    @Test
    void testGetDifferentElementsThanGiven() {
        selectService = new SelectService(FILEPATH, "last_name <> Krawczyk");
        List<String> expected = new ArrayList<> (Arrays.asList("Bob,Ross,40,New York",
                                                                "Jack,Daniels,18,London",
                                                                "Adam,Małysz,40,Wisła",
                                                                "Adam,Mickiewicz,60,Paryż",
                                                                "Sam,Samotny,22,Warszawa"));
        List<String> result = selectService.evaluateWhereCondition();
        assertEquals(expected, result);
    }

}
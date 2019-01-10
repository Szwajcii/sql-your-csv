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

    @Test
    void testGetElementsWhichStartsSimilarly() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String statement = "city like %szawa";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetElementsWhichEndsSimilarly() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String statement = "city like War%";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetBiggerElementThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Adam,Mickiewicz,60,Paryż","Krzysztof,Krawczyk,60,Warszawa"));
        String statement = "age > 50";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);

    }

    @Test
    void testGetSmallerElementsThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Bob,Ross,40,New York", "Jack,Daniels,18,London", "Adam,Małysz,40,Wisła", "Sam,Samotny,22,Warszawa"));
        String statement = "age < 50";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetTheSameElementsAsGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa"));
        String statement = "last_name = Krawczyk";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetDifferentElementsThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Bob,Ross,40,New York",
                "Jack,Daniels,18,London",
                "Adam,Małysz,40,Wisła",
                "Adam,Mickiewicz,60,Paryż",
                "Sam,Samotny,22,Warszawa"));
        String statement = "last_name <> Krawczyk";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetElementLikeGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String statement = "city like Warszawa";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetElementWhereAgeIsGreaterThan18AndLowerThan40() {
        List<String> expected = new ArrayList<> (Arrays.asList("Sam,Samotny,22,Warszawa"));
        String statement = "age > 18 and age < 40";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

    @Test
    void testGetElementWhereAgeEquals18OrAgeEquals22() {
        List<String> expected = new ArrayList<> (Arrays.asList("Jack,Daniels,18,London","Sam,Samotny,22,Warszawa"));
        String statement = "age = 18 or age = 22";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);

    }

    @Test
    void testGetElementLikeGivenAndGreaterThanGiven() {
        List<String> expected = new ArrayList<> (Arrays.asList("Krzysztof,Krawczyk,60,Warszawa", "Sam,Samotny,22,Warszawa"));
        String statement = "city like %szawa and age > 18";
        List<String> result = selectService.evaluateWhereCondition(statement);
        assertEquals(expected, result);
    }

}
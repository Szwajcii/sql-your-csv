package com.sqlcsv.sqlcsv.service;

import org.junit.jupiter.api.BeforeEach;

class SelectServiceTest {
    private SelectService selectService;
    private final String FILEPATH = "src/main/resources/csv/test.csv";

    @BeforeEach
    void buildUp(){
            selectService = new SelectService(FILEPATH);
    }

}
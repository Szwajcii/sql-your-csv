package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Table;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService implements TableServiceInterface {


    @Override
    public List<String> getAllSpreadsheetsIds() {
        return null;
    }

    @Override
    public List<String> gettAllSheetsNames(int spreadsheetId) {
        return null;
    }

    @Override
    public Table getTableById(int sheetId) {
        return null;
    }

    @Override
    public Table queryTheTable(String query) {
        return null;
    }
}

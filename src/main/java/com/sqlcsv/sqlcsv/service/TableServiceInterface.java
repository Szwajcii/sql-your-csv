package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Table;

import java.io.IOException;
import java.util.List;

public interface TableServiceInterface {
    List<String> getAllSpreadsheetsIds() throws IOException;
    List<String> gettAllSheetsNames(int spreadsheetId);
    Table getTableById(int sheetId);
    Table queryTheTable(String query);
}

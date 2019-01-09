package com.sqlcsv.sqlcsv.service;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sqlcsv.sqlcsv.google.GoogleAPI;
import com.sqlcsv.sqlcsv.model.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TableService implements TableServiceInterface {
    private GoogleAPI api;

    @Autowired
    public TableService(GoogleAPI api) {
        this.api = api;
    }

    @Override
    public List<String> getAllSpreadsheetsIds() throws IOException {
        FileList result = api.getDrive().files().list().execute();
        List<File> filess = result.getFiles();
//        System.out.println("Files:");
//        for (File file : filess) {
//            System.out.printf("%s (%s)\n", file.getName(), file.getId());
//        }
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

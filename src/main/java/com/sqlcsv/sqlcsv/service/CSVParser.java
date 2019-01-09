package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.Table;
import com.sqlcsv.sqlcsv.model.Row;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVParser {

    private String directoryPath = "/home/szwajcii/Desktop/Java Advanced/TW_4/sql-your-csv/src/main/resources/CSV_Files/";
    private String csvSplitBy = ",";
    private String line;
    private Table CSVFile = new Table();

    public Table parseCSVFile(String fileName){

        try (BufferedReader br = new BufferedReader(new FileReader(directoryPath+fileName))) {

            while ((line = br.readLine()) != null) {

                Row row = new Row();
                row.setData(Arrays.asList(line.split(csvSplitBy)));

                CSVFile.getRows().add(row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.CSVFile;
    }

    public static void main(String[] args) {
        Table lol = new CSVParser().parseCSVFile("playlist_song.csv");

        QueryParser parser = new QueryParser();

        parser.queryParserHandler("SELECT * FROM playlist_song.csv", lol);

    }
}

package com.sqlcsv.sqlcsv.service;

import com.sqlcsv.sqlcsv.model.CSVFile;
import com.sqlcsv.sqlcsv.model.Row;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVParser {

    private String directoryPath = "/home/cyan/codecool/advance_java/repositories/sql-your-csv/src/main/resources/CSV_Files/";
    private String csvSplitBy = ",";
    private String line;
    private CSVFile CSVFile = new CSVFile();

    public CSVFile parseCSVFile(String fileName){

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
        CSVFile lol = new CSVParser().parseCSVFile("playlist_song.csv");
    }
}

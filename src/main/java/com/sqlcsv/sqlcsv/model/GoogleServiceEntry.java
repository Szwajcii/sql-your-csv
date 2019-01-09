package com.sqlcsv.sqlcsv.model;

import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleServiceEntry {
    private final Drive drive;
    private final Sheets sheets;
}

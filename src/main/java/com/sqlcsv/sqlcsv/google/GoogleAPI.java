package com.sqlcsv.sqlcsv.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.sqlcsv.sqlcsv.model.GoogleServiceEntry;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Component
@Getter
public class GoogleAPI {

    private static final String APPLICATION_NAME = "sqlyourcsv";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File CREDENTIALS_FOLDER = new java.io.File("/home/bartosz/Codecool/Advanced/sql-your-csv/src/main/resources");
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
    private static final List<String> DRIVE_SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final List<String> SHEETS_SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private Drive drive;
    private Sheets sheets;

    public GoogleAPI() throws IOException, GeneralSecurityException {
        System.out.println("1");
        GoogleServiceEntry gse = getServices();
        this.drive = gse.getDrive();
        this.sheets = gse.getSheets();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, List<String> scopes) throws IOException {
        java.io.File clientSecretFile = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
        InputStream clientSecretInputStream = new FileInputStream(clientSecretFile);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecretInputStream));
        System.out.println("2");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, scopes).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                .setAccessType("offline").build();
        System.out.println("3");
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private GoogleServiceEntry getServices() throws IOException, GeneralSecurityException {
        System.out.println("4");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, GoogleAPI.SHEETS_SCOPES)).setApplicationName(APPLICATION_NAME).build();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, GoogleAPI.DRIVE_SCOPES)).setApplicationName(APPLICATION_NAME).build();
        System.out.println("5");
        return new GoogleServiceEntry(drive, sheets);
    }
}
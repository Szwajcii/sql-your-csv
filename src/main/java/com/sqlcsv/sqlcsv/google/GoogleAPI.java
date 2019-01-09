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
import java.util.List;

@Component
@Getter
public class GoogleAPI {

    private static final String APPLICATION_NAME = "sqlyourcsv";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File CREDENTIALS_FOLDER = new java.io.File("/home/bartosz/Codecool/Advanced/sql-your-csv/src/main/resources");
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
    private static final List<String> SCOPES = List.of(DriveScopes.DRIVE_READONLY, SheetsScopes.SPREADSHEETS_READONLY);

    private Drive drive;
    private Sheets sheets;

    public GoogleAPI() throws IOException, GeneralSecurityException {
        GoogleServiceEntry gse = getServices();
        this.drive = gse.getDrive();
        this.sheets = gse.getSheets();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        java.io.File clientSecretFile = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
        InputStream clientSecretInputStream = new FileInputStream(clientSecretFile);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecretInputStream));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, GoogleAPI.SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                .setAccessType("offline").build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    private GoogleServiceEntry getServices() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credentials = getCredentials(HTTP_TRANSPORT);
        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials).setApplicationName(APPLICATION_NAME).build();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials).setApplicationName(APPLICATION_NAME).build();
        return new GoogleServiceEntry(drive, sheets);
    }
}
package com.sqlcsv.sqlcsv.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.SheetsScopes;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
@Getter
public class GoogleAuthorizationFlow {

    private static final String APPLICATION_NAME = "sqlyourcsv";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final java.io.File CREDENTIALS_FOLDER = new java.io.File("/home/bartosz/Codecool/Advanced/sql-your-csv/src/main/resources");
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
    private static final List<String> SCOPES = List.of(DriveScopes.DRIVE_READONLY, SheetsScopes.SPREADSHEETS_READONLY);

    private GoogleAuthorizationCodeFlow flow;

    public GoogleAuthorizationFlow() throws IOException, GeneralSecurityException {
        this.flow = getNewFlow();
    }

    private GoogleAuthorizationCodeFlow getNewFlow() throws IOException, GeneralSecurityException {
        return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                getClientSecrets(), SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
                .setAccessType("offline").setApprovalPrompt("force").build();
    }

    private GoogleClientSecrets getClientSecrets() throws IOException {
        java.io.File clientSecretFile = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
        InputStream clientSecretInputStream = new FileInputStream(clientSecretFile);
        return GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(clientSecretInputStream));
    }
}
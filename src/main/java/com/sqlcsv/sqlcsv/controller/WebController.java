package com.sqlcsv.sqlcsv.controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.sqlcsv.sqlcsv.google.GoogleAuthorizationFlow;
import com.sqlcsv.sqlcsv.service.DriveService;
import com.sqlcsv.sqlcsv.service.SheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
public class WebController {
    private DriveService driveService;
    private SheetsService sheetsService;

    @Autowired
    public WebController(DriveService driveService, SheetsService sheetsService) {
        this.sheetsService = sheetsService;
        this.driveService = driveService;
    }


    @GetMapping("/")
    public void getAuthPage(HttpServletResponse response) throws IOException, GeneralSecurityException {
        String url = GoogleAuthorizationFlow.getNewFlow().newAuthorizationUrl()
                .setRedirectUri("http://localhost:8080/callback")
                .build();
        response.sendRedirect(url);
    }
    @GetMapping("/home")
    public String doGet(Model model) throws IOException, GeneralSecurityException {

//        model.addAttribute("spreadsheets", spreadsheets);
        return "home";
    }
    @GetMapping("/choose")
    public String getChoosePage(Model model) throws IOException, GeneralSecurityException {
        Map<String, String> spreadsheets = driveService.getAllSpreadsheets();
        model.addAttribute("spreadsheets", spreadsheets);
        return "choosePage";
    }

    @GetMapping("/callback")
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException {
        String code = request.getParameter("code");
        authorizeAndSaveToken(code);
        response.sendRedirect("http://localhost:8080/choose");

    }

    private void authorizeAndSaveToken(String code) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = GoogleAuthorizationFlow.getNewFlow();
        GoogleAuthorizationCodeTokenRequest query = flow
                .newTokenRequest(code)
                .setRedirectUri("http://localhost:8080/callback")
                .setClientAuthentication(flow.getClientAuthentication())
                .setCode(code)
                .set("response-type", "code")
                .setGrantType("authorization_code");

        TokenResponse tokenResponse = query.execute();
        flow.createAndStoreCredential(tokenResponse,"user");
    }


}

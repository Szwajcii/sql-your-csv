package com.sqlcsv.sqlcsv.controller.exception;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.sqlcsv.sqlcsv.google.GoogleAuthorizationFlow;
import com.sqlcsv.sqlcsv.service.SpreadsheetsService;
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
public class OneToRuleThemAllController {
    private SpreadsheetsService spreadsheetsService;

    @Autowired
    public OneToRuleThemAllController(SpreadsheetsService spreadsheetsService) {
        this.spreadsheetsService = spreadsheetsService;
    }


    @GetMapping("/")
    public void getAuth(HttpServletResponse response) throws IOException, GeneralSecurityException {
        String url = GoogleAuthorizationFlow.getNewFlow().newAuthorizationUrl()
                .setRedirectUri("http://localhost:8080/callback")
                .build();
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/home")
    public String doGet(Model model) throws IOException, GeneralSecurityException {
        Map<String, String> spreadsheets = spreadsheetsService.getAllSpreadsheets();
        System.out.println(spreadsheets);
        model.addAttribute("spreadsheets", spreadsheets);
        return "choosePage";
    }

    @GetMapping("/callback")
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException {
        String code = request.getParameter("code");
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
        response.sendRedirect("http://localhost:8080/home");

    }


}

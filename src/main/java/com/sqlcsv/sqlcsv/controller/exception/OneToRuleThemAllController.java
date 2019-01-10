package com.sqlcsv.sqlcsv.controller.exception;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.sqlcsv.sqlcsv.google.GoogleAuthorizationFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OneToRuleThemAllController {

    private GoogleAuthorizationFlow googleAuthorizationFlow;

    @Autowired
    public OneToRuleThemAllController(GoogleAuthorizationFlow googleAuthorizationFlow) {
        this.googleAuthorizationFlow = googleAuthorizationFlow;
    }

    @GetMapping("/")
    public void getAuth(HttpServletResponse response) {
        String url = googleAuthorizationFlow.getFlow().newAuthorizationUrl()
                .setRedirectUri("http://localhost:8080/callback")
                .build();
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/home")
    public String doGet(Model model) {
        model.addAttribute("entry", "yeah it's working!");
        return "home";
    }

    @GetMapping("/callback")
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        GoogleAuthorizationCodeTokenRequest query = googleAuthorizationFlow.getFlow()
                .newTokenRequest(code)
                .setRedirectUri("http://localhost:8080/callback")
                .setClientAuthentication(googleAuthorizationFlow.getFlow().getClientAuthentication())
                .setCode(code)
                .set("response-type", "code")
                .setGrantType("authorization_code");

        TokenResponse tokenResponse = query.execute();
        googleAuthorizationFlow.getFlow().createAndStoreCredential(tokenResponse,"user");
        response.sendRedirect("http://localhost:8080/home");

    }


}

package com.sqlcsv.sqlcsv.controller.exception;

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

    @GetMapping("/test")
    public String dog() {
        return "dupa";
    }

    @GetMapping("/callback")
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        System.out.println("kurwa chuj");
        googleAuthorizationFlow.getFlow()
                .newTokenRequest(code)
                .setRedirectUri("http://localhost:8080/home").execute();

//        createApiClients(tokenResponse);
//        response.sendRedirect("http://localhost");
//
//        return null;
    }


}

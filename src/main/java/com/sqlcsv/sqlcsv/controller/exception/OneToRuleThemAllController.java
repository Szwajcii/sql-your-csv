package com.sqlcsv.sqlcsv.controller.exception;

import com.sqlcsv.sqlcsv.google.GoogleAuthorizationFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public String getToken(HttpServletRequest request) {
        String code = request.getParameter("code");
        System.out.println(code);
        return "home";
    }
}

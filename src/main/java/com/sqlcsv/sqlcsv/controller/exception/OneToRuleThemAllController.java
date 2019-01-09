package com.sqlcsv.sqlcsv.controller.exception;

import com.sqlcsv.sqlcsv.service.TableServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class OneToRuleThemAllController {
    private TableServiceInterface tsi;

    @Autowired
    public OneToRuleThemAllController(TableServiceInterface tsi) {
        this.tsi = tsi;
    }

    @GetMapping("/")
    public String doGet(Model model) throws IOException {
        tsi.getAllSpreadsheetsIds();
        model.addAttribute("entry", "yeah it's working!");
        return "home";
    }
}

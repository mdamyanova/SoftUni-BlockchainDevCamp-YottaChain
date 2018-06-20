package com.faucet.controllers;

import com.faucet.services.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    private final TransactionService certificateService;

    @Autowired
    public PageController(TransactionService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView homePage = new ModelAndView("home");
        homePage.addObject("title", "Faucet");
        return homePage;
    }
}

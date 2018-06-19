package com.softuni.faucet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView homePage = new ModelAndView("home.html");
        homePage.addObject("title", "Faucet");
        return homePage;
    }
}

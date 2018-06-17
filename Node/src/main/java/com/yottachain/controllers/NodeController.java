package com.yottachain.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class NodeController {

    @RequestMapping("/")
    public String index() {
        return "ZDR";
    }
}
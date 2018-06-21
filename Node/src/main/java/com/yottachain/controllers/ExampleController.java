package com.yottachain.controllers;

import com.yottachain.entities.Example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @RequestMapping("/greeting")
    public Example greeting() {
        return new Example("Hello!");
    }
}
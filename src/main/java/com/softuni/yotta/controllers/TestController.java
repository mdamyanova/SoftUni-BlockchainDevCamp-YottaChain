package com.softuni.yotta.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity hello() {
        return new ResponseEntity("Hello world", HttpStatus.OK);
    }
}

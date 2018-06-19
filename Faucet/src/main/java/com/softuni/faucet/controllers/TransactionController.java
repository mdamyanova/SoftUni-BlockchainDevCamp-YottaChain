package com.softuni.faucet.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TransactionController {

    @PostMapping("/transaction")
    public ResponseEntity sentTransaction() {

        return new ResponseEntity("Hi", HttpStatus.OK);
    }
}

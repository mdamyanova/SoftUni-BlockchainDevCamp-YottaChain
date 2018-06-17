package com.yottachain.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TransactionsController {

    // GET Pending Transactions
    @GetMapping("/transactions/pending")
    public ResponseEntity<String> pending() {
        // TODO - return list

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }

    // GET Confirmed Transactions
    @GetMapping("/transactions/confirmed")
    public ResponseEntity<String> confirmed() {
        // TODO - return list

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }

    // GET Transaction by Hash
    @GetMapping("/transactions/{hash}")
    public ResponseEntity<String> byHash() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK); // or 404
    }

    // GET All Account Balances
    @GetMapping("/balances")
    public ResponseEntity<String> balances() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }

    // GET Transactions for Address
    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<String> byAddress() {
        // TODO - return list

        return new ResponseEntity<String>("TODO", HttpStatus.OK); // or 404
    }

    // GET Balances for Address
    @GetMapping("/address/{address}/balance")
    public ResponseEntity<String> balancesByAddress() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK); // or 404
    }

    // POST Send Transaction
    @GetMapping("/transactions/send")
    public ResponseEntity<String> send() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.CREATED); // or 400
    }
}
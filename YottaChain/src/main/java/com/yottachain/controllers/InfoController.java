package com.yottachain.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class InfoController {

    // GET Info
    @GetMapping("/info")
    public ResponseEntity<String> info() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }

    // GET Debug
    @GetMapping("/debug")
    public ResponseEntity<String> debug() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }

    // GET Reset chain
    @GetMapping("/debug/reset-chain")
    public ResponseEntity<String> resetChain() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK);
    }
}
package com.yottachain.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @MessageMapping("/hello")
    @SendTo("/peer/hello")
    public String hello() {
        return "Hello!";
    }
}
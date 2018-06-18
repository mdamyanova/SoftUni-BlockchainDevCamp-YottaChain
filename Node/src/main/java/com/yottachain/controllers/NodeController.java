package com.yottachain.controllers;

import com.yottachain.entities.Block;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class NodeController {

    private final NodeService nodeService;

    public NodeController() {
        this.nodeService = new NodeServiceImpl();
        int difficulty = 5;
    }

    @RequestMapping("/")
    public String index() {
        return "ZDR";
    }
}
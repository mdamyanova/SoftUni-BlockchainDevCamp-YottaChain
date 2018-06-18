package com.yottachain.controllers;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@CrossOrigin
@RestController
public class BlocksController {

    // TODO - initialisation is not good
    private final NodeServiceImpl nodeService = new NodeServiceImpl();

    // GET Blocks
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockViewModel>> blocks() {
        return new ResponseEntity<>(nodeService.getAllBlocks(), HttpStatus.OK);
    }

    // GET Block by Id
    @GetMapping("/blocks/{id}")
    public ResponseEntity<BlockViewModel> byId(@PathVariable int id) throws Exception {
        // TODO - 404 ?
        BlockViewModel m = nodeService.getBlock(id);
        return new ResponseEntity<>(m, HttpStatus.OK); // or 404
    }
}
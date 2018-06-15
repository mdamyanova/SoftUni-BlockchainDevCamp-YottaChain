package com.yottachain.controllers;

import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class BlocksController {

    private final NodeService nodeService;

    public BlocksController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    // GET Blocks
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockViewModel>> blocks() {
        List<BlockViewModel> allBlocks = nodeService.GetAllBlocks();
        return new ResponseEntity<List<BlockViewModel>>(allBlocks, HttpStatus.OK);
    }

    // GET Block by Id
    @GetMapping("/blocks/{id}")
    public ResponseEntity<String> debug() {
        // TODO

        return new ResponseEntity<String>("TODO", HttpStatus.OK); // or 404
    }
}
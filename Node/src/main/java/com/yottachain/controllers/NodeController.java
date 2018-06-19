package com.yottachain.controllers;

import com.yottachain.entities.Block;
import com.yottachain.models.viewModels.BlockViewModel;
import com.yottachain.models.viewModels.NodeInfoViewModel;
import com.yottachain.models.viewModels.ResetChainViewModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NodeController {

    private final NodeService nodeService;

    public NodeController() {
        this.nodeService = new NodeServiceImpl();
        int difficulty = 5;
    }

    @RequestMapping("/")
    public String index() {
        return "<h1>ZDR</h1>";
    }

    // GET Reset Chain
    @GetMapping("/debug/reset-chain")
    public ResponseEntity<ResetChainViewModel> reset() {
        return new ResponseEntity<>(nodeService.resetChain(), HttpStatus.OK);
    }

    // GET Info
    @GetMapping("/info")
    public ResponseEntity<NodeInfoViewModel> info() {
        return new ResponseEntity<>(nodeService.getInfo(), HttpStatus.OK);
    }

    // GET Blocks
    @GetMapping("/blocks")
    public ResponseEntity<List<BlockViewModel>> blocks() {
        return new ResponseEntity<>(nodeService.getAllBlocks(), HttpStatus.OK);
    }

    // GET Block by Id
    @GetMapping("/blocks/{id}")
    public ResponseEntity<BlockViewModel> byId(@PathVariable int id) throws Exception {
        // TODO - 404 ?
        return new ResponseEntity<>(nodeService.getBlock(id), HttpStatus.OK); // or 404
    }

    // GET Pending Transactions
    @GetMapping("/transactions/pending")
    public ResponseEntity<List<TransactionViewModel>> pending() {
        return new ResponseEntity<>(nodeService.getPendingTransactions(), HttpStatus.OK);
    }
}
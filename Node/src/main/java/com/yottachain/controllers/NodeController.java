package com.yottachain.controllers;

import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.models.viewModels.*;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import com.yottachain.utils.Helpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.Request;

import java.util.List;

@RestController
public class NodeController {

    private final NodeService nodeService;

    public NodeController() {
        this.nodeService = new NodeServiceImpl();
    }

    @RequestMapping("/")
    public String index() {
        return Helpers.html; // TODO - list all requests for more user friendly API :))
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
    public ResponseEntity<BlockViewModel> blockById(@PathVariable int id) throws Exception {
        return new ResponseEntity<>(nodeService.getBlock(id), HttpStatus.OK); // or 404
    }

    // GET Pending Transactions
    @GetMapping("/transactions/pending")
    public ResponseEntity<List<TransactionViewModel>> pending() {
        return new ResponseEntity<>(nodeService.getTransactions(false), HttpStatus.OK);
    }

    // GET Confirmed Transactions
    @GetMapping("/transactions/confirmed")
    public ResponseEntity<List<TransactionViewModel>> confirmed() {
        return new ResponseEntity<>(nodeService.getTransactions(true), HttpStatus.OK);
    }

    // GET Transaction by Hash
    @GetMapping("/transactions/{hash}")
    public ResponseEntity<TransactionViewModel> transactionByHash(@PathVariable String hash) {
        return new ResponseEntity<>(nodeService.getTransactionByHash(hash), HttpStatus.OK); // or 404
    }

    // GET All Account Balances
    @GetMapping("/balances")
    public ResponseEntity<List<BalanceViewModel>> balances() {
        return new ResponseEntity<>(nodeService.getBalances(), HttpStatus.OK);
    }

    // GET Transactions for Address
    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<List<TransactionViewModel>> transactionsByAddress(@PathVariable String address) {
        return new ResponseEntity<>(nodeService.getTransactionsByAddress(address), HttpStatus.OK); // or 404
    }

    // TODO - Get Balances for Address

    // TODO
    // POST Create a Transaction
    @PostMapping("/transactions/send")
    public ResponseEntity<TransactionCreatedViewModel> sendTransaction(@RequestBody TransactionBindingModel transaction) throws Exception {
        return new ResponseEntity<>(nodeService.addTransaction(transaction), HttpStatus.CREATED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewModel> exceptionHandler(Exception e) {
        ErrorViewModel model = new ErrorViewModel();
        model.setMessage(e.getMessage());
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
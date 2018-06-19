package com.yottachain.controllers;

import com.fasterxml.jackson.core.JsonParser;
import com.yottachain.models.viewModels.*;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        String html = "<h1>YottaChain - Simple Blockchain Network</h1><ul><li>GET <a href=\"/\">/</a></li><li>GET <a href=\"/info\">/info</a></li><li>GET <a href=\"/debug\">/debug</a></li><li>GET <a href=\"/debug/reset-chain\">/debug/reset-chain</a></li><li>GET <a href=\"/debug/mine/:minerAddress/:difficulty\">/debug/mine/:minerAddress/:difficulty</a></li><li>GET <a href=\"/blocks\">/blocks</a></li><li>GET <a href=\"/blocks/:index\">/blocks/:index</a></li><li>GET <a href=\"/transactions/pending\">/transactions/pending</a></li><li>GET <a href=\"/transactions/confirmed\">/transactions/confirmed</a></li><li>GET <a href=\"/transactions/:tranHash\">/transactions/:tranHash</a></li><li>GET <a href=\"/balances\">/balances</a></li><li>GET <a href=\"/address/:address/transactions\">/address/:address/transactions</a></li><li>GET <a href=\"/address/:address/balance\">/address/:address/balance</a></li><li>POST <a href=\"/transactions/send\">/transactions/send</a></li><li>GET <a href=\"/peers\">/peers</a></li><li>POST <a href=\"/peers/connect\">/peers/connect</a></li><li>POST <a href=\"/peers/notify-new-block\">/peers/notify-new-block</a></li><li>GET <a href=\"/mining/get-mining-job/:address\">/mining/get-mining-job/:address</a></li><li>POST <a href=\"/mining/submit-mined-block\">/mining/submit-mined-block</a></li></ul>";
        return html; // TODO - list all requests for more user friendly API :))
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
    @GetMapping("/transactions/send")
    public ResponseEntity<TransactionCreatedViewModel> sendTransaction() {
        return null; // TODO
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewModel> exceptionHandler(Exception e) {
        ErrorViewModel model = new ErrorViewModel();
        model.setMessage(e.getMessage());
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
package com.yottachain.controllers;

import com.yottachain.entities.MiningJob;
import com.yottachain.models.bindingModels.MiningJobBindingModel;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.models.viewModels.*;
import com.yottachain.services.implementations.NodeServiceImpl;
import com.yottachain.services.interfaces.NodeService;
import com.yottachain.utils.Helpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NodeController {

    private NodeService nodeService;

    public NodeController() {
        this.nodeService = new NodeServiceImpl();
    }

    @RequestMapping("/")
    public String index() {
        return Helpers.html;
    }

    @GetMapping("/debug/reset-chain")
    public ResponseEntity<ResetChainViewModel> reset() {
        return new ResponseEntity<>(this.nodeService.resetChain(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<NodeInfoViewModel> info() {
        return new ResponseEntity<>(this.nodeService.getInfo(), HttpStatus.OK);
    }

    @GetMapping("/blocks")
    public ResponseEntity<List<BlockViewModel>> blocks() {
        return new ResponseEntity<>(this.nodeService.getAllBlocks(), HttpStatus.OK);
    }

    @GetMapping("/blocks/{id}")
    public ResponseEntity<BlockViewModel> blockById(
            @PathVariable int id) throws Exception {
        return new ResponseEntity<>(this.nodeService.getBlock(id), HttpStatus.OK);
    }

    @GetMapping("/transactions/pending")
    public ResponseEntity<List<TransactionViewModel>> pending() {
        return new ResponseEntity<>(this.nodeService.getTransactions(false),
                HttpStatus.OK);
    }

    @GetMapping("/transactions/confirmed")
    public ResponseEntity<List<TransactionViewModel>> confirmed() {
        return new ResponseEntity<>(this.nodeService.getTransactions(true),
                HttpStatus.OK);
    }

    @GetMapping("/transactions/{hash}")
    public ResponseEntity<TransactionViewModel> transactionByHash(
            @PathVariable String hash) throws Exception {
        return new ResponseEntity<>(this.nodeService.getTransactionByHash(hash),
                HttpStatus.OK);
    }

    @GetMapping("/balances")
    public ResponseEntity<List<BalanceViewModel>> balances() {
        return new ResponseEntity<>(this.nodeService.getBalances(), HttpStatus.OK);
    }

    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<List<TransactionViewModel>> transactionsByAddress(
            @PathVariable String address) throws Exception {
        return new ResponseEntity<>(this.nodeService.getTransactionsByAddress(address),
                HttpStatus.OK);
    }

    @GetMapping("/address/{address}/balance")
    public ResponseEntity<BalanceByAddressViewModel> balanceByAddress(
            @PathVariable String address) throws Exception {
        return new ResponseEntity<>(this.nodeService.getBalanceByAddress(address), HttpStatus.OK);
    }

    @PostMapping("/transactions/send")
    public ResponseEntity<TransactionCreatedViewModel> sendTransaction(
            @RequestBody TransactionBindingModel transaction) throws Exception {
        return new ResponseEntity<>(this.nodeService.addTransaction(transaction),
                HttpStatus.CREATED);
    }

    @GetMapping("/mining/get-mining-job/{miner-address}")
    public ResponseEntity<MiningJobViewModel> miningJob(
            @PathVariable String minerAddress) throws Exception {
        return new ResponseEntity<>(this.nodeService.getMiningJob(minerAddress), HttpStatus.OK);
    }

    @PostMapping("/mining/submit-mined-block")
    public ResponseEntity<MiningJobViewModel> submitMinedBlock(
            @RequestBody MiningJobBindingModel minedBlock) throws Exception {
        return new ResponseEntity<>(this.nodeService.submitMinedBlock(minedBlock), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorViewModel> exceptionHandler(Exception e) {
        ErrorViewModel model = new ErrorViewModel();
        model.setMessage(e.getMessage());
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
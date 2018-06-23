package com.faucet.controllers;

import com.faucet.entities.Transaction;
import com.faucet.exceptions.InvalidPrivateKeyException;
import com.faucet.exceptions.TransactionRequestException;
import com.faucet.models.bindingModels.TransactionBindingModel;
import com.faucet.models.viewModels.TransactionViewModel;
import com.faucet.services.interfaces.TransactionService;
import com.faucet.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class PageController {

    private final TransactionService transactionService;

    @Autowired
    public PageController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Faucet");
        return "home";
    }

    @PostMapping("/new-transaction")
    @ResponseBody
    public ResponseEntity<TransactionViewModel> sendTransaction(@Valid TransactionBindingModel bindingModel) throws InvalidPrivateKeyException, TransactionRequestException {
        String privKey = bindingModel.getPrivateKey();
        if (privKey == null || privKey.length() != Config.KEY_LENGTH) {
            throw new InvalidPrivateKeyException();
        }

        TransactionViewModel transaction = this.transactionService.sendTransaction(privKey);

        // Redirect to the blockchain
//        URI yottaURI = new URI("http://yotta.com");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(yottaURI);
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}

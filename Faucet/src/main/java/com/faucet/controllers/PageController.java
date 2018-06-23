package com.faucet.controllers;

import com.faucet.exceptions.InvalidPrivateKeyException;
import com.faucet.models.bindingModels.TransactionBindingModel;
import com.faucet.services.interfaces.TransactionService;
import com.faucet.utils.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PageController {

    private final TransactionService transactionService;

    @Autowired
    public PageController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public ModelAndView getHomePage() {
        ModelAndView homePage = new ModelAndView("home");
        homePage.addObject("title", "Faucet");
        return homePage;
    }

    @PostMapping("/new-transaction")
    public void sendTransaction(TransactionBindingModel bindingModel) throws InvalidPrivateKeyException {
        String privKey = bindingModel.getPrivateKey();
        if (privKey == null || privKey.length() < Config.MIN_KEY_LENGTH) {
            throw new InvalidPrivateKeyException();
        }

        this.transactionService.sendTransaction(privKey);
    }
}

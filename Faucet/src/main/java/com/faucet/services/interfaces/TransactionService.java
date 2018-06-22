package com.faucet.services.interfaces;

import com.faucet.models.viewModels.TransactionViewModel;

import java.util.List;

public interface TransactionService {

    public void sendTransaction(String recipientPrivateKey);
}

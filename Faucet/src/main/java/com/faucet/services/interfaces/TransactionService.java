package com.faucet.services.interfaces;

import com.faucet.exceptions.TransactionRequestException;
import com.faucet.models.viewModels.TransactionViewModel;

public interface TransactionService {

    public TransactionViewModel sendTransaction(String recipientPrivateKey) throws TransactionRequestException;
}

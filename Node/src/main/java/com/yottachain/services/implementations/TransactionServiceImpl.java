package com.yottachain.services.implementations;

import com.yottachain.entities.Transaction;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public Transaction create(TransactionBindingModel model) {
        Transaction transaction = new Transaction();
        transaction.setFrom(model.getFrom());
        transaction.setTo(model.getTo());
        transaction.setAmount(model.getAmount());
        transaction.setNonce(model.getNonce());
        transaction.setSenderPublicKey(model.getSenderPublicKey());
        transaction.setSenderSignature(model.getSenderSignature());
        return transaction;
    }

    @Override
    public Transaction sign(Transaction transaction, String privateKey) {
        // TODO - Get transaction data, hash, sign, create signature and return transaction
        return null;
    }

    @Override
    public boolean validate(Transaction transaction) {
        // TODO - Get transaction data, hash, validate signature
        return false;
    }
}
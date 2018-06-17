package com.yottachain.services.implementations;

import com.yottachain.entities.Transaction;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public Transaction create(TransactionViewModel transaction) {
        // TODO - Create new object from type Transaction and return it
        return null;
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
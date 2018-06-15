package com.yottachain.services.implementations;

import com.yottachain.entities.Transaction;
import com.yottachain.models.viewModels.TransactionViewModel;
import com.yottachain.services.interfaces.TransactionService;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public Transaction Create(TransactionViewModel transaction) {
        return null;
    }

    @Override
    public Transaction Sign(Transaction transaction, String privateKey) {
        return null;
    }

    @Override
    public boolean Validate(Transaction transaction) {
        return false;
    }
}
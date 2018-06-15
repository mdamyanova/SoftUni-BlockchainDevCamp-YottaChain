package com.yottachain.services.interfaces;

import com.yottachain.entities.Transaction;
import com.yottachain.models.viewModels.TransactionViewModel;

public interface TransactionService {

    Transaction Create(TransactionViewModel transaction);
    Transaction Sign(Transaction transaction, String privateKey);
    boolean Validate(Transaction transaction);
}
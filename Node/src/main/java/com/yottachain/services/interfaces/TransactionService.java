package com.yottachain.services.interfaces;

import com.yottachain.entities.Transaction;
import com.yottachain.models.bindingModels.TransactionBindingModel;

public interface TransactionService {

    Transaction create(TransactionBindingModel model);
    Transaction sign(Transaction transaction, String privateKey);
    boolean validate(Transaction transaction) throws Exception;
}
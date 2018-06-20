package com.yottachain.services.implementations;

import com.yottachain.entities.Address;
import com.yottachain.entities.Transaction;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.services.interfaces.TransactionService;
import com.yottachain.utils.CryptoUtils;
import com.yottachain.utils.ValidationUtils;

public class TransactionServiceImpl implements TransactionService {

    @Override
    public Transaction create(TransactionBindingModel model) {
        Address from = new Address();
        from.setAddressId(model.getFrom());
        Address to = new Address();
        to.setAddressId(model.getTo());

        Transaction transaction = new Transaction();
        transaction.setFrom(from);
        transaction.setTo(to);
        transaction.setAmount(model.getAmount());
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
    public String validate(Transaction transaction) {
        String fromAddress = transaction.getFrom().getAddressId();
        if (!ValidationUtils.isValidAddress(fromAddress)) {
            return "Invalid sender address: " + fromAddress;
        }
        String toAddress = transaction.getTo().getAddressId();
        if (!ValidationUtils.isValidAddress(toAddress)) {
            return "Invalid recipient address: " + toAddress;
        }
        String publicKey = transaction.getSenderPublicKey();
        if (!ValidationUtils.isValidPublicKey(publicKey)) {
            return "Invalid public key: " + publicKey;
        }
        String senderAddress = CryptoUtils.publicKeyToAddress(publicKey);
        if (!senderAddress.equals(fromAddress)) {
            return "The public key should match the sender address";
        }

        return "Valid transaction";
    }
}
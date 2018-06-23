package com.faucet.repositories;

import com.faucet.entities.Transaction;
import com.faucet.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class TransactionDao {

    private static Map<String, Transaction> transactions = new HashMap<>();

    public static String getFaucetPrivateKey() {
        return Config.FAUCET_PRIVATE_KEY;
    }

    public static String getFaucetAddress() {
        return Config.FAUCET_ADDRESS;
    }

    public static void saveTransaction(Transaction transaction) {
        transactions.put(transaction.getAddressTo(), transaction);
    }

    public static Transaction findTransactionByAddressTo(String recipientAddress) {
        return transactions.get(recipientAddress);
    }
}

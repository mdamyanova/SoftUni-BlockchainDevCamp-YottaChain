package com.yottachain.utils;

import java.util.List;

public class ValidationUtils {

    private static final int minTransactionFee = 5;
    private static final int maxTransactionFee = 10000;

    public static boolean isValidAddress(String address) {
        return address.matches("^[0-9a-f]{40}$");
    }

    public static boolean isValidPublicKey(String publicKey) {
        return publicKey.matches("^[0-9a-f]{65}$");
    }

    public static boolean isValidFee(int fee) {
        return fee >= minTransactionFee && fee <= maxTransactionFee;
    }

    public static boolean isValidDate(String date) {
        return date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{2,6}Z$");
    }

    public static boolean isValidSignature(List<String> signature) {
        return signature.size() == 2 &&
                signature.get(0).matches("^[0-9a-f]{1,65}$") &&
                signature.get(1).matches("^[0-9a-f]{1,65}$");
    }

    public static boolean isValidDifficulty(String blockHash, int difficulty) {
        for (int i = 0; i < difficulty; i++) {
            if (blockHash.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}
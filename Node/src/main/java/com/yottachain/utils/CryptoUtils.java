package com.yottachain.utils;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.ec.ECPair;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import java.util.List;

public class CryptoUtils {

    public static String publicKeyToAddress(String publicKey) {
        BigInteger publicKeyInt = new BigInteger(publicKey, 16);
        byte[] bytes = publicKeyInt.toByteArray();
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);
        return Hex.toHexString(bytes);
    }

    public static boolean verifySignature(String data, String publicKey, List<String> signature) {
       return true; // TODO
    }
}
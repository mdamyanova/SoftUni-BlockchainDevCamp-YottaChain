package com.yottachain.utils;

import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;

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
}
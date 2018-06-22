package com.faucet.utils;

import com.faucet.entities.Transaction;
import com.faucet.repositories.TransactionDao;
import com.google.gson.Gson;
import org.bouncycastle.asn1.sec.SECNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.DSAKCalculator;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.HMacDSAKCalculator;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class CryptoUtil {

    static final X9ECParameters curve = SECNamedCurves.getByName ("secp256k1");
    private static final SecureRandom secureRandom = new SecureRandom ();
    private static final ECDomainParameters domain = new ECDomainParameters(curve.getCurve(), curve.getG(), curve.getN (), curve.getH());

    public static AsymmetricCipherKeyPair generateRandomKeys() {
        ECKeyPairGenerator gen = new ECKeyPairGenerator();
        KeyGenerationParameters keyGenParams = new ECKeyGenerationParameters(domain, secureRandom);
        gen.init(keyGenParams);

        return gen.generateKeyPair();
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    public static String encodeECPointHexCompressed(ECPoint point) {
        BigInteger x = point.getXCoord().toBigInteger();
        BigInteger y = point.getYCoord().toBigInteger();

        return x.toString(16) + (y.testBit(0) ? 1 : 0);
    }

    public static ECPoint getPublicKeyFromPrivateKey(BigInteger privKey) {
        ECPoint publicKey = curve.getG().multiply(privKey).normalize();
        return publicKey;
    }

    public static ECPublicKeyParameters toPublicKey(String privateKeyHash) {
        BigInteger d = new BigInteger(privateKeyHash, 16);
        ECPoint q = domain.getG().multiply(d);

        ECPublicKeyParameters publicKeyParams = new ECPublicKeyParameters(q, domain);

        return publicKeyParams;
    }

    public static String calcRipeMD160(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];
        digest.doFinal(result, 0);

        return bytesToHex(result);
    }

    public static byte[] calcSHA256(String text) throws UnsupportedEncodingException {
        byte[] bytes = text.getBytes("UTF-8");
        SHA256Digest digest = new SHA256Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] result = new byte[digest.getDigestSize()];

        return result;
    }

    public static String getRecipientAddressFromPrivateKey(String privateKeyHex) throws UnsupportedEncodingException {
        BigInteger privateKey = new BigInteger(privateKeyHex, 16);

        ECPoint publicKey = getPublicKeyFromPrivateKey(privateKey);
        String recipientPublicKeyCompressed = encodeECPointHexCompressed(publicKey);

        String recipientAddress = calcRipeMD160(recipientPublicKeyCompressed);

        return recipientAddress;
    }

    public static Transaction signAndVerifyTransaction(String recipientAddress, int value, int fee, String iso8601datetime, String senderPrivateKeyHex) throws UnsupportedEncodingException {
        BigInteger privateKey = new BigInteger(TransactionDao.getFaucetPrivateKey(), 16);

        ECPoint publicKey = getPublicKeyFromPrivateKey(privateKey);
        String senderPublicKeyCompressed = encodeECPointHexCompressed(publicKey);

        String senderAddress = calcRipeMD160(senderPublicKeyCompressed);

        Transaction transaction = new Transaction(TransactionDao.getFaucetAddress(), recipientAddress, senderPublicKeyCompressed, value, fee, iso8601datetime, null);
        Gson gson = new Gson();
        String tranJSON = gson.toJson(transaction);
        System.out.println("Transaction JSON: " + tranJSON);

        byte[] tranHash = calcSHA256(tranJSON);

        BigInteger[] tranSignatureBig = signData(privateKey, tranHash);

        String[] senderSignature = new String[] {
                tranSignatureBig[0].toString(16),
                tranSignatureBig[1].toString(16),
        };
        Transaction tranSigned = new Transaction(senderAddress, recipientAddress, senderPublicKeyCompressed, value, fee, iso8601datetime, senderSignature);

        // Verify Transaction
        boolean isVerified = verifySignature(senderPrivateKeyHex, tranSignatureBig, tranHash);

        // If is not verified do nothing
        if (!isVerified) {
            return null;
        }

        return tranSigned;
    }

    public static boolean verifySignature(String senderPrivateKeyHex, BigInteger[] signature, byte[] msg) {
        DSAKCalculator kCalculator = new HMacDSAKCalculator(new SHA256Digest());
        ECDSASigner signer = new ECDSASigner(kCalculator);
        ECPublicKeyParameters pubKeyParams = toPublicKey(senderPrivateKeyHex);
        signer.init(false, pubKeyParams);

        return signer.verifySignature(msg, signature[0], signature[1]);
    }

    public static BigInteger[] signData(BigInteger privateKey, byte[] data) {
        ECDomainParameters ecSpec = new ECDomainParameters(curve.getCurve(), curve.getG(), curve.getN(), curve.getH());
        ECPrivateKeyParameters keyParams = new ECPrivateKeyParameters(privateKey, ecSpec);
        DSAKCalculator kCalculator = new HMacDSAKCalculator(new SHA256Digest());
        ECDSASigner signer = new ECDSASigner(kCalculator);
        signer.init(true, keyParams);
        BigInteger[] signature = signer.generateSignature(data);

        return signature;
    }
}

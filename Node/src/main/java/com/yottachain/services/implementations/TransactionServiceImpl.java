package com.yottachain.services.implementations;

import com.yottachain.entities.Address;
import com.yottachain.entities.Transaction;
import com.yottachain.models.bindingModels.TransactionBindingModel;
import com.yottachain.services.interfaces.TransactionService;
import com.yottachain.utils.CryptoUtils;
import com.yottachain.utils.ValidationUtils;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;

import java.math.BigInteger;

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
        BigInteger privateKeyInt = new BigInteger(privateKey, 16);
        BigInteger publicKey = Sign.publicKeyFromPrivate(privateKeyInt);
        ECKeyPair keyPair = new ECKeyPair(privateKeyInt, publicKey);
        byte[] messageHash = Hash.sha3(transaction.getTransactionData().getBytes());
        Sign.SignatureData signature = Sign.signMessage(messageHash, keyPair, false);
        transaction.setTransactionHash(Hex.toHexString(messageHash) +
                Hex.toHexString(signature.getR()) + Hex.toHexString(signature.getS()));
        return transaction;
    }

    @Override
    public boolean validate(Transaction transaction) throws Exception {
        // TODO validate data
        String fromAddress = transaction.getFrom().getAddressId();
        if (!ValidationUtils.isValidAddress(fromAddress)) {
            throw new Exception("Invalid sender address: " + fromAddress);
        }
        String toAddress = transaction.getTo().getAddressId();
        if (!ValidationUtils.isValidAddress(toAddress)) {
            throw new Exception("Invalid recipient address: " + toAddress);
        }
        String publicKey = transaction.getSenderPublicKey();
        if (!ValidationUtils.isValidPublicKey(publicKey)) {
            throw new Exception("Invalid public key: " + publicKey);
        }
        String senderAddress = CryptoUtils.publicKeyToAddress(publicKey);
        if (!senderAddress.equals(fromAddress)) {
            throw new Exception("The public key should match the sender address: " + senderAddress);
        }
        if (!CryptoUtils.verifySignature(transaction.getTransactionData(), publicKey,
                transaction.getSenderSignature())) {
            throw new Exception("Invalid signature");
        }
        return true;
    }

    private static String encodeECPointHexCompressed(ECPoint point) {
        BigInteger x = point.getXCoord().toBigInteger();
        BigInteger y = point.getYCoord().toBigInteger();
        return x.toString(16) + (y.testBit(0) ? 1 : 0);
    }
}
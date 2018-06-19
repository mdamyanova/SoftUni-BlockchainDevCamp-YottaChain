package com.yottachain.models.viewModels;

import com.yottachain.entities.Address;

import java.util.List;

public class TransactionViewModel {

    private Address from;
    private Address to;
    private long amount;
    private int fee;
    private long createdOn;
    private String transactionData;
    private String senderPublicKey;
    private List<String> senderSignature;
    private String transactionDataHash;

    public Address getFrom() {
        return from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public List<String> getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(List<String> senderSignature) {
        this.senderSignature = senderSignature;
    }

    public String getTransactionDataHash() {
        return transactionDataHash;
    }

    public void setTransactionDataHash(String transactionDataHash) {
        this.transactionDataHash = transactionDataHash;
    }
}
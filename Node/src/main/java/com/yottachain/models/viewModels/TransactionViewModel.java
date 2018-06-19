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
        return this.from;
    }

    public void setFrom(Address from) {
        this.from = from;
    }

    public Address getTo() {
        return this.to;
    }

    public void setTo(Address to) {
        this.to = to;
    }

    public long getAmount() {
        return this.amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getFee() {
        return this.fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public long getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getTransactionData() {
        return this.transactionData;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    public String getSenderPublicKey() {
        return this.senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public List<String> getSenderSignature() {
        return this.senderSignature;
    }

    public void setSenderSignature(List<String> senderSignature) {
        this.senderSignature = senderSignature;
    }

    public String getTransactionDataHash() {
        return this.transactionDataHash;
    }

    public void setTransactionDataHash(String transactionDataHash) {
        this.transactionDataHash = transactionDataHash;
    }
}
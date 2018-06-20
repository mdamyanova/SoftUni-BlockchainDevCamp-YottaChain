package com.yottachain.entities;

import java.util.List;

public class Transaction {

    private Address from;
    private Address to;
    private long amount;
    private int fee;
    private long createdOn;
    private String transactionData;
    private String senderPublicKey;
    private List<String> senderSignature;
    private String transactionHash;
    private int minedInBlockIndex;
    private boolean isConfirmed;

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

    public String getTransactionHash() {
        return this.transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public boolean isConfirmed() {
        return this.isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public int getMinedInBlockIndex() {
        return this.minedInBlockIndex;
    }

    public void setMinedInBlockIndex(int minedInBlockIndex) {
        this.minedInBlockIndex = minedInBlockIndex;
    }
}
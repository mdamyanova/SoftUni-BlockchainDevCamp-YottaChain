package com.yottachain.models.bindingModels;

import java.util.List;

public class TransactionBindingModel {

    private String from;
    private String to;
    private long amount;
    private int fee;
    private String transactionData;
    private String senderPublicKey;
    private List<String> senderSignature;
    private long nonce;

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
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

    public String getTransactionData() { return this.transactionData; }

    public void setTransactionData(String transactionData) { this.transactionData = transactionData; }

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

    public long getNonce() {
        return this.nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }
}
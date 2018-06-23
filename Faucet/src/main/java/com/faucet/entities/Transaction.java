package com.faucet.entities;

import java.util.Date;

public class Transaction {

    private String addressFrom;
    private String addressTo;
    private String senderPublicKey;
    private int value;
    private int fee;
    private String dateCreated;
    private Date lastRequest;
    private String[] senderSignature;


    public Transaction(String addressFrom, String addressTo, String senderPublicKey, int value, int fee, String dateCreated, String[] senderSignature) {
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.senderPublicKey = senderPublicKey;
        this.value = value;
        this.fee = fee;
        this.dateCreated = dateCreated;
        this.senderSignature = senderSignature;
        this.lastRequest = new Date();
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }

    public String[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String[] senderSignature) {
        this.senderSignature = senderSignature;
    }
}

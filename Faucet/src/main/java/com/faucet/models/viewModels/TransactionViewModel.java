package com.faucet.models.viewModels;

import java.util.Date;

public class TransactionViewModel {
    private String addressFrom;
    private String addressTo;
    private String senderPublicKey;
    private int value;
    private int fee;
    private String dateCreated;
    private String[] senderSignature;

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

    public String[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String[] senderSignature) {
        this.senderSignature = senderSignature;
    }
}

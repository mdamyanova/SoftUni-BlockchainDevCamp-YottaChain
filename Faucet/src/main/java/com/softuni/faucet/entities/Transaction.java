package com.softuni.faucet.entities;

import java.util.Date;

public class Transaction {

    private int id;
    private String from;
    private String to;
    private int value;
    private String senderPublicKey;
    private String nodeUrl;
    private Date lastRequest;

    public Transaction() {
        this.lastRequest = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public Date getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }
}

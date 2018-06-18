package com.yottachain.entities;

import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.util.*;

public class Block {
    private int index;
    private List<Transaction> transactions;
    private long difficulty;
    private String previousBlockHash;
    private Address minedBy;
    private String blockDataHash;
    private long nonce;
    private long createdOn;
    private String blockHash;

    public void generateHashes() {

    }

    public int getIndex() {
        return index;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public Address getMinedBy() {
        return minedBy;
    }

    public void setMinedBy(Address minedBy) {
        this.minedBy = minedBy;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
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
    private long createdOn;
    private String blockHash;

    public void generateHashes() {

    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public long getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public String getPreviousBlockHash() {
        return this.previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public Address getMinedBy() {
        return this.minedBy;
    }

    public void setMinedBy(Address minedBy) {
        this.minedBy = minedBy;
    }

    public String getBlockDataHash() {
        return this.blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }

    public long getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getBlockHash() {
        return this.blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
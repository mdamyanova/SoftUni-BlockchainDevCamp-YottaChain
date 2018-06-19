package com.yottachain.models.viewModels;

import com.yottachain.entities.Address;
import com.yottachain.entities.Transaction;

import java.util.Date;
import java.util.List;

public class BlockViewModel {

    private int index;
    private List<Transaction> transactions;
    private long difficulty;
    private String previousBlockHash;
    private Address minedBy;
    private String blockDataHash;
    private long nonce;
    private Date createdOn;
    private String blockHash;

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

    public long getNonce() {
        return this.nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getBlockHash() {
        return this.blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }
}
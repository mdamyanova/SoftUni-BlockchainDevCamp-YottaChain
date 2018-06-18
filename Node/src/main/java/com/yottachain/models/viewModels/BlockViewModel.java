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

    public BlockViewModel(int index, List<Transaction> transactions, long difficulty,
                          String previousBlockHash, Address minedBy, String blockDataHash,
                          long nonce, Date createdOn, String blockHash) {
        this.index = index;
        this.transactions = transactions;
        this.difficulty = difficulty;
        this.previousBlockHash = previousBlockHash;
        this.minedBy = minedBy;
        this.blockDataHash = blockDataHash;
        this.nonce = nonce;
        this.createdOn = createdOn;
        this.blockHash = blockHash;
    }

    public int getIndex() {
        return index;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public Address getMinedBy() {
        return minedBy;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public long getNonce() {
        return nonce;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getBlockHash() {
        return blockHash;
    }
}
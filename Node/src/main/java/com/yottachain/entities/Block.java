package com.yottachain.entities;

import java.util.Date;
import java.util.List;

public class Block {
    private int index;
    private List<Transaction> transactions;
    private long difficulty;
    private String previousBlockHash;
    private Address minedBy;
    private String blockDataHash;
    private long nonce;
    private Date createdOn;
    private String blockHash;
    private static Block genesis;

    public Block() {
    }


    public void GenerateHashes() {

    }

    // TODO
    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                '}';
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
    // TODO - Getters and setters
}
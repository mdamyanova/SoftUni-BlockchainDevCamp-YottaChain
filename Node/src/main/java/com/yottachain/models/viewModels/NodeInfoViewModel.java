package com.yottachain.models.viewModels;

import com.yottachain.entities.Block;
import com.yottachain.p2p.Peer;

import java.util.List;

public class NodeInfoViewModel {

    private String nodeId;
    private String chainId;
    private String nodeUrl;
    private long peers;
    private int currentDifficulty;
    private int port;
    private long blocksCount;
    private long confirmedTransactions;
    private long pendingTransactions;

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getChainId() {
        return this.chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getNodeUrl() {
        return this.nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public long getPeers() {
        return this.peers;
    }

    public void setPeers(long peers) {
        this.peers = peers;
    }

    public int getCurrentDifficulty() {
        return this.currentDifficulty;
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getBlocksCount() {
        return this.blocksCount;
    }

    public void setBlocksCount(long blocksCount) {
        this.blocksCount = blocksCount;
    }

    public long getConfirmedTransactions() {
        return this.confirmedTransactions;
    }

    public void setConfirmedTransactions(long confirmedTransactions) {
        this.confirmedTransactions = confirmedTransactions;
    }

    public long getPendingTransactions() {
        return this.pendingTransactions;
    }

    public void setPendingTransactions(long pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }
}
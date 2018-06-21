package com.yottachain.entities;

import com.yottachain.p2p.Peer;

import java.util.List;

public class Node {

    private int nodeId;
    private String host; // IP address
    private int port; // TCP port
    private String url; // Base URL for the REST endpoints
    private List<Peer> peers; // Peers connected to this node
    private List<Block> chain; // The Blockchain
    private int chainId; // Hash of the genesis block

    public int getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Peer> getPeers() {
        return this.peers;
    }

    public void setPeers(List<Peer> peers) {
        this.peers = peers;
    }

    public List<Block> getChain() {
        return this.chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    public int getChainId() {
        return this.chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }
}
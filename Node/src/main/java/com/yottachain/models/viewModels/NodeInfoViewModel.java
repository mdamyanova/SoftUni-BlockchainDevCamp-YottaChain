package com.yottachain.models.viewModels;

import com.yottachain.entities.Block;
import com.yottachain.p2p.Peer;

import java.util.List;

public class NodeInfoViewModel {


    private int nodeId;
    private String host;
    private int port;
    private String url;
    private List<Peer> peers;
    private List<Block> chain;
    private int chainId;

    public NodeInfoViewModel(int nodeId, String host, int port, String url,
                                List<Peer> peers, List<Block> chain, int chainId) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
        this.url = url;
        this.peers = peers;
        this.chain = chain;
        this.chainId = chainId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public List<Block> getChain() {
        return chain;
    }

    public int getChainId() {
        return chainId;
    }
}
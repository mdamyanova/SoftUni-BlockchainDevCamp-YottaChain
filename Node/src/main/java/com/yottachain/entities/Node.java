package com.yottachain.entities;

import com.yottachain.p2p.Peer;

import java.util.List;

public class Node {

    private int nodeId;
    private String host;
    private int port;
    private String url;
    private List<Peer> peers;
    private List<Block> chain;
    private int chainId;
}
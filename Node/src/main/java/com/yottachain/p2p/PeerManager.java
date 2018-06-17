package com.yottachain.p2p;

import java.util.ArrayList;
import java.util.List;

public class PeerManager {

    private List<Peer> peers = new ArrayList<>();
    public List<Peer> getAllPeers() {
        return peers;
    }
}
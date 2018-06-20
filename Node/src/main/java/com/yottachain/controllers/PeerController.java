package com.yottachain.controllers;

import com.yottachain.p2p.Peer;
import com.yottachain.p2p.PeerManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/peers")
public class PeerController {

    // TODO - peers
    // TODO - peers/connect
    private static PeerManager peerManager = new PeerManager();

    @RequestMapping("/all")
    public List<Peer> getAllPeers() {
        return peerManager.getAllPeers();
    }
}
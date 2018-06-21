package com.yottachain.p2p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.yottachain.p2p.Message.MESSAGE_TYPE.*;

public class PeerServerThread extends Thread {

    private Socket client;
    private final Peer peer;

    PeerServerThread(final Peer peer, final Socket client) {
        super(peer.getName() + System.currentTimeMillis());
        this.peer = peer;
        this.client = client;
    }

    @Override
    public void run() {
        try (
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            final ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            Message message = new Message.MessageBuilder().withSender(peer.getPort()).withType(READY).build();
            out.writeObject(message);
            Object fromClient;

            while ((fromClient = in.readObject()) != null) {
                if (fromClient instanceof Message) {
                    final Message msg = (Message) fromClient;
                    System.out.println(String.format("%d received: %s", peer.getPort(), fromClient.toString()));
                    if (INFO_NEW_BLOCK == msg.type) {
                        if (msg.blocks.isEmpty() || msg.blocks.size() > 1) {
                            System.err.println("Invalid block received: " + msg.blocks);
                        }
                        synchronized (peer) {
                            //  peer.addBlock(msg.blocks.get(0));
                        }
                        break;
                    } else if (REQ_ALL_BLOCKS == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withSender(peer.getPort())
                                .withType(RSP_ALL_BLOCKS)
                                .withBlocks(peer.getBlockchain())
                                .build());
                        break;
                    }
                }
            }
            client.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
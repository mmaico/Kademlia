package kademlia.network.server;

import kademlia.exceptions.KadServerDownException;
import kademlia.message.Message;
import kademlia.message.Receiver;
import kademlia.node.Node;

import java.io.IOException;

public interface KadServer {

    int sendMessage(Node to, Message msg, Receiver recv) throws IOException, KadServerDownException;
    void reply(Node to, Message msg, int comm) throws IOException;
    void shutdown();
    boolean isRunning();
}

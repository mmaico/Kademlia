package kademliadht.network.server;

import kademliadht.exceptions.KadServerDownException;
import kademliadht.message.Message;
import kademliadht.message.Receiver;
import kademliadht.node.Node;

import java.io.IOException;

public interface KadServer {

    int sendMessage(Node to, Message msg, Receiver recv) throws IOException, KadServerDownException;
    void reply(Node to, Message msg, int comm) throws IOException;
    void shutdown();
    boolean isRunning();
}

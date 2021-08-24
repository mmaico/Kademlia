package io.github.chronosx88.kademliadht.network.server;

import io.github.chronosx88.kademliadht.exceptions.KadServerDownException;
import io.github.chronosx88.kademliadht.message.Message;
import io.github.chronosx88.kademliadht.message.Receiver;
import io.github.chronosx88.kademliadht.node.Node;

import java.io.IOException;

public interface KadServer {

    int sendMessage(Node to, Message msg, Receiver recv) throws IOException, KadServerDownException;
    void reply(Node to, Message msg, int comm) throws IOException;
    void shutdown();
    boolean isRunning();
}

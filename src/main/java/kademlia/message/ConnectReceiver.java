package kademlia.message;

import kademlia.network.server.NativeKadServer;
import kademlia.KademliaNode;

import java.io.IOException;

/**
 * Receives a ConnectMessage and sends an AcknowledgeMessage as reply.
 *
 * @author Joshua Kissoon
 * @created 20140219
 */
public class ConnectReceiver implements Receiver {

    private final NativeKadServer server;
    private final KademliaNode localNode;

    public ConnectReceiver(NativeKadServer server, KademliaNode local) {
        this.server = server;
        this.localNode = local;
    }

    /**
     * Handle receiving a ConnectMessage
     *
     * @param comm
     * @throws IOException
     */
    @Override
    public void receive(Message incoming, int comm) throws IOException {
        ConnectMessage mess = (ConnectMessage) incoming;

        /* Update the local space by inserting the origin node. */
        this.localNode.getRoutingTable().insert(mess.getOrigin());

        /* Respond to the connect request */
        AcknowledgeMessage msg = new AcknowledgeMessage(this.localNode.getNode());

        /* Reply to the connect message with an Acknowledgement */
        this.server.reply(mess.getOrigin(), msg, comm);
    }

    /**
     * We don't need to do anything here
     *
     * @param comm
     * @throws IOException
     */
    @Override
    public void timeout(int comm) throws IOException {
    }
}

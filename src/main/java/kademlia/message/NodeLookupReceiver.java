package kademlia.message;

import kademlia.KadConfiguration;
import kademlia.network.server.NativeKadServer;
import kademlia.KademliaNode;
import kademlia.node.Node;

import java.io.IOException;
import java.util.List;

/**
 * Receives a NodeLookupMessage and sends a NodeReplyMessage as reply with the K-Closest nodes to the ID sent.
 *
 * @author Joshua Kissoon
 * @created 20140219
 */
public class NodeLookupReceiver implements Receiver {

    private final NativeKadServer server;
    private final KademliaNode localNode;
    private final KadConfiguration config;

    public NodeLookupReceiver(NativeKadServer server, KademliaNode local, KadConfiguration config) {
        this.server = server;
        this.localNode = local;
        this.config = config;
    }

    /**
     * Handle receiving a NodeLookupMessage
     * Find the set of K nodes closest to the lookup ID and return them
     *
     * @param comm
     * @throws IOException
     */
    @Override
    public void receive(Message incoming, int comm) throws IOException {
        NodeLookupMessage msg = (NodeLookupMessage) incoming;

        Node origin = msg.getOrigin();

        /* Update the local space by inserting the origin node. */
        this.localNode.getRoutingTable().insert(origin);

        /* Find nodes closest to the LookupId */
        List<Node> nodes = this.localNode.getRoutingTable().findClosest(msg.getLookupId(), this.config.k());

        /* Respond to the NodeLookupMessage */
        Message reply = new NodeReplyMessage(this.localNode.getNode(), nodes);

        if (this.server.isRunning()) {
            /* Let the Server send the reply */
            this.server.reply(origin, reply, comm);
        }
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

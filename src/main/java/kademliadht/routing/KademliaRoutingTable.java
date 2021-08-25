package kademliadht.routing;

import kademliadht.KadConfiguration;
import kademliadht.node.KademliaId;
import kademliadht.node.Node;

import java.util.List;

/**
 * Specification for Kademlia's Routing Table
 *
 * @author Joshua Kissoon
 * @since 20140501
 */
public interface KademliaRoutingTable {

    /**
     * Initialize the RoutingTable to it's default state
     */
    void initialize();

    /**
     * Sets the configuration file for this routing table
     *
     * @param config
     */
    void setConfiguration(KadConfiguration config);

    /**
     * Adds a contact to the routing table based on how far it is from the LocalNode.
     *
     * @param c The contact to add
     */
    void insert(Contact c);

    /**
     * Adds a node to the routing table based on how far it is from the LocalNode.
     *
     * @param n The node to add
     */
    void insert(Node n);

    /**
     * Compute the bucket ID in which a given node should be placed; the bucketId is computed based on how far the node is away from the Local Node.
     *
     * @param nid The NodeId for which we want to find which bucket it belong to
     * @return Integer The bucket ID in which the given node should be placed.
     */
    int getBucketId(KademliaId nid);

    /**
     * Find the closest set of contacts to a given NodeId
     *
     * @param target           The NodeId to find contacts close to
     * @param numNodesRequired The number of contacts to find
     * @return List A List of contacts closest to target
     */
    List<Node> findClosest(KademliaId target, int numNodesRequired);

    /**
     * @return List A List of all Nodes in this RoutingTable
     */
    List<Node> getAllNodes();

    /**
     * @return List A List of all Nodes in this RoutingTable
     */
    List getAllContacts();

    /**
     * @return Bucket[] The buckets in this Kad Instance
     */
    KademliaBucket[] getBuckets();

    /**
     * Method used by operations to notify the routing table of any contacts that have been unresponsive.
     *
     * @param contacts The set of unresponsive contacts
     */
    void setUnresponsiveContacts(List<Node> contacts);

    /**
     * Method used by operations to notify the routing table of any contacts that have been unresponsive.
     *
     * @param n
     */
    void setUnresponsiveContact(Node n);

}

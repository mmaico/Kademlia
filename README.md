KademliaDHT
========

This is an implementation of the Kademlia (http://en.wikipedia.org/wiki/Kademlia) routing protocol and DHT in Java.

Joshua Kissoon wrote an
article [An Introduction to Kademlia DHT & How It Works](http://gleamly.com/article/introduction-kademlia-dht-how-it-works)
.

Kademlia original Publication: http://link.springer.com/chapter/10.1007/3-540-45748-8_5

Usage
-----
The Implementation is meant to be self contained and very easy to setup and use. There are
several [tests](https://github.com/ChronosX88/KademliaDHT/tree/master/src/main/java/io/github/chronosx88/kademliadht/simulations)
which demonstrates the usage of the protocol and DHT.

**Configuration**

There is a configuration file available in the kademlia.core package which have all settings used throughout the
protocol, all of these settings are described in depth in the Configuration file.

**Creating a Kad Instance**

All of Kademlia's sub-components (DHT, Node, Routing Table, Server, etc) are wrapped within the Kademlia object to
simplify the usage of the protocol. To create an instance, simply call:

```Java
JKademliaNode kad1 = new JKademliaNode("OwnerName1", new KademliaId("6779878AEF92"), 12049);
JKademliaNode kad2 = new JKademliaNode("OwnerName2", new KademliaId(), 12057);  // Random NodeId will be generated
```

Param 1: The Name of the owner of this instance, can be any name. Param 2: A NodeId for this node Param 3: The port on
which this Kademlia instance will run on.

After this initialization phase, the 2 Kad instances will basically be 2 separate networks. Lets connect them so they'll
be in the same network.

**Connecting Nodes**

Test: https://github.com/ChronosX88/KademliaDHT/blob/master/src/main/java/io/github/chronosx88/kademlia/simulations/NodeConnectionTest.java

```Java
kad2.bootstrap(kad1.getNode());   // Bootstrap kad2 by using kad1 as the main network node
```

**Storing Content**

Test: https://github.com/ChronosX88/KademliaDHT/blob/master/src/main/java/io/github/chronosx88/kademlia/simulations/ContentSendingTest.java

```Java
/* Working example at: https://github.com/JoshuaKissoon/Kademlia/blob/master/src/kademlia/tests/ContentSendingTest.java */
DHTContentImpl c = new DHTContentImpl(kad2.getOwnerId(), "Some Data");  // Create a content
kad2.put(c);    // Put the content on the network

```

**Retrieving Content**

Test: https://github.com/ChronosX88/KademliaDHT/blob/master/src/main/java/io/github/chronosx88/kademlia/simulations/ContentSendingTest.java

```Java
/* Create a GetParameter object with the parameters of the content to retrieve */
GetParameter gp = new GetParameter(c.getKey());   // Lets look for content by key
gp.setType(DHTContentImpl.TYPE);                  // We also only want content of this type
gp.setOwnerId(c.getOwnerId());                    // And content from this owner

/* Now we call get specifying the GetParameters and the Number of results we want */
List<KadContent> conte = kad2.get(gp, 1);
```

**Saving and Retrieving a Node State**

Test: https://github.com/ChronosX88/KademliaDHT/blob/master/src/main/java/io/github/chronosx88/kademlia/simulations/SaveStateTest.java

You may want to save the Node state when your application is shut down and Retrieve the Node state on startup to remove
the need of rebuilding the Node State (Routing Table, DHT Content Entries, etc). Lets look at how we do this.

```Java
/** 
 * Shutting down the Kad instance.
 * Calling .shutdown() ill automatically store the node state in the location specified in the Configuration file 
 */
kad1.shutdown();

/**
 * Retrieving the Node state
 * This is done by simply building the Kademlia instance by calling .loadFromFile()
 * and passing in the instance Owner name as a parameter
 */
 JKademliaNode kad1Reloaded = JKademliaNode.loadFromFile("OwnerName1");
```

For more information on using Kademlia, check the tests
at: https://github.com/ChronosX88/KademliaDHT/tree/master/src/main/java/io/github/chronosx88/kademlia/simulations


Usage in a Real Project
-----------------------

* [Distributed Online Social Network Architecture](https://github.com/JoshuaKissoon/DOSNA)
* [Influence](https://github.com/ChronosX88/Influence-android) - decentralized messenger.

##Next steps
1 - Create automated tests for the features below  
   - Create tests for node connections   DONE
   - Create tests for "sending content"  WORKING 
   - Create tests for refresh operation
   - Create tests for content updating
   - Create tests for auto refresh
   - Create tests for "save state"
   - Create tests for routing table
   - Remove the package simulations, after all tests are implemented
2 - Adding LOG4J
3 - Create implementation for the gRPC Kad Server
4 - Adding levelDB for save state (content data and maybe routing table)

####JoshuaKissoon todo   
- Improve multi-threading system (the gRPC implementation solve this problem)
  -- I think server is slow in handling requests because the listen method runs in a single thread. 
  --- Listen() method should create a new thread to handle every incoming request. 
- KadStatistician (i'll make this improvement and add log4j)
  -- Let the statistician keep track of failed get requests!


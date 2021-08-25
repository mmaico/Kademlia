package kademliadht.connection;

import infratest.BDD;
import kademliadht.JKademliaNode;
import kademliadht.node.KademliaId;
import kademliadht.node.Node;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static infratest.BDD.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;


public class NodeConnectionTest {

    @Test
    public void shouldConnectOneNodeToAnother() throws IOException {
        Given("two nodes up");
            JKademliaNode joshuaK = new JKademliaNode("JoshuaK", new KademliaId(), 7574);
            JKademliaNode crystal = new JKademliaNode("Crystal", new KademliaId(), 7572);
        When("connecting JoshuaK  and Crystal");
            joshuaK.bootstrap(crystal.getNode());
        Then("the routing table of JoshuaK should have the Crystal node");
            List<Node> joshuaKNodes = joshuaK.getRoutingTable().getAllNodes();
            assertThat(joshuaKNodes, containsInAnyOrder(
                hasProperty("name", is("JoshuaK")),
                hasProperty("name", is("Crystal"))
            ));

        And("the routing table of Crystal should have the JoshuaK node");
            List<Node> crystalNodes = joshuaK.getRoutingTable().getAllNodes();
            assertThat(crystalNodes, containsInAnyOrder(
                hasProperty("name", is("Crystal")),
                hasProperty("name", is("JoshuaK"))
            ));
    }
}

package kademliadht.connection;

import infratest.BaseTest;
import infratest.JsonHelper;
import kademliadht.JKademliaNode;
import kademliadht.node.KademliaId;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static infratest.BDD.*;
import static infratest.JsonHelper.toJson;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;


public class NodeConnectionTest extends BaseTest {

    @BeforeAll
    public static void setUp() throws IOException {
        scenarioLoader.load("connection/node-connection-scenarios.json");
    }

    @DisplayName("Should connect one node to another")
    @Test
    public void shouldConnectOneNodeToAnother() throws IOException, JSONException {
        Given("two nodes up");
            JKademliaNode joshuaK = new JKademliaNode("JoshuaK", new KademliaId("6kMWPnij63TcguaCvD326i+8Qi4="), 7574);
            JKademliaNode crystal = new JKademliaNode("Crystal", new KademliaId("3qT03tT+j/KfPrG/4O/vqH9Adtc="), 7572);
        When("connecting JoshuaK  and Crystal");
            joshuaK.bootstrap(crystal.getNode());
        Then("the routing table of JoshuaK should have the Crystal node");
            String joshuaKExpected = s("result expected for JoshuaK node");
            assertEquals(joshuaKExpected, toJson(joshuaK.getRoutingTable().getAllNodes()), STRICT);
        And("the routing table of Crystal should have the JoshuaK node");
            String crystalExpected = s("result expected for Crystal node");
            assertEquals(crystalExpected, toJson(joshuaK.getRoutingTable().getAllNodes()), STRICT);
    }
}

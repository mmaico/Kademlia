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
        When("connecting JoshuaK  to Crystal");
            joshuaK.bootstrap(crystal.getNode());
        Then("the routing table of JoshuaK should have the Crystal node");
            assertEquals(s("result expected for JoshuaK node"), toJson(joshuaK.getRoutingTable().getAllNodes()), STRICT);
        And("the routing table of Crystal should have the JoshuaK node");
            assertEquals(s("result expected for Crystal node"), toJson(joshuaK.getRoutingTable().getAllNodes()), STRICT);
    }

    @DisplayName("Creating a new node 3 and connecting it to 1, hoping it'll get onto 2 also")
    @Test
    public void shouldConnect3NodesAndTheThirdNodeMustBeInTheTableOfTheFirst() throws IOException, JSONException {
        Given("two nodes up");
            JKademliaNode firstNode = new JKademliaNode("FirstNode", new KademliaId("2IaTRDsyk0erGmVFsOp1/zMflz8="), 7474);
            JKademliaNode secondNode = new JKademliaNode("SecondNode", new KademliaId("mgYLP+fjGQqaAedehBKFs+0ezkE="), 7472);
        And("connecting Second Node to First Node");
            firstNode.bootstrap(secondNode.getNode());
        When("The third node starting");
            JKademliaNode thirdNode = new JKademliaNode("ThirdNode", new KademliaId("LMYJwfbItVvOsO6UbuHEbD4Mfo0="), 7471);
        And("connecting the Third Node to the second node");
            thirdNode.bootstrap(secondNode.getNode());
        Then("the routing table of the First Node must have the third node");
            assertEquals(s("The first node have 3 node contacts"), toJson(firstNode.getRoutingTable().getAllNodes()), STRICT);
    }

    @DisplayName("Creating 4 nodes and the last node should have all nodes too")
    @Test
    public void shouldConnect4NodesAndTheLastNodeShouldHaveAllNodesToo() throws IOException, JSONException {
        Given("3 nodes up");
            JKademliaNode firstNode = new JKademliaNode("FirstNode", new KademliaId("kveaNUYeiKrPdi+Grt+JTfEVE5Y="), 6474);
            JKademliaNode secondNode = new JKademliaNode("SecondNode", new KademliaId("x70Apb9lQxCSzX8uwQsv51GT800="), 6472);
            JKademliaNode thirdNode = new JKademliaNode("ThirdNode", new KademliaId("FQ5R8HYdhymhHF9qL80zFu5vZoY="), 6471);
            JKademliaNode fourthNode = new JKademliaNode("FourthNode", new KademliaId("UeYXRkWvMgKsL0QuLOIotuVE8vM="), 6470);
        And("connecting First Node to Second Node");
            secondNode.bootstrap(firstNode.getNode());
        And("connecting Second Node to Third Node");
            thirdNode.bootstrap(secondNode.getNode());
        When("connecting Third Node to Fourth Node");
            fourthNode.bootstrap(thirdNode.getNode());
        Then("the routing table of the Fourth Node must have all nodes too");
            assertEquals(s("The fourth node must have all contacts"), toJson(firstNode.getRoutingTable().getAllNodes()), STRICT);
    }
}

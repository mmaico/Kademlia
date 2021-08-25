package infratest;

public class BaseTest {
    protected static final ScenarioLoader scenarioLoader = new ScenarioLoader();

    public String s(String scenario) {
        return scenarioLoader.getScenario(scenario).getJson();
    }

}

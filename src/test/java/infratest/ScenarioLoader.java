package infratest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class ScenarioLoader {

    private List<Scenario> scenarios = new ArrayList<>();
    final Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

    public ScenarioLoader(){}

    public void load(String... files) throws IOException {
        for (String fileName : files) {
            String fileFullName = "/scenarios/" + fileName;
            InputStream stream = ScenarioLoader.class.getResourceAsStream(fileFullName);
            if (stream == null) {
                throw new IllegalArgumentException("Scenario file [" + fileName +"] not found");
            }

            String json = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))
                    .lines().collect(joining("\n"));

            final List<Object> scenariosToInsert = JsonPath.parse(json).read("$.[*]", ArrayList.class);

            Set<Scenario> items = scenariosToInsert.stream()
                    .map(scenarioToInsert -> (LinkedHashMap<String, String>) scenarioToInsert)
                    .map(scenarioToInsert -> {
                        Object jsonScenario = scenarioToInsert.get("json");
                        if (jsonScenario instanceof JSONArray) {
                            return new Scenario(scenarioToInsert.get("scenario"), jsonScenario.toString());
                        } else {
                            return new Scenario(scenarioToInsert.get("scenario"), gson.toJson(scenarioToInsert.get("json"), LinkedHashMap.class));
                        }
                    })
                    .collect(Collectors.toSet());

            scenarios.addAll(items);
        }

    }

    public Scenario getScenario(String scenario) {

        int indexOf = scenarios.indexOf(ExternalEquals.externalEquals(scenario));

        if (indexOf > -1) {
            return scenarios.get(indexOf);
        }

        throw new IllegalArgumentException("Scenario not found [" + scenario +"]" + scenarios);
    }

    public static class Scenario {

        private String json;
        private String scenario;
        private String file;

        public Scenario() {
        }

        public Scenario(String scenario) {
            this.scenario = scenario;
        }

        public Scenario(String scenario, String json) {
            this.scenario = scenario;
            this.json = json;
        }

        public String getScenario() {
            return scenario;
        }

        public void setScenario(String scenario) {
            this.scenario = scenario;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }

        @Override
        public boolean equals(Object o) {
            return EqualsBuilder.reflectionEquals(o, this, "json");
        }

        @Override
        public int hashCode() {
            return scenario != null ? scenario.hashCode() : 0;
        }
    }

    private static class ExternalEquals {

        private final Scenario scenario;

        ExternalEquals(String scenario) {
            this.scenario = new Scenario(scenario);
        }

        static ExternalEquals externalEquals(String scenario) {
            return new ExternalEquals(scenario);
        }

        @Override
        public boolean equals(Object e) {
            if (e == null) {
                return Boolean.FALSE;
            }
            if (!(e instanceof Scenario)) {
                return Boolean.FALSE;
            }
            Scenario scenarioParam = (Scenario) e;

            if (scenarioParam.getScenario().equalsIgnoreCase(this.scenario.getScenario())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;
        }
    }

}

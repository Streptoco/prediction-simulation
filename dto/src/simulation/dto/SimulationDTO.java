package simulation.dto;

import java.util.HashMap;
import java.util.Map;

public class SimulationDTO {
    private Map<String, Integer> populations;
    private Map<String, Object> envProperties;

    public SimulationDTO() {
        this.populations = new HashMap<>();
        this.envProperties = new HashMap<>();
    }

    public void addPopulation(String entityName, int population) {
        this.populations.put(entityName, population);
    }

    public void addEnvVariable(String propertyName, Object value) {
        this.envProperties.put(propertyName, value);
    }

    public Map<String, Integer> getPopulations() {
        return populations;
    }

    public Map<String, Object> getEnvProperties() {
        return envProperties;
    }
}
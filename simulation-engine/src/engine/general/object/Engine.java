package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstanceManager;
import engine.property.api.PropertyInterface;
import engine.xml.XmlReader;
import enginetoui.dto.basic.EntityDTO;
import enginetoui.dto.basic.PropertyDTO;
import enginetoui.dto.basic.RuleDTO;
import enginetoui.dto.basic.WorldDTO;

import java.util.*;

public class Engine {
    private final Map<Integer, World> simulations; //TODO: should be map, key:value pairs. the key would be generated.
    private int serialNumber = 0;
    private final XmlReader reader;

    public Engine() {
        simulations = new HashMap<>();
        reader = new XmlReader();
    }

    public void addSimulation(String filePath) {
        serialNumber++;
        simulations.put(serialNumber, reader.ReadXML(filePath));
    }

    public List<EntityDTO> GetAllEntities() {
        World currentWorld = simulations.get(serialNumber);
        List<EntityDTO> resultList = new ArrayList<>();
        for (EntityDefinition entity : currentWorld.GetEntities()) {
            resultList.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        return resultList;
    }

    public List<RuleDTO> GetAllRules() {
        World currentWorld = simulations.get(serialNumber);
        List<RuleDTO> resultList = new ArrayList<>();
        for (Rule rule : currentWorld.getRules()) {
            resultList.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        return resultList;
    }

    public int GetSimulationTotalTicks() {
        return simulations.get(serialNumber).GetSimulationTotalTicks();
    }

    public long GetSimulationTotalTime() {
        return simulations.get(serialNumber).GetSimulationTotalTime();
    }

    public List<PropertyDTO> GetAllEnvProperties() {
        List<PropertyDTO> resultList = new ArrayList<>();
        for (PropertyInterface envProperty : this.simulations.get(this.serialNumber).getEnvironment().GetAllEnvVariables()) {
            resultList.add(new PropertyDTO(envProperty.getName(), envProperty.getPropertyType(), envProperty.getFrom(), envProperty.getTo(), envProperty.getRandomStatus()));
        }
        return resultList;
    }

    public void SetVariable(String variableName, int value) {
        simulations.get(serialNumber).getEnvironment().updateProperty(variableName, value);
    }

    public void SetVariable(String variableName, double value) {
        simulations.get(serialNumber).getEnvironment().updateProperty(variableName, value);
    }

    public void SetVariable(String variableName, boolean value) {
        simulations.get(serialNumber).getEnvironment().updateProperty(variableName, value);
    }

    public void SetVariable(String variableName, String value) {
        simulations.get(serialNumber).getEnvironment().updateProperty(variableName, value);
    }

    public int RunSimulation() {
        simulations.get(serialNumber).Run();
        return (serialNumber);
    }

    public List<WorldDTO> GetSimulations() {
        List<WorldDTO> resultList = new ArrayList<>();
        for(Map.Entry<Integer, World> entry : simulations.entrySet()) {
            resultList.add(new WorldDTO(entry.getKey(), entry.getValue().getSimulationDate(), entry.getValue().getAllInstancesManager(), entry.getValue().getSimDate()));
        }
        return resultList;
    }

    public EntityInstanceManager GetInstanceManager(String name, int simID) {
        return this.simulations.get(simID).GetInstances(name);
    }
}

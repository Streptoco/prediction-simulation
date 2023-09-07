package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstanceManager;
import engine.general.multiThread.impl.SimulationExecutionManager;
import engine.property.api.PropertyInterface;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import enginetoui.dto.basic.impl.RuleDTO;
import enginetoui.dto.basic.impl.WorldDTO;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Engine {
    private final Map<String, World> worlds;
    private final NewXMLReader reader;
    private String filePath;
    private final SimulationExecutionManager simulationManager;

    public Engine() {
        worlds = new HashMap<>();
        reader = new NewXMLReader();
        simulationManager = new SimulationExecutionManager();
        filePath = "";
    }

    public void loadWorld(String filePath) throws JAXBException {
        this.filePath = filePath;
        World aWholeNewworld = reader.ReadXML(filePath);
        worlds.put(filePath, aWholeNewworld);
        worlds.get(filePath).createPopulationOfEntity(worlds.get(filePath).GetEntities().get(0), 15);
        worlds.get(filePath).createPopulationOfEntity(worlds.get(filePath).GetEntities().get(1), 3);
        worlds.get(filePath).getEnvironment().updateProperty("infection-proximity", 1);
        // NOTE: THIS IS HARD CODED SO THAT I COULD CREATE DTOs
    }

    public void loadWorld() throws JAXBException {
        if(!this.filePath.isEmpty()) {
            //loadWorld(filePath);
            World aWholeNewworld = reader.ReadXML(filePath);
            aWholeNewworld.createPopulationOfEntity(worlds.get(filePath).GetEntities().get(0), 15);
            aWholeNewworld.createPopulationOfEntity(worlds.get(filePath).GetEntities().get(1), 3);
            aWholeNewworld.getEnvironment().updateProperty("infection-proximity", 1);
        }
    }

    public void StartSimulation() {
        simulationManager.CreateSimulation(worlds.get(filePath));
        simulationManager.StartSimulation(simulationManager.getLatestSimulation());
    }

    public List<EntityDTO> GetAllEntities() {
        World currentWorld = worlds.get(filePath);
        List<EntityDTO> resultList = new ArrayList<>();
        for (EntityDefinition entity : currentWorld.GetEntities()) {
            resultList.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        return resultList;
    }

    public List<RuleDTO> GetAllRules() {
        World currentWorld = worlds.get(filePath);
        List<RuleDTO> resultList = new ArrayList<>();
        for (Rule rule : currentWorld.getRules()) {
            resultList.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        return resultList;
    }

    public int GetSimulationTotalTicks(String filePath) {
        return worlds.get(filePath).GetSimulationTotalTicks();
    }

    public long GetSimulationTotalTime() {
        return worlds.get(filePath).GetSimulationTotalTime();
    }

    public List<PropertyDTO> GetAllEnvProperties() {
        List<PropertyDTO> resultList = new ArrayList<>();
        for (PropertyInterface envProperty : this.worlds.get(filePath).getEnvironment().GetAllEnvVariables()) {
            resultList.add(new PropertyDTO(envProperty.getName(), envProperty.getPropertyType(), envProperty.getFrom(), envProperty.getTo(), envProperty.getRandomStatus()));
        }
        return resultList;
    }

    public void SetVariable(String variableName, int value) {
        double from = worlds.get(filePath).getEnvironment().getProperty(variableName).getFrom();
        double to = worlds.get(filePath).getEnvironment().getProperty(variableName).getTo();
        if (value < (int) from || value > (int) to) {
            throw new RuntimeException("The value " + value + " is out of bound\n" +
                    "The value should be between: " + (int) from + " to: " + (int) to);
        }
        worlds.get(filePath).getEnvironment().updateProperty(variableName, value);
    }

    public void SetVariable(String variableName, double value) {
        double from = worlds.get(filePath).getEnvironment().getProperty(variableName).getFrom();
        double to = worlds.get(filePath).getEnvironment().getProperty(variableName).getTo();
        if (value < from || value > to) {
            throw new RuntimeException("The value " + value + " is out of bound\n" +
                    "The value should be between: " + from + " to: " + to);
        }
        worlds.get(filePath).getEnvironment().updateProperty(variableName, value);
    }

    public void SetVariableBool(String variableName, String value) {
        if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
            throw new RuntimeException("The value " + value + " cannot be parsed to boolean\n" +
                    "The value should be \"true\" or \"false\"");
        }
        worlds.get(filePath).getEnvironment().updateProperty(variableName, Boolean.parseBoolean(value));
    }

    public void SetVariable(String variableName, String value) {
        worlds.get(filePath).getEnvironment().updateProperty(variableName, value);
    }

    public WorldDTO getWorldDTO() {
        return simulationManager.getWorldDTO(simulationManager.getLatestSimulation());
    }

    public int RunSimulation(int simulationID) {
        World world = worlds.get(simulationID);
        int numOfThreads = world.getNumOfThreads();
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        executor.execute(world::NewRun);
        return (simulationID);
    }


    public EntityInstanceManager GetInstanceManager(String name, String filePath) {
        return this.worlds.get(filePath).GetInstances(name);
    }
}

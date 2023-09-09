package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstanceManager;
import engine.general.multiThread.api.Status;
import engine.general.multiThread.impl.SimulationExecutionManager;
import engine.property.api.PropertyInterface;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import enginetoui.dto.basic.impl.RuleDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Engine {
    private final NewXMLReader reader;
    private String filePath;
    private final SimulationExecutionManager simulationManager;

    public Engine() {
        reader = new NewXMLReader();
        simulationManager = new SimulationExecutionManager();
        filePath = "";
    }

    public void loadWorld(String filePath) {
        this.filePath = filePath;
    }

    public int setupSimulation() throws JAXBException {
        if (!filePath.isEmpty()) {
            World aWholeNewworld = reader.ReadXML(filePath);
            return simulationManager.CreateSimulation(aWholeNewworld);
        }
        return -1;
    }

    public void setupPopulation(EntityAmountDTO entityAmount, int id) {
        /*
         * TODO:
         *  1. make sure that the size of the population isn't larger than the grid size
         */

        World currentWorld = simulationManager.getWorld(id);
        if (currentWorld != null) {
            currentWorld.createPopulationOfEntity(entityAmount.entityName, entityAmount.amountInPopulation);
        }
    }

    public void setupPopulation(List<EntityAmountDTO> entityAmount, int id) {
        entityAmount.forEach(currentEntity -> setupPopulation(currentEntity, id));
    }

    public void setupEnvProperties(PropertyInitializeDTO envProperty, int id) {
        if (envProperty.value instanceof Integer) {
            SetVariable(envProperty.propertyName, (Integer) envProperty.value, id);
        } else if (envProperty.value instanceof Double) {
            SetVariable(envProperty.propertyName, (Double) envProperty.value, id);
        } else if (envProperty.value instanceof Boolean) {
            SetVariable(envProperty.propertyName, String.valueOf(envProperty.value), id);
        } else {
            SetVariable(envProperty.propertyName, (String) envProperty.value, id);
        }
    }

    public void setupEnvProperties(List<PropertyInitializeDTO> envProperties, int id) {
        envProperties.forEach(currentProperty -> setupEnvProperties(currentProperty, id));
    }

    public void runSimulation() {
        simulationManager.StartSimulation(simulationManager.getLatestSimulation());
    }

    public void runSimulation(int id) {
        simulationManager.StartSimulation(id);
    }

    public List<EntityDTO> GetAllEntities(int id) {
        World currentWorld = simulationManager.getWorld(id);
        List<EntityDTO> resultList = new ArrayList<>();
        for (EntityDefinition entity : currentWorld.GetEntities()) {
            resultList.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        return resultList;
    }

    public List<RuleDTO> GetAllRules(int id) {
        World currentWorld = simulationManager.getWorld(id);
        List<RuleDTO> resultList = new ArrayList<>();
        for (Rule rule : currentWorld.getRules()) {
            resultList.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        return resultList;
    }

    public int GetSimulationTotalTicks(int id) {
        return simulationManager.getSimulation(id).GetSimulationTotalTicks();
    }

    public long GetSimulationTotalTime(int id) {
        return simulationManager.getSimulation(id).GetSimulationTotalTime();
    }

    public List<PropertyDTO> GetAllEnvProperties(int id) {
        List<PropertyDTO> resultList = new ArrayList<>();
        for (PropertyInterface envProperty : simulationManager.getWorld(id).getEnvironment().GetAllEnvVariables()) {
            resultList.add(new PropertyDTO(envProperty.getName(), envProperty.getPropertyType(), envProperty.getFrom(), envProperty.getTo(), envProperty.getRandomStatus()));
        }
        return resultList;
    }

    public void SetVariable(String variableName, int value, int id) {
        if (simulationManager.getWorld(id) != null) {
            double from = simulationManager.getWorld(id).getEnvironment().getProperty(variableName).getFrom();
            double to = simulationManager.getWorld(id).getEnvironment().getProperty(variableName).getTo();
            if (value < (int) from || value > (int) to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + (int) from + " to: " + (int) to);
            }
            simulationManager.getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public void SetVariable(String variableName, double value, int id) {
        if (simulationManager.getWorld(id) != null) {
            double from = simulationManager.getWorld(id).getEnvironment().getProperty(variableName).getFrom();
            double to = simulationManager.getWorld(id).getEnvironment().getProperty(variableName).getTo();
            if (value < from || value > to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + from + " to: " + to);
            }
            simulationManager.getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public void SetVariableBool(String variableName, String value, int id) {
        if (simulationManager.getWorld(id) != null) {
            if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                throw new RuntimeException("The value " + value + " cannot be parsed to boolean\n" +
                        "The value should be \"true\" or \"false\"");
            }
            simulationManager.getWorld(id).getEnvironment().updateProperty(variableName, Boolean.parseBoolean(value));
        }
    }

    public void SetVariable(String variableName, String value, int id) {
        if (simulationManager.getWorld(id) != null) {
            simulationManager.getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public WorldDTO getWorldDTO() {
        return simulationManager.getWorldDTO(simulationManager.getLatestSimulation());
    }

    public EntityInstanceManager GetInstanceManager(String name, int id) {
        return simulationManager.getWorld(id).GetInstances(name);
    }

    public void pauseSimulation(int id) {
        simulationManager.pauseSimulation(id);
    }

    public void resumeSimulation(int id) {
        simulationManager.resumeSimulation(id);
    }

    public void simulationManualStep(int id) {
        simulationManager.simulationManualStep(id);
    }
}

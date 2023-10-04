package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstanceManager;
import engine.exception.XMLException;
import engine.general.multiThread.api.SimulationRunner;
import engine.general.multiThread.impl.SimulationExecutionManager;
import engine.property.api.PropertyInterface;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.*;
import simulation.dto.PopulationsDTO;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.*;

public class Engine {
    private final NewXMLReader reader;
    private final Map<String, SimulationExecutionManager> simulationManager;

    public Engine() {
        reader = new NewXMLReader();
        simulationManager = new LinkedHashMap<>();
    }

    public SimulationRunner getSimulationRunner(int simID, String worldName) {
        return getSimulationManager(worldName).getSimulationRunner(simID);
    }

    public SimulationExecutionManager getSimulationManager(String worldName) {
        return simulationManager.get(worldName);
    }

    public int setupSimulation(InputStream fileContent) throws JAXBException, XMLException {
        int simulationID = -1;
        if (fileContent != null) {
            World aWholeNewworld = reader.ReadXMLFromStream(fileContent);
            if (!(simulationManager.containsKey(aWholeNewworld.getWorldName()))) {
                aWholeNewworld.setWorldVersion(1);
                simulationManager.put(aWholeNewworld.getWorldName(), new SimulationExecutionManager());
                simulationID = simulationManager.get(aWholeNewworld.getWorldName()).CreateSimulation(aWholeNewworld);
            } else {
                // WHY 0 ?
                int newWorldVersion = simulationManager.get(aWholeNewworld.getWorldName()).getWorld(0).getWorldVersion() + 1;
                aWholeNewworld.setWorldVersion(newWorldVersion);
                simulationManager.put(aWholeNewworld.getWorldName(), new SimulationExecutionManager());
                simulationID = simulationManager.get(aWholeNewworld.getWorldName()).CreateSimulation(aWholeNewworld);
                System.out.println("The new world version is: " + newWorldVersion);
            }


        }
        return simulationID;
    }

    public int clearSimulation(int id, String worldName) {
        return simulationManager.get(worldName).clearSimulation(id);
    }

    public void setupPopulation(EntityAmountDTO entityAmount, int id, String worldName) {
        World currentWorld = simulationManager.get(worldName).getWorld(id);
        if (currentWorld != null) {
            currentWorld.createPopulationOfEntity(entityAmount.entityName, entityAmount.amountInPopulation);
        }
    }

    public void setupPopulation(List<EntityAmountDTO> entityAmount, int id, String worldName) {
        entityAmount.forEach(currentEntity -> setupPopulation(currentEntity, id, worldName));
    }

    public void setupEnvProperties(PropertyInitializeDTO envProperty, int id, String worldName) {
        if (envProperty.value instanceof Integer) {
            SetVariable(envProperty.propertyName, (Integer) envProperty.value, id, worldName);
        } else if (envProperty.value instanceof Double) {
            SetVariable(envProperty.propertyName, (Double) envProperty.value, id, worldName);
        } else if (envProperty.value instanceof Boolean) {
            SetVariable(envProperty.propertyName, String.valueOf(envProperty.value), id, worldName);
        } else {
            SetVariable(envProperty.propertyName, (String) envProperty.value, id, worldName);
        }
    }

    public void setupEnvProperties(List<PropertyInitializeDTO> envProperties, int id, String worldName) {
        envProperties.forEach(currentProperty -> setupEnvProperties(currentProperty, id, worldName));
    }

    public int runSimulation(int id, String worldName) {
        simulationManager.get(worldName).StartSimulation(id++);
        return id;
    }

    public List<EntityDTO> GetAllEntities(int id, String worldName) {
        World currentWorld = simulationManager.get(worldName).getWorld(id);
        List<EntityDTO> resultList = new ArrayList<>();
        for (EntityDefinition entity : currentWorld.GetEntities()) {
            resultList.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        return resultList;
    }

    public List<RuleDTO> GetAllRules(int id, String worldName) {
        World currentWorld = simulationManager.get(worldName).getWorld(id);
        List<RuleDTO> resultList = new ArrayList<>();
        for (Rule rule : currentWorld.getRules()) {
            resultList.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        return resultList;
    }

    public WorldDTO getLastWorld() {
        Map.Entry<String, SimulationExecutionManager> lastEntry = null;
        for(Map.Entry<String, SimulationExecutionManager> entry : simulationManager.entrySet()) {
            lastEntry = entry;
        }

        if (lastEntry != null) {
            return lastEntry.getValue().getWorldDTO(0);
        }
        return null;
    }

    public List<WorldDTO> getAllWorlds() {
        List<WorldDTO> resultList = new ArrayList<>();
        simulationManager.values().forEach(manager -> {
            resultList.add(manager.getWorldDTO(0));
        });
        return resultList;
    }

//    public int GetSimulationTotalTicks(int id) {
//        return simulationManager.getSimulation(id).GetSimulationTotalTicks();
//    }

    //    public long GetSimulationTotalTime(int id) {
//        return simulationManager.getSimulation(id).GetSimulationTotalTime();
//    }
//
    public List<PropertyDTO> GetAllEnvProperties(int id, String worldName) {
        List<PropertyDTO> resultList = new ArrayList<>();
        for (PropertyInterface envProperty : simulationManager.get(worldName).getWorld(id).getEnvironment().GetAllEnvVariables()) {
            resultList.add(new PropertyDTO(envProperty.getName(), envProperty.getPropertyType(), envProperty.getFrom(), envProperty.getTo(), envProperty.getRandomStatus()));
        }
        return resultList;
    }

    public void SetVariable(String variableName, int value, int id, String worldName) {
        if (simulationManager.get(worldName).getWorld(id) != null) {
            double from = simulationManager.get(worldName).getWorld(id).getEnvironment().getProperty(variableName).getFrom();
            double to = simulationManager.get(worldName).getWorld(id).getEnvironment().getProperty(variableName).getTo();
            if (value < (int) from || value > (int) to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + (int) from + " to: " + (int) to);
            }
            simulationManager.get(worldName).getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public void SetVariable(String variableName, double value, int id, String worldName) {
        if (simulationManager.get(worldName).getWorld(id) != null) {
            double from = simulationManager.get(worldName).getWorld(id).getEnvironment().getProperty(variableName).getFrom();
            double to = simulationManager.get(worldName).getWorld(id).getEnvironment().getProperty(variableName).getTo();
            if (value < from || value > to) {
                throw new RuntimeException("The value " + value + " is out of bound\n" +
                        "The value should be between: " + from + " to: " + to);
            }
            simulationManager.get(worldName).getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public void SetVariableBool(String variableName, String value, int id, String worldName) {
        if (simulationManager.get(worldName).getWorld(id) != null) {
            if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                throw new RuntimeException("The value " + value + " cannot be parsed to boolean\n" +
                        "The value should be \"true\" or \"false\"");
            }
            simulationManager.get(worldName).getWorld(id).getEnvironment().updateProperty(variableName, Boolean.parseBoolean(value));
        }
    }

    public void SetVariable(String variableName, String value, int id, String worldName) {
        if (simulationManager.get(worldName).getWorld(id) != null) {
            simulationManager.get(worldName).getWorld(id).getEnvironment().updateProperty(variableName, value);
        }
    }

    public WorldDTO getWorldDTO(int id, String worldName) {
        return simulationManager.get(worldName).getWorldDTO(id);
    }

    public EntityInstanceManager GetInstanceManager(String name, int id, String worldName) {
        return simulationManager.get(worldName).getWorld(id).GetInstances(name);
    }

    public void pauseSimulation(int id, String worldName) {
        simulationManager.get(worldName).pauseSimulation(id);
    }

    public void resumeSimulation(int id, String worldName) {
        simulationManager.get(worldName).resumeSimulation(id);
    }

    public void abortSimulation(int id, String worldName) {
        simulationManager.get(worldName).abortSimulation(id);
    }

    public void stopSimulation(int id, String worldName) {
        //This function is to stop a simulation which has a termination-by-user option
        //Can check if the simulation has a termination by user or not
        simulationManager.get(worldName).manualStopSimulation(id);
    }

    public void simulationManualStep(int id, String worldName) {
        simulationManager.get(worldName).simulationManualStep(id);
    }

    public SimulationStatusDTO getSimulationDetails(int id, String worldName) {
        return simulationManager.get(worldName).getSimulationDetails(id);
    }

//    public int reRunSimulation(int id) throws JAXBException {
//        int newSimID = -1;
//        if (this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.DONE) || this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.ABORTED)) {
//            SimulationDTO currentSim = this.getSimulationManager().getSimulationDTO(id);
//            newSimID = setupSimulation();
//            Map<String, Object> envVariables = currentSim.getEnvProperties();
//            for (Map.Entry<String, Object> entry : envVariables.entrySet()) {
//                PropertyInitializeDTO currentProperty = new PropertyInitializeDTO(entry.getKey(), entry.getValue());
//                setupEnvProperties(currentProperty, newSimID);
//            }
//            Map<String, Integer> entities = currentSim.getPopulations();
//            for (Map.Entry<String, Integer> entry : entities.entrySet()) {
//                EntityAmountDTO currentEntity = new EntityAmountDTO(entry.getKey(), entry.getValue());
//                setupPopulation(currentEntity, newSimID);
//            }
////            runSimulation(newSimID);
//            return newSimID;
//        } else if (this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.RUNNING) || this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.PAUSED)) {
//            throw new RuntimeException("The simulation: " + id + " is still running, so can't rerun the simulation");
//        } else {
//            throw new RuntimeException("No such simulation ");
//        }
//    }

    public Map<Integer, PopulationsDTO> getEntitiesAmountPerTick(int id, String worldName) {
        return simulationManager.get(worldName).getEntitiesAmountPerTick(id);
    }

    public double getConsistency(String entityName, String propertyName, int id, String worldName) {
        return simulationManager.get(worldName).getConsistency(entityName, propertyName, id);
    }

    public Map<String, Integer> GetHistogram(String entityName, String propertyName, int id, String worldName) {
        return this.simulationManager.get(worldName).GetHistogram(entityName, propertyName, id);
    }

    public double averageValueOfProperty(String entityName, String propertyName, int id, String worldName) {
        return simulationManager.get(worldName).averageValueOfProperty(entityName, propertyName, id);
    }
}

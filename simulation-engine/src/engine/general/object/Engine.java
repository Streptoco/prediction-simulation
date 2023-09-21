package engine.general.object;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstanceManager;
import engine.exception.XMLException;
import engine.general.multiThread.api.Status;
import engine.general.multiThread.impl.SimulationExecutionManager;
import engine.property.api.PropertyInterface;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.*;
import simulations.dto.PopulationsDTO;
import simulations.dto.SimulationDTO;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;
import java.util.*;

public class Engine {
    private final NewXMLReader reader;
    private String filePath;
    private final SimulationExecutionManager simulationManager;

    public Engine() {
        reader = new NewXMLReader();
        simulationManager = new SimulationExecutionManager();
        filePath = "";
    }

    public SimulationExecutionManager getSimulationManager() {
        return simulationManager;
    }

    public void loadWorld(String filePath) {
        this.filePath = filePath;
    }

    public int setupSimulation() throws JAXBException, XMLException {
        int simulationID = -1;
        if (!filePath.isEmpty()) {
            World aWholeNewworld = reader.ReadXML(filePath);
            simulationID = simulationManager.CreateSimulation(aWholeNewworld);

        }
        return simulationID;
    }

    public void setupPopulation(EntityAmountDTO entityAmount, int id) {
        this.simulationManager.setupPopulation(entityAmount, id);
    }

    public void setupPopulations(List<EntityAmountDTO> entityAmount, int id) {
        entityAmount.forEach(currentEntity -> setupPopulation(currentEntity, id));
    }

    public void setupEnvProperties(PropertyInitializeDTO envProperty, int id) {
        this.simulationManager.setupEnvProperties(envProperty, id);
    }

    public void setupEnvProperties(List<PropertyInitializeDTO> envProperties, int id) {
        envProperties.forEach(currentProperty -> setupEnvProperties(currentProperty, id));
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

    public WorldDTO getWorldDTO(int id) {
        return simulationManager.getWorldDTO(id);
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

    public void abortSimulation(int id) {
        simulationManager.abortSimulation(id);
    }

    public void stopSimulation(int id) {
        //This function is to stop a simulation which has a termination-by-user option
        //Can check if the simulation has a termination by user or not
        simulationManager.manualStopSimulation(id);
    }

    public void simulationManualStep(int id) {
        simulationManager.simulationManualStep(id);
    }

    public SimulationStatusDTO getSimulationDetails(int id) {
        return simulationManager.getSimulationDetails(id);
    }

    public int reRunSimulation(int id) throws JAXBException {
        int newSimID = -1;
        if(this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.DONE) || this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.ABORTED)) {
            SimulationDTO currentSim = this.getSimulationManager().getSimulationDTO(id);
            newSimID = setupSimulation();
            Map<String, Object> envVariables = currentSim.getEnvProperties();
            for(Map.Entry<String, Object> entry : envVariables.entrySet()) {
                PropertyInitializeDTO currentProperty = new PropertyInitializeDTO(entry.getKey(), entry.getValue());
                setupEnvProperties(currentProperty, newSimID);
            }
            Map<String, Integer> entities = currentSim.getPopulations();
            for(Map.Entry<String, Integer> entry : entities.entrySet()) {
                EntityAmountDTO currentEntity = new EntityAmountDTO(entry.getKey(), entry.getValue());
                setupPopulation(currentEntity, newSimID);
            }
            runSimulation(newSimID);
            return newSimID;
        } else if (this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.RUNNING) || this.simulationManager.getSimulationRunner(id).getStatus().equals(Status.PAUSED)){
            throw new RuntimeException("The simulation: " + id + " is still running, so can't rerun the simulation");
        } else {
            throw new RuntimeException("No such simulation ");
        }
    }

    public Map<Integer, PopulationsDTO> getEntitiesAmountPerTick(int id) {
        return simulationManager.getEntitiesAmountPerTick(id);
    }

    public double getConsistency(String entityName, String propertyName, int id) {
        return simulationManager.getConsistency(entityName, propertyName, id);
    }

    public Map<String, Integer> GetHistogram(String entityName, String propertyName, int id) {
        return this.simulationManager.GetHistogram(entityName, propertyName, id);
    }

    public double averageValueOfProperty(String entityName, String propertyName, int id) {
        return simulationManager.averageValueOfProperty(entityName, propertyName, id);
    }
}

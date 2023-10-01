package enginetoui.dto.basic.impl;

import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Engine;
import engine.general.object.Environment;
import engine.general.object.Rule;
import engine.general.object.Termination;
import engine.property.api.PropertyInterface;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorldDTO {
    public final int simulationId;
    public final SimpleDateFormat simulationDate;
    public final GridDTO gridDTO;

    public final EnvironmentDTO environment;
    public final List<EntityDTO> entityDefinitions;

    public final List<RuleDTO> rules;
    public final List<InstancesDTO> instances;
    private final List<EntityInstanceManager> managerList;
    private final Date simDate;

    public TerminationDTO termination;

    public WorldDTO(int simulationId, SimpleDateFormat simulationDate, List<EntityInstanceManager> entities,
                    Date date, Termination termination, List<Rule> rules,
                    List<EntityDefinition> entityDefinitions, Environment environment, int gridRows, int gridCols) {
        this.rules = new ArrayList<>();
        this.entityDefinitions = new ArrayList<>();
        this.simulationId = simulationId;
        this.simulationDate = simulationDate;
        this.simDate = date;
        this.instances = new ArrayList<>();
        this.managerList = entities;
//        for (EntityInstanceManager entityManager : entities) { // TODO: no need for this since it will be later defined once properties are set.
//            this.instances.add(new InstancesDTO(entityManager.getCountInstances(), entityManager.getNumberOfAllInstances(), entityManager.getEntityName(), entityManager.getPropertiesName()));
//        }
        for (Rule rule : rules) {
            this.rules.add(new RuleDTO(rule.getName(), rule.getTick(), rule.getProbability(), rule.GetNumOfActions(), rule.getActions()));
        }
        for (EntityDefinition entity : entityDefinitions) {
            this.entityDefinitions.add(new EntityDTO(entity.getName(), entity.getPopulation(), entity.getProps()));
        }
        this.termination = new TerminationDTO(termination.getAllTicks(), termination.getHowManySecondsToRun(), termination.getIsInteractive());
        this.environment = new EnvironmentDTO(environment.GetAllEnvVariablesNames(), environment.GetAllEnvVariables());
        this.gridDTO = new GridDTO(gridRows, gridCols);
    }

    public List<RuleDTO> getRules() {
        return this.rules;
    }

    public List<EntityDTO> getEntities() {
        return this.entityDefinitions;
    }

    public String GetSimulationDateString() {
        return simulationDate.format(this.simDate);
    }

    public Map<String, Integer> getEntitiesAmountHistogram(int simId, Engine engine) {
        Map<String, Integer> resultMap = new HashMap<>();
                            Map<String, EntityInstanceManager> allEntities = new HashMap<>();
                            // This should be the entity instance managers map, but is it the right option? maybe we need to use the relevant DTO?
        for(Map.Entry<String, EntityInstanceManager> entry : allEntities.entrySet()) {
            for(EntityInstance currentEntity : entry.getValue().getInstances()) {
                if(currentEntity.isAlive()) {
                    if(resultMap.containsKey(currentEntity.getEntityName())) {
                        int val = resultMap.get(currentEntity.getEntityName()) + 1;
                        resultMap.put(currentEntity.getEntityName(), val);
                    } else {
                        resultMap.put(currentEntity.getEntityName(), 1);
                    }
                }
            }
        }
        return resultMap;
    }

    public Map<String, Integer> GetHistogram(String entityName, String propertyName, int simulationId, Engine engine) {
        Map<String, Integer> resultMap = new HashMap<>();
        EntityInstanceManager entity = engine.GetInstanceManager(entityName, simulationId);
        for (EntityInstance currentInstance : entity.getInstances()) {
            if (!currentInstance.isAlive()) {
                continue;
            }
            PropertyInterface currentProperty = currentInstance.getPropertyByName(propertyName);
            if (resultMap.containsKey((currentProperty.getValue().toString()))) {
                int val = (resultMap.get(currentProperty.getValue().toString())) + 1;
                resultMap.put((currentInstance.getPropertyByName(propertyName).getValue().toString()), val);
            } else {
                resultMap.put(currentProperty.getValue().toString(), 1);
            }
        }
        return resultMap;
    }

    @Override
    public String toString() {
        return "WorldDTO{" +
                "simulationId=" + simulationId +
                ", simulationDate=" + simulationDate +
                ", gridDTO=" + gridDTO +
                ", environment=" + environment +
                ", entityDefinitions=" + entityDefinitions +
                ", rules=" + rules +
                ", instances=" + instances +
                ", managerList=" + managerList +
                ", simDate=" + simDate +
                ", termination=" + termination +
                '}';
    }
}

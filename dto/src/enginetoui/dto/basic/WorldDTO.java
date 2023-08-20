package enginetoui.dto.basic;

import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.general.object.Engine;
import engine.property.api.PropertyInterface;

import java.text.SimpleDateFormat;
import java.util.*;

public class WorldDTO {
    public final int simulationId;
    public final SimpleDateFormat simulationDate;
    public final List<InstancesDTO> instances;
    private final List<EntityInstanceManager> managerList;
    private final Date simDate;

    public WorldDTO(int simulationId, SimpleDateFormat simulationDate, List<EntityInstanceManager> entities, Date date) {
        this.simulationId = simulationId;
        this.simulationDate = simulationDate;
        this.simDate = date;
        this.instances = new ArrayList<>();
        this.managerList = entities;
        for(EntityInstanceManager entityManager : entities) {
            this.instances.add(new InstancesDTO(entityManager.getCountInstances(), entityManager.getNumberOfAllInstances(), entityManager.getEntityName()));
        }
    }
    public String GetSimulationDateString() {
        return simulationDate.format(this.simDate);
    }

    public Map<String, Integer> GetHistogram(String entityName, String propertyName, int simulationId,Engine engine) {
        Map<String, Integer> resultMap = new HashMap<>();
        EntityInstanceManager entity = engine.GetInstanceManager(entityName,simulationId);
        for(EntityInstance currentInstance :entity.getInstances()) {
            PropertyInterface currentProperty = currentInstance.getPropertyByName(propertyName);
            if(resultMap.containsKey((String)currentProperty.getValue())) {
                int val = (resultMap.get((String) currentProperty.getValue())) + 1;
                resultMap.put((String) currentInstance.getPropertyByName(propertyName).getValue(), val);
            }
            else {
                resultMap.put((String) currentProperty.getValue(), 1);
            }
        }
        return resultMap;
    }
}

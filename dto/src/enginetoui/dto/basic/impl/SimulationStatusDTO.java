package enginetoui.dto.basic.impl;

import engine.entity.impl.EntityDefinition;
import engine.general.multiThread.api.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStatusDTO {
    public final int simID;
    public final Status status;

    public final int currentTick;
    public final long totalSecondsInMillis;
    public final long simulationRunningTimeInMillis;
    public final int totalTicks;
    public final Map<String, Integer> entitiesAmount;
    public final List<EntityDefinition> entityDefinitions;


    public SimulationStatusDTO(int simID, Status status, int currentTick, long simulationRunningTimeInMillis,
                               long totalSecondsInMillis, int totalTicks, List<EntityDefinition> entityDefinitions, Map<String, Integer> entitiesAmount) {
        this.simID = simID;
        this.status = status;
        this.currentTick = currentTick;
        this.simulationRunningTimeInMillis = simulationRunningTimeInMillis;
        this.totalSecondsInMillis = totalSecondsInMillis;
        this.totalTicks = totalTicks;
        this.entityDefinitions = entityDefinitions;
        this.entitiesAmount = entitiesAmount;
    }
    public void updateEntityAmount(String entityName, int amount) {
        this.entitiesAmount.put(entityName, amount);
    }
}

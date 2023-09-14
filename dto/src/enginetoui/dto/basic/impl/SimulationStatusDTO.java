package enginetoui.dto.basic.impl;

import engine.general.multiThread.api.Status;

import java.util.HashMap;
import java.util.Map;

public class SimulationStatusDTO {
    public final int simID;
    public final Status status;
    public final int currentTick;
    public final long simulationRunningTimeInMillis;
    public final Map<String, Integer> entitiesAmount;


    public SimulationStatusDTO(int simID, Status status, int currentTick, long simulationRunningTimeInMillis) {
        this.simID = simID;
        this.status = status;
        this.currentTick = currentTick;
        this.simulationRunningTimeInMillis = simulationRunningTimeInMillis;
        this.entitiesAmount = new HashMap<>();
    }
    public void updateEntityAmount(String entityName, int amount) {
        this.entitiesAmount.put(entityName, amount);
    }
}

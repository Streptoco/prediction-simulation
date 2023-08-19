package enginetoui.dto.basic;

import engine.entity.impl.EntityInstance;

import java.text.SimpleDateFormat;

public class WorldDTO {
    public final int simulationId;
    public final SimpleDateFormat simulationDate;
    public final InstancesDTO instance;

    public WorldDTO(int simulationId, SimpleDateFormat simulationDate, EntityInstance ) {
        this.simulationId = simulationId;
        this.simulationDate = simulationDate;
    }
}

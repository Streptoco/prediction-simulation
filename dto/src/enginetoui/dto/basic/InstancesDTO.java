package enginetoui.dto.basic;

import java.util.List;
import java.util.Set;

public class InstancesDTO {
    public final int numberOfRemainInstances;
    public final int numberOfInstances;
    public final String entityName;

    public final List<String> propertyNames;

    public InstancesDTO(int numberOfRemainInstances, int numberOfInstances, String entityName, List<String> properties) {
        this.numberOfRemainInstances = numberOfRemainInstances;
        this.numberOfInstances = numberOfInstances;
        this.entityName = entityName;
        this.propertyNames = properties;
    }
}

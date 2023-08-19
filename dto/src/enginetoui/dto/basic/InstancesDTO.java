package enginetoui.dto.basic;

public class InstancesDTO {
    public final int numberOfRemainInstances;
    public final int numberOfInstances;
    public final String entityName;

    public InstancesDTO(int numberOfRemainInstances, int numberOfInstances, String entityName) {
        this.numberOfRemainInstances = numberOfRemainInstances;
        this.numberOfInstances = numberOfInstances;
        this.entityName = entityName;
    }
}

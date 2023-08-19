package enginetoui.dto.basic;

public class EntityNotFoundDTO {
    public final String ruleName;
    public final String entityName;

    public EntityNotFoundDTO(String ruleName, String entityName) {
        this.ruleName = ruleName;
        this.entityName = entityName;
    }

}

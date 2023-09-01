package enginetoui.dto.basic.impl;

public class PropertyNotFoundDTO {
    public final String ruleName;
    public final String entityName;
    public final String propertyName;

    public PropertyNotFoundDTO(String ruleName, String entityName, String propertyName) {
        this.ruleName = ruleName;
        this.entityName = entityName;
        this.propertyName = propertyName;
    }
}

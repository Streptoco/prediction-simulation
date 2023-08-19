package enginetoui.dto.basic;

public class PropertyDuplicateNameDTO {
    public final String propertyName;
    public final String entityName;

    public PropertyDuplicateNameDTO(String propertyName, String entityName) {
        this.propertyName = propertyName;
        this.entityName = entityName;
    }
}

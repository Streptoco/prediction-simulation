package engine.exception;

public class XMLEntityPropertyNotFound extends XMLException {
    public XMLEntityPropertyNotFound(String filePath, String entityName, String propertyName) {
        super(filePath);
        this.exceptionMessage += "Couldn't find the property \"" + propertyName + "\" of the entity \"" + entityName + "\"";
    }

    public XMLEntityPropertyNotFound(String filePath, String entityName, String propertyName, String ruleName) {
        super(filePath);
        this.exceptionMessage += "Couldn't find the property \"" + propertyName + "\" of the entity \"" + entityName + "\" in the rule \"" + ruleName + "\"";
    }
}

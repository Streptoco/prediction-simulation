package engine.exception;

public class XMLEntityNotFoundException extends XMLException {
    public XMLEntityNotFoundException(String filePath, String ruleName, String entityName) {
        super(filePath);
        this.exceptionMessage += "In the rule " + ruleName + " the entity " + entityName + " didn't found";
    }
}

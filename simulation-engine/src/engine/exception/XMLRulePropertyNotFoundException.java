package engine.exception;

public class XMLRulePropertyNotFoundException extends XMLException {
    public XMLRulePropertyNotFoundException(String filePath, String ruleName, String entityName, String propertyName) {
        super(filePath);
        this.exceptionMessage += "In the rule " + ruleName + " the entity " + entityName + " didn't found";
    }
}

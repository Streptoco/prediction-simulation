package engine.exception;

import engine.action.api.ActionType;

public class XMLEntityNotFoundException extends XMLException {
    public XMLEntityNotFoundException(String filePath, String ruleName, String entityName) {
        super(filePath);
        this.exceptionMessage += "In the rule \"" + ruleName + "\" the entity \"" + entityName + "\" didn't found ";
    }

    public XMLEntityNotFoundException(String filePath, ActionType actionType, String ruleName, String entityName) {
        super(filePath);
        this.exceptionMessage += "In the rule \"" + ruleName + "\" in the action: \"" + actionType + "\" the entity \"" + entityName + "\" didn't found ";
    }
}

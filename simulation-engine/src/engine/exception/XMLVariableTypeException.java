package engine.exception;

import engine.action.api.ActionType;
import engine.action.expression.ReturnType;

public class XMLVariableTypeException  extends XMLException{
    public XMLVariableTypeException(String filePath, ReturnType action, ReturnType expected) {
        super(filePath);
        this.exceptionMessage += "There are invalid argument types in the action: \"" + action + "\" and the expected type was: \"" + expected + "\"";
    }

    public XMLVariableTypeException(String filePath, String ruleName ,ActionType action, ReturnType expected, ReturnType received) {
        super(filePath);
        this.exceptionMessage += "In the rule: \""  + ruleName + "\" there are invalid argument types in the action: \"" + action + "\" the expected type was: \"" + expected + "\" " +
                "and the actual was: \"" + received + "\" ";
    }

    public XMLVariableTypeException(String filePath, String value, ReturnType expected) {
        super(filePath);
        this.exceptionMessage += "Couldn't parse: \"" + value + "\" into: \"" + expected + "\" ";
    }
}

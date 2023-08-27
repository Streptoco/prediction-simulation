package engine.exception;

import engine.action.api.ActionType;
import engine.action.expression.ReturnType;

public class XMLVariableTypeException  extends XMLException{
    public XMLVariableTypeException(String filePath, ReturnType action, ReturnType expected) {
        super(filePath);
        this.exceptionMessage += "There are invalid argument types in the action: \"" + action + "\" and the expected type was: \"" + expected + "\"";
    }
}

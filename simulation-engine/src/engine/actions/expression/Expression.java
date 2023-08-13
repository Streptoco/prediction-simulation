package engine.actions.expression;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

import engine.Environment;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.properties.api.PropertyInterface;

import static engine.Environment.environmentGetter;
import static engine.Environment.propertyTypeGetter;
import static engine.World.*;


enum Type {
    FUNCTION,
    PROPERTY,
    FREE
}

public class Expression {
    String name;
    Type type;
    EntityInstance entityInstance;
    PropertyInterface propertyMatch;
    Object castedValueOfExpression;
    ReturnType returnType;

    public Expression (EntityInstance entityInstance, String name) { // random(5), "11", "true"
        this.name = name;
        this.entityInstance = entityInstance;
        evaluateExpression();
    }

    public void evaluateExpression() {
        propertyMatch = entityInstance.getPropertyByName(name);

        if (name.startsWith("random(")) {
            // TODO: segment to functions. it should be up to the parentheses... and then evaluate.
            type = Type.FUNCTION;
            this.returnType = ReturnType.INT;
            int stringValue = Integer.parseInt(name.replaceAll("[^0-9]", ""));
            castedValueOfExpression = randomGetter(stringValue);
            this.returnType = ReturnType.INT;
        }

        else if (name.startsWith("environment")) {
            String envVariableName = "";
            int startIndex = name.indexOf("(");
            int endIndex = name.indexOf(")");

            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                envVariableName = name.substring(startIndex + 1, endIndex);
            }

            castedValueOfExpression = environmentGetter(envVariableName);
            this.returnType = propertyTypeGetter(envVariableName);
        }
        else if (propertyMatch != null) {
            // we need to know the property type and then return the value
            type = Type.PROPERTY;
            this.returnType = propertyMatch.getPropertyType();
            castedValueOfExpression = propertyMatch.getValue();
        }
        else {
            type = Type.FREE;
            FreeValuePositioning();
        }
    }

    public void FreeValuePositioning() {
        try {
            castedValueOfExpression = Double.parseDouble(name);
            this.returnType = ReturnType.INT;
            return;
        } catch (NumberFormatException e) {
            //
        }
        try {
            castedValueOfExpression = Double.parseDouble(name);
            this.returnType = ReturnType.DECIMAL;
            return;
        } catch (NumberFormatException e) {
            //
        }
        try {
            castedValueOfExpression = Boolean.parseBoolean(name);
            this.returnType = ReturnType.BOOLEAN;
            return;
        } catch (NumberFormatException e) {
            //
        }
        this.returnType = ReturnType.STRING;
        return;
    }

    public Object getValue() { return castedValueOfExpression; }

    public ReturnType getReturnType() { return this.returnType; }

    public Object getCastedValueOfExpression() {
        return castedValueOfExpression;
    }
}

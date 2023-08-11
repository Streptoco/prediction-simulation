package engine.actions.expression;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

import engine.entity.impl.EntityDefinition;
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
    EntityDefinition entityDefinition;
    PropertyInterface propertyMatch;
    Object castedValueOfExpression;
    ReturnType returnType;

    public Expression (EntityDefinition entityDefinition, String name) { // random(5), "11", "true"
        this.name = name;
        this.entityDefinition = entityDefinition;
        evaluateExpression();
    }

    public void evaluateExpression() {
        propertyMatch = entityDefinition.getPropertyByName(name);

        if (name.equals("environment") || name.equals("random")) {
            // TODO: segment to functions. it should be up to the parentheses... and then evaluate.
            type = Type.FUNCTION;
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
            castedValueOfExpression = Integer.parseInt(name);
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

    public Object getCastedValueOfExpression() {
        return castedValueOfExpression;
    }
}

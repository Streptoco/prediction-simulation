package engine.actions.expression;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

import engine.entity.EntityDefinition;
import engine.properties.api.PropertyInterface;
import engine.properties.api.PropertyType;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

import java.util.Arrays;

import static engine.World.environmentGetter;
import static engine.World.randomGetter;

enum Type {
    FUNCTION,
    PROPERTY,
    FREE
}

enum ReturnType {
    INT,
    BOOLEAN,
    DECIMAL,
    STRING
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
        propertyParsing();
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
            if (propertyMatch.getPropertyType().equals(PropertyType.INT)) {
                this.returnType = ReturnType.INT;
                IntProperty intProperty = (IntProperty)propertyMatch;
                castedValueOfExpression = intProperty.getValue();
            }
            else if (propertyMatch.getPropertyType().equals(PropertyType.DECIMAL)) {
                this.returnType = ReturnType.DECIMAL;
                DecimalProperty decimalProperty = (DecimalProperty)propertyMatch;
                castedValueOfExpression = decimalProperty.getValue();
            }
            else if (propertyMatch.getPropertyType().equals(PropertyType.BOOLEAN)) {
                this.returnType = ReturnType.BOOLEAN;
                BooleanProperty booleanProperty = (BooleanProperty)propertyMatch;
                castedValueOfExpression = booleanProperty.getValue();
            }
            else {
                // TODO: handle error (no casting available)
            }
        }
        else {
            type = Type.FREE;
            FreeValuePositioning();
        }
    }

    public void propertyParsing() {
        String objectType = Arrays.toString(propertyMatch.getClass().getName().split("^(?=.*[A-Z])(?!.*[^A-Z]).*$\n")); //TODO: check regex. // IntProperty cigarettes-amount

        if (objectType.equalsIgnoreCase("int")) {
            int castedValueOfExpression = Integer.parseInt(name); //TODO: add try.
        }
        else if(objectType.equalsIgnoreCase("decimal")) {
            double castedValueOfExpression = Double.parseDouble(name);
        }
        else {
            if (objectType.equalsIgnoreCase("boolean")) {
                if (name.equalsIgnoreCase("true")) {
                    boolean castedValueOfExpression = true;
                }
                else if (name.equalsIgnoreCase("false")) {
                    boolean castedValueOfExpression = false;
                }
            }
            else {
                // TODO: throw exception.
            }
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

package engine.actions.expression;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

import engine.entity.EntityDefinition;
import engine.properties.api.PropertyInterface;

import java.util.Arrays;

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

    public Expression (EntityDefinition entityDefinition, String name) {
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
            type = Type.PROPERTY;
        }
        else {
            type = Type.FREE;
        }
    }

    public void propertyParsing() {
        String objectType = Arrays.toString(propertyMatch.getClass().getName().split("^(?=.*[A-Z])(?!.*[^A-Z]).*$\n")); //TODO: check regex.

        if (objectType.equalsIgnoreCase("int")) {
            castedValueOfExpression = Integer.parseInt(name); //TODO: add try.
        }
        else if(objectType.equalsIgnoreCase("decimal")) {
            castedValueOfExpression = Double.parseDouble(name);
        }
        else {
            if (objectType.equalsIgnoreCase("boolean")) {
                if (name.equalsIgnoreCase("true")) {
                    castedValueOfExpression = true;
                }
                else if (name.equalsIgnoreCase("false")) {
                    castedValueOfExpression = false;
                }
            }
            else {
                // TODO: throw exception.
            }
        }
    }

    public Object getValue() { return castedValueOfExpression; }

    public Object getCastedValueOfExpression() {
        return castedValueOfExpression;
    }
}

package engine.action.expression;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

import engine.context.api.Context;
import engine.property.api.PropertyInterface;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;

import static engine.general.object.World.*;


public class Expression {
    String name;
    Type type;
    PropertyInterface propertyMatch;
    Object castedValueOfExpression;
    ReturnType returnType;
    Number castedNumber;

    public Expression (String name) { // random(5), "11", "true"
        this.name = name;
    }

    public void evaluateExpression(Context context) {
        propertyMatch = context.getPrimaryEntityInstance().getPropertyByName(name);

        if (name.startsWith("random(")) {
            // TODO: segment to functions. it should be up to the parentheses... and then evaluate.
            type = Type.FUNCTION;
            this.returnType = ReturnType.INT;
            double stringValue = Double.parseDouble(name.replaceAll("[^0-9]", ""));
            castedNumber = NumberRandomGetter(0,stringValue);
            this.returnType = ReturnType.INT;
        }

        else if (name.startsWith("environment(")) {
            String envVariableName = "";
            int startIndex = name.indexOf("(");
            int endIndex = name.indexOf(")");
            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                envVariableName = name.substring(startIndex + 1, endIndex);
            }

            castedValueOfExpression = context.getEnvironmentVariable(envVariableName);
            ReturnType returnType = ((PropertyInterface) castedValueOfExpression).getPropertyType();
            switch (returnType) {
                case INT:
                    IntProperty intProperty = (IntProperty) castedValueOfExpression;
                    castedNumber = (Number) ((IntProperty) castedValueOfExpression).getValue();
                    break;
                case DECIMAL:
                    DecimalProperty decimalProperty = (DecimalProperty) castedValueOfExpression;
                    castedNumber = (Number) ((DecimalProperty) castedValueOfExpression).getValue();
                    break;
                case BOOLEAN:
                    castedValueOfExpression = ((PropertyInterface) castedValueOfExpression).getValue();
                    break;
            }
            this.returnType = returnType;
        }
        else if (propertyMatch != null) {
            // we need to know the property type and then return the value
            type = Type.PROPERTY;
            this.returnType = propertyMatch.getPropertyType();
            castedValueOfExpression = propertyMatch.getValue();
            castedNumber = (Number) propertyMatch.getValue();
        }
        else {
            type = Type.FREE;
            FreeValuePositioning();
        }
    }

    public void FreeValuePositioning() {
        try {
            castedValueOfExpression = Double.parseDouble(name);
            castedNumber = Double.parseDouble(name); //TODO: need to do it cross class
            this.returnType = ReturnType.INT;
            return;
        } catch (NumberFormatException e) {
            //
        }
        try {
            castedValueOfExpression = Double.parseDouble(name);
            castedNumber = Double.parseDouble(name);
            this.returnType = ReturnType.DECIMAL;
            return;
        } catch (NumberFormatException e) {
            //
        }
        if(!(name.equalsIgnoreCase("true") || name.equalsIgnoreCase("false"))) {
            castedValueOfExpression = (String) name;
            this.returnType = ReturnType.STRING;
            return;
        }
        try {
            //castedValueOfExpression = Boolean.parseBoolean(name);
            castedValueOfExpression = (String) name;
            this.returnType = ReturnType.BOOLEAN;
            return;
        } catch (NumberFormatException e) {
            //
        }

    }

    public Object getValue() { return castedValueOfExpression; }

    public ReturnType getReturnType() { return this.returnType; }

    public Number getCastedNumber() { return castedNumber; }
}

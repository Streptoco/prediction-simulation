package engine.action.expression;

import engine.context.api.Context;
import engine.property.api.PropertyInterface;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

import static engine.general.object.World.*;


public class OldExpression {
    String name;
    Type type;
    PropertyInterface propertyMatch;
    Object castedValueOfExpression;
    ReturnType returnType;
    Number castedNumber;

    public OldExpression(String name) {
        this.name = name;
    }

    public void evaluateExpression(Context context) {
        propertyMatch = context.getPrimaryEntityInstance().getPropertyByName(name);

        if (name.startsWith("random(")) {
            type = Type.FUNCTION;
            this.returnType = ReturnType.INT;
            double stringValue = Double.parseDouble(name.replaceAll("[^0-9]", ""));
            castedNumber = NumberRandomGetter(0, stringValue);
            this.returnType = ReturnType.INT;
        } else if (name.startsWith("environment(")) {
            this.type= Type.FUNCTION;
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
                case STRING:
                    castedValueOfExpression = ((StringProperty) castedValueOfExpression).getValue();
                    break;
            }
            this.returnType = returnType;
        } else if (name.startsWith("evaluate(")) {
            String entityName = "", propertyName = "";
            int entityNameStartIndex, entityNameEndIndex, propertyNameStartIndex, propertyNameEndIndex;
            entityNameStartIndex = name.indexOf("(");
            entityNameEndIndex = name.indexOf(".");
            if (entityNameStartIndex != -1 && entityNameEndIndex != -1) {
                // the entity name is irrelevant because we use the context?
                entityName = name.substring(entityNameStartIndex + 1, entityNameEndIndex);
            } else {
                //TODO: throw entity not found exception
            }
            propertyNameStartIndex = entityNameEndIndex;
            propertyNameEndIndex = name.indexOf(")");
            if (propertyNameStartIndex != -1 && propertyNameEndIndex != -1) {
                propertyName = name.substring(propertyNameStartIndex + 1, propertyNameEndIndex);
            } else {
                //TODO: throw property not found exception
            }
            if (entityName.equals(context.getPrimaryEntityInstance().getEntityName())) { // if(entityName.equals(context.getEntityName()))
                castedValueOfExpression = context.getPrimaryEntityInstance().getPropertyByName(propertyName).getValue();
                this.returnType = context.getPrimaryEntityInstance().getPropertyByName(propertyName).getPropertyType();
            } else {
                castedValueOfExpression = context.getSecondaryEntityInstance().getPropertyByName(propertyName).getValue();
                this.returnType = context.getSecondaryEntityInstance().getPropertyByName(propertyName).getPropertyType();
            }
            switch (this.returnType) {
                case DECIMAL:
                case INT:
                    castedNumber = (Number) castedValueOfExpression;
            }

        } else if (name.startsWith("ticks(")) {
            this.type= Type.FUNCTION;
            String entityName = "", propertyName = "";
            int entityNameStartIndex, entityNameEndIndex, propertyNameStartIndex, propertyNameEndIndex;
            entityNameStartIndex = name.indexOf("(");
            entityNameEndIndex = name.indexOf(".");
            if (entityNameStartIndex != -1 && entityNameEndIndex != -1) {
                entityName = name.substring(entityNameStartIndex + 1, entityNameEndIndex);
            }
            propertyNameStartIndex = entityNameEndIndex;
            propertyNameEndIndex = name.indexOf(")");
            if (propertyNameStartIndex != -1 && propertyNameEndIndex != -1) {
                propertyName = name.substring(propertyNameStartIndex + 1, propertyNameEndIndex);
            }
            castedNumber = context.getInstance(entityName).getPropertyByName(propertyName).timeSinceLastChange(context.getCurrentTick());
            this.returnType = ReturnType.INT;



        } else if (propertyMatch != null) {
            // we need to know the property type and then return the value
            type = Type.PROPERTY;
            this.returnType = propertyMatch.getPropertyType();
            castedValueOfExpression = propertyMatch.getValue();
            castedNumber = (Number) propertyMatch.getValue();
        } else {
            type = Type.FREE;
            FreeValuePositioning();
        }
    }

    public void FreeValuePositioning() {
        try {
            castedValueOfExpression = Double.parseDouble(name);
            castedNumber = Double.parseDouble(name);
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
        if (!(name.equalsIgnoreCase("true") || name.equalsIgnoreCase("false"))) {
            castedValueOfExpression = (String) name;
            this.returnType = ReturnType.STRING;
            return;
        }
        try {
            castedValueOfExpression = (String) name;
            this.returnType = ReturnType.BOOLEAN;
            return;
        } catch (NumberFormatException e) {
            //
        }

    }

    public Object getValue() {
        if(this.returnType.equals(ReturnType.INT) || this.returnType.equals(ReturnType.DECIMAL)) {
            return castedNumber;
        } else {
            return castedValueOfExpression;
        }
    }

    public ReturnType getReturnType() {
        return this.returnType;
    }

    public Number getCastedNumber() {
        return castedNumber;
    }

    public String getName() {
        return name;
    }
}

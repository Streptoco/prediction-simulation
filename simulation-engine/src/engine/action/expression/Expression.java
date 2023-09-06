package engine.action.expression;

import engine.context.api.Context;
import engine.property.api.PropertyInterface;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;

import static engine.general.object.World.NumberRandomGetter;

public class Expression {
    private final String expression;
    private Type expressionType;
    private ReturnType returnType;
    private Object castedValueOfExpression;

    public Expression(String expression) {
        this.expression = expression;
        if (expression.startsWith("environment(")) {
            this.expressionType = Type.ENVVARIABLE;
        } else if (expression.startsWith("random(")) {
            this.expressionType = Type.RANDOM;
        } else if (expression.startsWith("evaluate(")) {
            this.expressionType = Type.EVALUATE;
        } else if (expression.startsWith("ticks(")) {
            this.expressionType = Type.TICKS;
        } else if (expression.startsWith("percent(")) {
            this.expressionType = Type.PERCENT;
        } else if (expression.equalsIgnoreCase("true") || expression.equalsIgnoreCase("false")) {
            this.expressionType = Type.BOOLEAN;
            this.returnType = ReturnType.BOOLEAN;
        } else {
            try {
                Double.parseDouble(expression);
                this.expressionType = Type.NUMBER;
                this.returnType = ReturnType.DECIMAL;
            } catch (NumberFormatException e) {
                this.expressionType = Type.STRING;
                this.returnType = ReturnType.STRING;
            }
        }
    }

    public Type getType() {
        return expressionType;
    }
    public Object getValue() {
        return castedValueOfExpression;
    }
    public String getName() {return expression;}

    public void evaluateExpression(Context context) {
        propertyMatch = context.getPrimaryEntityInstance().getPropertyByName(name);
    }

    public String getExpression() {
        return expression;
    }

    public ReturnType getReturnType() {
        if(returnType != null) {
            return returnType;
        } else {
            return null;
        }
    }

    private void assignValue(PropertyInterface entityProperty) {
        switch (returnType) {
            case INT:
            case DECIMAL:
                castedValueOfExpression = new Double(entityProperty.getValue().toString());
                break;
            case BOOLEAN:
                castedValueOfExpression = Boolean.valueOf(entityProperty.getValue().toString());
                break;
            case STRING:
                castedValueOfExpression = entityProperty.getValue().toString();
                break;
        }
    }

    private void evaluateEnvVariable(Context context) {
        PropertyInterface envVariable;
        String envVariableName = "";
        int startIndex = expression.indexOf("(");
        int endIndex = expression.indexOf(")");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            envVariableName = expression.substring(startIndex + 1, endIndex);
        }
        envVariable = context.getEnvironmentVariable(envVariableName);
        this.returnType = envVariable.getPropertyType();
        assignValue(envVariable);
    }

    private void createRandomValue() {
        double stringValue = Double.parseDouble(expression.replaceAll("[^0-9]", ""));
        this.returnType = ReturnType.DECIMAL;
        castedValueOfExpression = NumberRandomGetter(0, stringValue);
    }

    private void evaluateEntityProperty(Context context) {
        String entityName = "", propertyName = "";
        int entityNameStartIndex, entityNameEndIndex, propertyNameStartIndex, propertyNameEndIndex;
        entityNameStartIndex = expression.indexOf("(");
        entityNameEndIndex = expression.indexOf(".");
        if (entityNameStartIndex != -1 && entityNameEndIndex != -1) {
            entityName = expression.substring(entityNameStartIndex + 1, entityNameEndIndex);
        }
        propertyNameStartIndex = entityNameEndIndex;
        propertyNameEndIndex = expression.indexOf(")");
        if (propertyNameStartIndex != -1 && propertyNameEndIndex != -1) {
            propertyName = expression.substring(propertyNameStartIndex + 1, propertyNameEndIndex);
        }
        PropertyInterface entityProperty = context.getInstance(entityName).getPropertyByName(propertyName);
        this.returnType = entityProperty.getPropertyType();
        assignValue(entityProperty);
    }

    private void getTicksSinceChange(Context context) {
        String entityName = "", propertyName = "";
        int entityNameStartIndex, entityNameEndIndex, propertyNameStartIndex, propertyNameEndIndex;
        entityNameStartIndex = expression.indexOf("(");
        entityNameEndIndex = expression.indexOf(".");
        if (entityNameStartIndex != -1 && entityNameEndIndex != -1) {
            entityName = expression.substring(entityNameStartIndex + 1, entityNameEndIndex);
        }
        propertyNameStartIndex = entityNameEndIndex;
        propertyNameEndIndex = expression.indexOf(")");
        if (propertyNameStartIndex != -1 && propertyNameEndIndex != -1) {
            propertyName = expression.substring(propertyNameStartIndex + 1, propertyNameEndIndex);
        }

        castedValueOfExpression = (double) context.getInstance(entityName).getPropertyByName(propertyName).timeSinceLastChange(context.getCurrentTick());
        this.returnType = ReturnType.INT;
    }

    private void evaluateString(Context context) {
        if (context.getPrimaryEntityInstance().getPropertyByName(expression) != null) {
            this.expressionType = Type.PROPERTY;
            PropertyInterface entityProperty = context.getPrimaryEntityInstance().getPropertyByName(expression);
            this.returnType = entityProperty.getPropertyType();
            assignValue(entityProperty);
        } else {
            castedValueOfExpression = expression;
            this.returnType = ReturnType.STRING;
            return;
        }
    }

    public void evaluateExpression(Context context) {
        switch (expressionType) {
            case ENVVARIABLE:
                evaluateEnvVariable(context);
                break;
            case RANDOM:
                createRandomValue();
                break;
            case EVALUATE:
                evaluateEntityProperty(context);
                break;
            case TICKS:
                getTicksSinceChange(context);
                break;
            case NUMBER:
                castedValueOfExpression = new Double(expression);
                break;
            case PERCENT:
                break;
            case BOOLEAN:
                if (expression.equalsIgnoreCase("true")) {
                    castedValueOfExpression = Boolean.TRUE;
                } else {
                    castedValueOfExpression = Boolean.FALSE;
                }
                this.returnType = ReturnType.BOOLEAN;
                break;
            case STRING:
                evaluateString(context);
                break;


        }
    }

}

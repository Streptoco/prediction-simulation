package engine.action.impl.condition.impl;

import engine.action.expression.Expression;
import engine.action.expression.Type;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.property.api.AbstractProperty;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

public class SingleCondition implements Condition {
    private PropertyInterface propertyInstance;
    private String valueOperator;
    private String entityName;
    //TODO: need to make the property as expression
    private Expression property;
    private Expression valueExpression;

    public SingleCondition(String entityName, Expression propertyName, String operator, Expression valueExpression) {
        this.entityName = entityName;
        this.property = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
    }

    public boolean EvaluateExpression(PropertyExpressionEvaluation result) {
        if (valueOperator.equals("=")) {
            return result.isEqual();
        } else if (valueOperator.equals("!=")) {
            return !result.isEqual();
        } else if (valueOperator.equalsIgnoreCase("bt")) {
            return result.isGreater();
        } else {
            return !result.isGreater();
        }
    }


    public boolean evaluate(Context context) {
        PropertyExpressionEvaluation result = null;
        property.evaluateExpression(context);
        if (property.getType().equals(Type.PROPERTY)) {
            propertyInstance = context.getInstance(this.entityName).getPropertyByName(this.property.getExpression());
            valueExpression.evaluateExpression(context);
            result = propertyInstance.evaluate(valueExpression);
        } else {
            property.evaluateExpression(context);
            PropertyInterface propertyAsExpression = null;
            switch (property.getReturnType()) {
                case INT:
                    //propertyAsExpression = new IntProperty(((Double) property.getValue()).intValue(), property.getExpression(), ((Double) property.getValue()), ((Double) property.getValue()), false);
                    propertyAsExpression = new IntProperty(((Double) property.getValue()).intValue(), property.getExpression(), (Double) property.getValue(), (Double) property.getValue(), false);
                    break;
                case DECIMAL:
                    propertyAsExpression = new DecimalProperty((Double) property.getValue(), property.getExpression(), (Double) property.getValue(), (Double) property.getValue(), false);
                    break;
                case BOOLEAN:
                    propertyAsExpression = new BooleanProperty((boolean) property.getValue(), property.getExpression(), 0, 0, false);
                    break;
                case STRING:
                    propertyAsExpression = new StringProperty((String) property.getValue(), (String) property.getValue(), 0, 0, false);
                    break;
            }
            if (propertyAsExpression != null) {
                valueExpression.evaluateExpression(context);
                result = propertyAsExpression.evaluate(valueExpression);
            }
        }


        return EvaluateExpression(result);
    }
}

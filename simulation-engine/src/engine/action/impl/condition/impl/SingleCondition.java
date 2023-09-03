package engine.action.impl.condition.impl;

import engine.action.expression.Expression;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.property.api.PropertyInterface;

public class SingleCondition implements Condition {
    private PropertyInterface propertyInstance;
    private String valueOperator;
    private String entityName;
    //TODO: need to make the property as expression
    private String propertyName;
    private Expression valueExpression;

    public SingleCondition(String entityName, String propertyName, String operator, Expression valueExpression) {
        this.entityName = entityName;
        this.propertyName = propertyName;
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
        propertyInstance = context.getInstance(this.entityName).getPropertyByName(this.propertyName);
        valueExpression.evaluateExpression(context);
        PropertyExpressionEvaluation result = propertyInstance.evaluate(valueExpression);

        return EvaluateExpression(result);
    }
}

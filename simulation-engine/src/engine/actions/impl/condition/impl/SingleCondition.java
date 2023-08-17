package engine.actions.impl.condition.impl;

import engine.actions.expression.Expression;
import engine.actions.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.properties.api.PropertyInterface;

public class SingleCondition implements Condition{
    private PropertyInterface propertyInstance;
    private String valueOperator;
    private String propertyName;
    private Expression valueExpression;
    public SingleCondition(String propertyName, String operator, Expression valueExpression) {
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
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(this.propertyName);
        PropertyExpressionEvaluation result = propertyInstance.evaluate(valueExpression);

        return EvaluateExpression(result);
    }
}

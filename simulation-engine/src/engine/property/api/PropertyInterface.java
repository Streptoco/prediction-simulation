package engine.property.api;

// TODO: manage the range better. maybe create a class for the range.

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;

public interface PropertyInterface {
    String getName();

    double getFrom();

    double getTo();

    boolean getRandomStatus();

    ReturnType getPropertyType();

    Object getValue();

    PropertyExpressionEvaluation evaluate(Expression expression);
    void setPropertyValue(Object value);
}

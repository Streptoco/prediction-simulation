package engine.property.api;

// TODO: manage the range better. maybe create a class for the range.

import engine.action.expression.Expression;
import engine.action.expression.*;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;

public interface PropertyInterface {
    public String getName();

    public double getFrom();

    public double getTo();

    public boolean getRandomStatus();

    public ReturnType getPropertyType();

    public Object getValue();


    public PropertyExpressionEvaluation evaluate(Expression expression);
}

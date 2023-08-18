package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

public class StringProperty extends AbstractProperty {
    private String value;

    public StringProperty(String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        //TODO: Why the name is the value?
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, ReturnType.STRING);
        this.value = name;
    }

    public Object getValue() { return this.value; }

    // TODO: a way to change it maybe through interface.

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        return null;
    }
}

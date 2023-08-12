package engine.properties.impl;

import engine.actions.expression.Expression;
import engine.actions.expression.ReturnType;
import engine.actions.impl.condition.api.PropertyExpressionEvaluation;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

public class StringProperty extends AbstractProperty {
    private String value;

    public StringProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, ReturnType.STRING);
        this.value = name;
    }

    public Object getValue() { return this.value; }

    // TODO: a way to change it maybe through interface.

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        return null;
    }
}

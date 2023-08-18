package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

public class BooleanProperty extends AbstractProperty {
    private boolean value;

    public BooleanProperty(boolean value, String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, ReturnType.BOOLEAN);
        this.value = value;
    }

    public Object getValue() { return this.value; }

    // TODO: we'll probably need a way to change this value, maybe implement it in the interface?
    public PropertyExpressionEvaluation evaluate(Expression expression) {
        return null;
    }

    public void setValue(boolean newVal) {
        this.value = newVal;
    }
}

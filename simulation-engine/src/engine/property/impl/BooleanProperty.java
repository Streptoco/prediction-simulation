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

    public Object getValue() {
        return this.value;
    }

    // TODO: we'll probably need a way to change this value, maybe implement it in the interface?
    public PropertyExpressionEvaluation evaluate(Expression expression) {
        //boolean expressionValue = (boolean) expression.getValue();
        boolean expressionValue = Boolean.parseBoolean(expression.getValue().toString());
        PropertyExpressionEvaluation result;
        if (expressionValue == this.value) { //TODO: not working as expected. need to fix that
            result = new PropertyExpressionEvaluation(true, false);
        } else {
            result = new PropertyExpressionEvaluation(false, false);
        }

        return result;
    }

    public void setValue(boolean newVal, int currentTick) {
        this.value = newVal;
        this.sumOfConsistency += timeSinceLastChange(currentTick);
        this.lastChangedTick = currentTick;
        this.countOfChanges++;
    }

    @Override
    public void setPropertyValue(Object value,int currentTick) {
        setValue(Boolean.parseBoolean(value.toString()), currentTick);
    }
}
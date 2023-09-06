package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

// TODO: change range to an actual object.

public class IntProperty extends AbstractProperty {
    private int value;

    public IntProperty(int value, String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated, ReturnType.INT);
        this.value = value;
    }

    public Object getValue() { return value; }
    public void increaseValue(int value, int currentTick) {
        if (rangeFrom.intValue() < (this.value + value) && rangeTo.intValue() > (this.value + value)) {
            this.value += value;
            this.lastChangedTick = currentTick;
        }
    }

    public void decreaseValue(int value, int currentTick) {
        if (super.getFrom() < (this.value - value) && super.getTo() > (this.value - value)) {
            this.value -= value;
            this.lastChangedTick = currentTick;
        }
    }

    public void setValue(int value, int currentTick) {
        if (super.getFrom() < value && super.getTo() > value) {
            this.value = value;
            this.lastChangedTick = currentTick;
        }
    }

    @Override
    public void setPropertyValue(Object value, int currentTick) {
        setValue((int) value, currentTick);
    }

    @Override
    public PropertyExpressionEvaluation evaluate(Expression expression) {
        //int expressionValue = (int)expression.getValue();
        int expressionValue = ((Double)expression.getValue()).intValue();
        PropertyExpressionEvaluation result;
        if(this.value == expressionValue) {
            result = new PropertyExpressionEvaluation(true, false);
        }
        else {
            if(this.value > expressionValue) {
                result = new PropertyExpressionEvaluation(false, true);
            }
            else {
                result = new PropertyExpressionEvaluation(false, false);
            }
        }

        return result;
    }
}

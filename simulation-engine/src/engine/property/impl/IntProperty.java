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
    public void increaseValue(int value) {
        if (super.getFrom() < (this.value + value) && super.getTo() > (this.value + value)) {
            this.value += value;
        }
    }

    public void decreaseValue(int value) {
        if (super.getFrom() < (this.value - value) && super.getTo() > (this.value - value)) {
            this.value -= value;
        }
    }

    public void setValue(int value) {
        if (super.getFrom() < value && super.getTo() > value) {
            this.value = value;
        }
    }

    @Override
    public PropertyExpressionEvaluation evaluate(Expression expression) {
        //int expressionValue = (int)expression.getValue();
        int expressionValue = expression.getCastedNumber().intValue();
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

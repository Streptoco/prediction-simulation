package engine.property.impl;

import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.property.api.AbstractProperty;

public class DecimalProperty extends AbstractProperty {
    private double value;

    public DecimalProperty(double value, String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated, ReturnType.DECIMAL);
        this.value = value;
    }

    public Object getValue() { return value; }

    public void increaseValue(double value) {
        if (super.getFrom() < (this.value + value) && super.getTo() > (this.value + value)) {
            this.value += value;
        }
    }

    public void decreaseValue(double value) {
        if (super.getFrom() < (this.value - value) && super.getTo() > (this.value - value)) { // from: 10 to: 60 // 32 - 11.25
            this.value -= value;
        }
    }
    public void setValue(double value) {
        if (super.getFrom() < value && super.getTo() > value) {
            this.value = value;
        }
    }

    @Override
    public void setPropertyValue(Object value) {
        setValue((double)value);
    }

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        double expresionValue = expression.getCastedNumber().doubleValue();
        PropertyExpressionEvaluation result;
        if(this.value == expresionValue) {
            result = new PropertyExpressionEvaluation(true, false);
        }
        else {
            if(this.value > expresionValue) {
                result = new PropertyExpressionEvaluation(false, true);
            }
            else {
                result = new PropertyExpressionEvaluation(false, false);
            }
        }

        return result;
    }
}

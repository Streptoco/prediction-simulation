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

    public void increaseValue(double value, int currentTick) {
        if (super.getFrom() < (this.value + value) && super.getTo() > (this.value + value)) {
            this.value += value;
            this.sumOfConsistency += timeSinceLastChange(currentTick);
            this.lastChangedTick = currentTick;
            this.countOfChanges++;
        }
    }

    public void decreaseValue(double value, int currentTick) {
        if (super.getFrom() < (this.value - value) && super.getTo() > (this.value - value)) { // from: 10 to: 60 // 32 - 11.25
            this.value -= value;
            this.sumOfConsistency += timeSinceLastChange(currentTick);
            this.lastChangedTick = currentTick;
            this.countOfChanges++;
        }
    }
    public void setValue(double value, int currentTick) {
        if (super.getFrom() < value && super.getTo() > value) {
            this.value = value;
            this.sumOfConsistency += timeSinceLastChange(currentTick);
            this.lastChangedTick = currentTick;
            this.countOfChanges++;
        }
    }

    @Override
    public void setPropertyValue(Object value, int currentTick) {
        setValue((double)value, currentTick);
    }

    public PropertyExpressionEvaluation evaluate(Expression expression) {
        double expressionValue = (Double) expression.getValue();
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

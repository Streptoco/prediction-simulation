package engine.properties.impl;

import engine.actions.expression.ReturnType;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

public class DecimalProperty extends AbstractProperty {
    private double value;

    public DecimalProperty(double value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
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
}

package engine.properties.impl;

import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

public class DecimalProperty extends AbstractProperty {
    private double value;

    public DecimalProperty(double value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated, PropertyType.DECIMAL);
        this.value = value;
    }

    public double getValue() { return value; }

    public void increaseValue(double value) {
        if (super.getFrom() < (this.value + value) && super.getTo() < (this.value + value)) {
            this.value += value;
        }
    }
}

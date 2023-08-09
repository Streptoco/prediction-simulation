package engine.properties.impl;

import engine.properties.Property;

public class DecimalProperty extends Property {
    private double value;

    public DecimalProperty(double value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated);
        this.value = value;
    }

    public double getValue() { return value; }

    public void increaseValue(double value) {
        if (super.getFrom() < (this.value + value) && super.getTo() < (this.value + value)) {
            this.value += value;
        }
    }
}

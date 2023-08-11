package engine.properties.impl;

import engine.actions.expression.ReturnType;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

// TODO: change range to an actual object.

public class IntProperty extends AbstractProperty {
    private int value;

    public IntProperty(int value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
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
}

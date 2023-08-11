package engine.properties.impl;

import engine.actions.expression.ReturnType;
import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

public class BooleanProperty extends AbstractProperty {
    private boolean value;

    public BooleanProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, PropertyType.BOOLEAN);
    }

    public Object getValue() { return this.value; }

    // TODO: we'll probably need a way to change this value, maybe implement it in the interface?
}

package engine.properties.impl;

import engine.properties.api.AbstractProperty;
import engine.properties.api.PropertyType;

public class StringProperty extends AbstractProperty {
    private String value;

    public StringProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated, PropertyType.STRING);
    }

    public Object getValue() { return this.value; }

    // TODO: a way to change it maybe through interface.
}

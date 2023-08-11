package engine.properties.api;

// TODO: manage the range better. maybe create a class for the range.

import engine.actions.expression.ReturnType;

public interface PropertyInterface {
    public String getName();

    public int getFrom();

    public int getTo();

    public ReturnType getPropertyType();

    public Object getValue();
}

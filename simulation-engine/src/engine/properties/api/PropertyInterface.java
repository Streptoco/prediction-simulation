package engine.properties.api;

// TODO: manage the range better. maybe create a class for the range.

enum PropertyType {
    INT,
    DECIMAL,
    STRING,
    BOOLEAN
}

public interface PropertyInterface {
    public String getName();

    public int getFrom();

    public int getTo();

    public PropertyType getPropertyType();
}

package engine.properties.api;

import engine.actions.expression.ReturnType;

public abstract class AbstractProperty implements PropertyInterface {
    private String name;
    private int rangeFrom;
    private int rangeTo;
    private boolean isRandomlyGenerated;

    private ReturnType propertyType;

    public AbstractProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated, ReturnType propertyType) {
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomlyGenerated = isRandomlyGenerated;
        this.propertyType = propertyType;
    }

    public String getName() { return name; }

    public int getFrom() { return rangeFrom; }

    public int getTo() { return rangeTo; }

    public ReturnType getPropertyType() { return propertyType; }

    public abstract Object getValue();
}

package engine.properties.api;

public class AbstractProperty implements PropertyInterface {
    private String name;
    private int rangeFrom;
    private int rangeTo;
    private boolean isRandomlyGenerated;

    private PropertyType propertyType;

    public AbstractProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated, PropertyType propertyType) {
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomlyGenerated = isRandomlyGenerated;
        this.propertyType = propertyType;
    }

    public String getName() { return name; }

    public int getFrom() { return rangeFrom; }

    public int getTo() { return rangeTo; }

    public PropertyType getPropertyType() { return propertyType; }
}

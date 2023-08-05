package engine;

// TODO: make interface

enum PropertyType {
    INT,
    DECIMAL,
    STRING,
    BOOLEAN
}

public class Property {
    private String name;
    private int rangeFrom;
    private int rangeTo;
    private boolean isRandomlyGenerated;
    private PropertyType propertyType;

    public Property(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomlyGenerated = isRandomlyGenerated;
    }

    public String getName() { return name; }
}

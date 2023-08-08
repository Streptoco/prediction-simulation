package engine.properties;

public class StringProperty extends Property {
    private String value;

    public StringProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated);
    }

    public String getValue() { return this.value; }
}
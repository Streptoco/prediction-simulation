package engine.properties;

public class BooleanProperty extends Property {
    private boolean value;

    public BooleanProperty(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name, rangeFrom, rangeTo, isRandomlyGenerated);
    }

    public boolean getValue() { return this.value; }
}

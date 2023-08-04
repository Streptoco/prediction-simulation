package engine;

public class DecimalProperty extends Property {
    private double value;

    public DecimalProperty(double value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated);
        this.value = value;
    }

    public double getValue() { return value; }
}

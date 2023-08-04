package engine;

public class DecimalProperty extends Property {
    private double value;

    public DecimalProperty(double value, String name, int range, boolean isRandomlyGenerated) {
        super(name,range,isRandomlyGenerated);
        this.value = value;
    }
}

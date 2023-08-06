package engine.properties;

public class IntProperty extends Property {
    private int value;

    public IntProperty(int value, String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        super(name,rangeFrom,rangeTo,isRandomlyGenerated);
        this.value = value;
    }

    public int getValue() { return value; }
    public void increaseValue(int value) {
        if (super.getFrom() < (this.value + value) && super.getTo() > (this.value + value)) {
            this.value += value;
        }
    }
}

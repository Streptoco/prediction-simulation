package engine;

public class IntProperty extends Property {
    private int value;

    public IntProperty(int value, String name, int range, boolean isRandomlyGenerated) {
        super(name,range,isRandomlyGenerated);
        this.value = value;
    }
}

package engine;

// TODO: make interface

public class Property {
    private String name;
    private int range;
    private boolean isRandomlyGenerated;

    public Property(String name, int range, boolean isRandomlyGenerated) {
        this.name = name;
        this.range = range;
        this.isRandomlyGenerated = isRandomlyGenerated;
    }
}

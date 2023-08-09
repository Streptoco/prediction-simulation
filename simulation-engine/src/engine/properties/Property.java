package engine.properties;

// TODO: make interface

import engine.properties.api.AbstractProperty;

public class Property extends AbstractProperty {
    private String name;
    private int rangeFrom;
    private int rangeTo;
    private boolean isRandomlyGenerated;

    public Property(String name, int rangeFrom, int rangeTo, boolean isRandomlyGenerated) {
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomlyGenerated = isRandomlyGenerated;
    }


}

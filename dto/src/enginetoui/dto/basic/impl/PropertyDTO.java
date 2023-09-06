package enginetoui.dto.basic.impl;

import engine.action.expression.ReturnType;

public class PropertyDTO {
    public final String name;
    public final engine.action.expression.ReturnType type;
    public final double from;
    public final double to;
    public final boolean isRandomlyGenerated;

    public PropertyDTO(String name, engine.action.expression.ReturnType type, double from, double to, boolean isRandomlyGenerated) {
        this.name = name;
        this.type = type;
        this.from = from;
        this.to = to;
        this.isRandomlyGenerated = isRandomlyGenerated;
    }

    public String getName() {
        return name;
    }

    public ReturnType getType() {
        return type;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public boolean isRandomlyGenerated() {
        return isRandomlyGenerated;
    }
}

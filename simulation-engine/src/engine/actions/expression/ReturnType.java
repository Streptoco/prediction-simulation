package engine.actions.expression;

import engine.properties.api.PropertyType;

public enum ReturnType {
    INT,
    BOOLEAN,
    DECIMAL,
    STRING;

    public static ReturnType convert(String name) {
        if(name.equalsIgnoreCase("int")) {
            return INT;
        } else if(name.equalsIgnoreCase("decimal")) {
            return DECIMAL;
        } else if (name.equalsIgnoreCase("string")) {
            return STRING;
        } else if(name.equalsIgnoreCase("boolean")) {
            return BOOLEAN;
        } else {
            throw new RuntimeException(); //TODO: fix this
        }
    }
}

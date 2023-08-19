package engine.action.expression;

public enum ReturnType {
    INT,
    BOOLEAN,
    DECIMAL,
    STRING;

    public static ReturnType convert(String name) {
        if(name.equalsIgnoreCase("decimal")) {
            return INT;
        } else if(name.equalsIgnoreCase("float")) {
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

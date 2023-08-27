package engine.action.api;

public enum ActionType {
    INCREASE,
    DECREASE,
    CALCULATION,
    CONDITION,
    SET,
    KILL,
    REPLACE,
    PROXIMITY;


    public static ActionType convert(String name) {
        if (name.equalsIgnoreCase("increase")) {
            return INCREASE;
        } else if (name.equalsIgnoreCase("decrease")) {
            return DECREASE;
        } else if (name.equalsIgnoreCase("calculation")) {
            return CALCULATION;
        } else if (name.equalsIgnoreCase("condition")) {
            return CONDITION;
        } else if (name.equalsIgnoreCase("kill")) {
            return KILL;
        } else if (name.equalsIgnoreCase("set")) {
            return SET;
        } else if (name.equalsIgnoreCase("replace")) {
            return REPLACE;
        } else if (name.equalsIgnoreCase("proximity")) {
            return PROXIMITY;
        } else {
            throw new RuntimeException("Unrecognized action type");
        }
    }
}

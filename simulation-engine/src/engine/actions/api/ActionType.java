package engine.actions.api;

import engine.actions.expression.ReturnType;

public enum ActionType {
    INCREASE,
    DECREASE,
    CALCULATION,
    SET,
    KILL,
    CONDITION;
    public static ActionType convert(String name) {
        if(name.equalsIgnoreCase("increase")) {
            return INCREASE;
        } else if(name.equalsIgnoreCase("decrease")) {
            return DECREASE;
        } else if (name.equalsIgnoreCase("calculation")) {
            return CALCULATION;
        } else if(name.equalsIgnoreCase("condition")) {
            return CONDITION;
        }
        else if (name.equalsIgnoreCase("kill")) {
            return KILL;

        }
        else if (name.equalsIgnoreCase("set")) {
            return SET;
        }
        else {
            throw new RuntimeException(); //TODO: fix this
        }
    }
}

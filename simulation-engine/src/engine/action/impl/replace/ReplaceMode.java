package engine.action.impl.replace;

import engine.action.api.ActionType;

public enum ReplaceMode {
    SCRATCH,
    DERIVED;

    public static ReplaceMode convert(String name) {
        if (name.equalsIgnoreCase("scratch")) {
            return SCRATCH;
        } else if (name.equalsIgnoreCase("derived")) {
            return DERIVED;
        }  else {
            throw new RuntimeException("Unrecognized action type");
        }
    }
}

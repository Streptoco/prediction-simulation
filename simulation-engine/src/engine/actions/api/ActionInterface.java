package engine.actions.api;

import engine.EntityDefinition;

public interface ActionInterface {
    public void invoke(Context context);
    ActionType getActionType();
    EntityDefinition getContextEntity();
}

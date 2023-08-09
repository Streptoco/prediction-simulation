package engine.actions.api;

import engine.entity.EntityDefinition;

import engine.context.Context;

public interface ActionInterface {
    public void invoke(Context context);
    ActionType getActionType();
    EntityDefinition getContextEntity();
}

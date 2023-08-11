package engine.actions.api;

import engine.entity.impl.EntityDefinition;

import engine.context.api.Context;

public interface ActionInterface {
    public void invoke(Context context);
    ActionType getActionType();
    EntityDefinition getContextEntity();
}

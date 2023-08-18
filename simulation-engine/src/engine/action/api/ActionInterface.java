package engine.action.api;

import engine.context.api.Context;

public interface ActionInterface {
    public void invoke(Context context);
    ActionType getActionType();
}

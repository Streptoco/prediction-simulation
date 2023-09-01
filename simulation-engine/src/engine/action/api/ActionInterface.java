package engine.action.api;

import engine.context.api.Context;

public interface ActionInterface {
    void invoke(Context context);

    ActionType getActionType();

    String getPropertyName();
    String getEntityOfTheAction();
}

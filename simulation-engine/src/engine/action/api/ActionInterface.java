package engine.action.api;

import engine.action.impl.condition.impl.Condition;
import engine.context.api.Context;

public interface ActionInterface {
    void invoke(Context context);

    ActionType getActionType();

    String getPropertyName();
    String getEntityOfTheAction();
    void addSecondEntity(String entityName, String count, Condition condition);
    boolean haveSecondaryEntity();
    String getSecondEntityName();
}

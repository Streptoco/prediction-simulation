package engine.action.api;

import engine.entity.impl.EntityInstance;

import java.util.List;

public abstract class AbstractAction implements ActionInterface {

    private final ActionType actionType;
    private final String entityOfTheAction;
    private List<EntityInstance> secondaryEntityList = null;


    protected AbstractAction(ActionType actionType, String entityName) {

        this.actionType = actionType;
        this.entityOfTheAction = entityName;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String getEntityOfTheAction() {
        return entityOfTheAction;
    }
}

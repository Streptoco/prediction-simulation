package engine.actions.api;

import engine.entity.impl.EntityDefinition;

public abstract class AbstractAction implements ActionInterface {

    private final ActionType actionType;

    protected AbstractAction(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }
}

package engine.action.api;

public abstract class AbstractAction implements ActionInterface {

    private final ActionType actionType;
    private final String entityOfTheAction;


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

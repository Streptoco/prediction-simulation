package engine.action.api;

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

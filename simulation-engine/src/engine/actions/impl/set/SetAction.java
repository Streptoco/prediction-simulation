package engine.actions.impl.set;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionType;
import engine.context.api.Context;

public class SetAction extends AbstractAction {
    public SetAction(ActionType actionType) {
        super(ActionType.SET);
    }

    @Override
    public void invoke(Context context) {

    }
}

package engine.action.impl.kill;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;

import java.util.List;

public class KillAction extends AbstractAction {
    public KillAction() {
        super(ActionType.KILL);
    }
    @Override
    public void invoke(Context context) {
        context.removeEntity();
    }

    @Override
    public String getPropertyName() {
        return "";
    }
}

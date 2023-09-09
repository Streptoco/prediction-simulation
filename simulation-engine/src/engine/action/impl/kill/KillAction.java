package engine.action.impl.kill;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.context.api.Context;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KillAction extends AbstractAction {
    public KillAction(String actionEntity) {
        super(ActionType.KILL, actionEntity);
    }
    @Override
    public void invoke(Context context) {
        //context.getInstance(this.getEntityOfTheAction()).setDead();
        //You will always kill the primary entity is case of kill action
        context.getPrimaryEntityInstance().setDead();
        context.getGrid().removeFromGrid(context.getPrimaryEntityInstance().getPosition());

        EntityInstance entityToKill = context.getPrimaryEntityInstance();
//        System.out.println("\t\tKilling: " + entityToKill.getId() + "" + entityToKill.getEntityName().charAt(0) + " Place on grid: (" + entityToKill.getPosition().getRow() +
//                "," + entityToKill.getPosition().getCol() + ")");
    }

    @Override
    public String getPropertyName() {
        return "";
    }
}

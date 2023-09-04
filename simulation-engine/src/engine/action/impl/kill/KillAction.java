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
    List<EntityInstance> entitiesToKill;
    public KillAction(String actionEntity) {
        super(ActionType.KILL, actionEntity);
        this.entitiesToKill = new ArrayList<>();
    }
    @Override
    public void invoke(Context context) {
        context.getInstance(this.getEntityOfTheAction()).setDead();
        this.entitiesToKill.add(context.getInstance(this.getEntityOfTheAction()));
        context.getGrid().removeFromGrid(context.getInstance(this.getEntityOfTheAction()).getPosition());
    }

    public void removeSpecifiedEntities(List<EntityInstance> allInstances) {
        for (Iterator<EntityInstance> it = allInstances.iterator(); it.hasNext();) {
            for(EntityInstance entity : this.entitiesToKill) {
                if(it.next().getId() == entity.getId() && it.next().getEntityName().equalsIgnoreCase(entity.getEntityName())) {
                    it.remove();
                    break;
                }
            }
        }
    }

    @Override
    public String getPropertyName() {
        return "";
    }
}

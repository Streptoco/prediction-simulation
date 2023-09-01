package engine.action.impl.replace;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.exception.NoEntityToKillException;

import java.util.Iterator;
import java.util.List;

public class ReplaceAction extends AbstractAction {

    private String entityToCreate;
    private EntityInstance entityToKill;
    private ReplaceMode mode;

    public ReplaceAction(ActionType actionType, ReplaceMode mode) {
        super(actionType);
        this.entityToCreate = null;
        this.mode = mode;
    }

    @Override
    public void invoke(Context context) {
        if (this.entityToCreate == null) {
            throw new NoEntityToKillException(context.getPrimaryEntityInstance().getEntityName());
        }
        switch (mode) {
            case SCRATCH:
                //DO SOMETHING
                break;
            case DERIVED:
                //DO ANOTHER THING
                break;
        }
        this.entityToKill = context.getPrimaryEntityInstance();

    }

    public void setEntityToCreate(String entityToCreate) {
        this.entityToCreate = entityToCreate;
    }

    private void KillEntity(Context context) {
        List<EntityInstance> entityInstances = context.getInstancesList();
//        for(Iterator<EntityInstance> it = entityInstances.iterator(); it.hasNext()) {
//            if(it.next().getId() == this.entityToKill.getId()) {
//                it.remove();
//            }
//        }
        entityInstances.removeIf(entityInstance -> entityInstance.getId() == this.entityToKill.getId());
    }

    @Override
    public String getPropertyName() {
        return null;
    }
}

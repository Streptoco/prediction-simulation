package engine.action.impl.replace;

import engine.action.api.AbstractAction;
import engine.action.api.ActionType;
import engine.action.impl.kill.KillAction;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.exception.NoEntityToKillException;
import engine.grid.impl.Sack;
import engine.property.api.PropertyInterface;

import java.util.Iterator;
import java.util.List;

public class ReplaceAction extends AbstractAction {

    private String entityToCreate;
    private EntityInstance entityToKill;
    private ReplaceMode mode;

    public ReplaceAction(ReplaceMode mode, String actionEntity, String entityToCreate) {
        super(ActionType.REPLACE, actionEntity);
        this.entityToCreate = entityToCreate;
        this.mode = mode;
    }

    @Override
    public void invoke(Context context) {
        if (this.entityToCreate == null) {
            throw new NoEntityToKillException(context.getPrimaryEntityInstance().getEntityName());
        }

        entityToKill = context.getPrimaryEntityInstance();
        KillAction killAction = new KillAction(entityToKill.getEntityName());
        EntityDefinition newEntityDefinition = context.getManager().get(entityToCreate).getEntityDefinition();
        EntityInstance newEntityInstance = context.getManager().get(entityToCreate).create(newEntityDefinition);
        switch (mode) {
            case SCRATCH:
                killAction.invoke(context);
                context.getGrid().addSackToGrid(new Sack(newEntityInstance));
                break;
            case DERIVED:
                newEntityInstance.setPosition(entityToKill.getPosition());
                for (PropertyInterface propertyToCreate : newEntityInstance.getProps()) {
                    for (PropertyInterface propertyToKill : entityToKill.getProps()) {
                        if (propertyToCreate.getName().equalsIgnoreCase(propertyToKill.getName())) {
                            if (propertyToCreate.getPropertyType().equals(propertyToKill.getPropertyType())) {
                                propertyToCreate.setPropertyValue(propertyToKill.getValue(), context.getCurrentTick());
                            }
                        }
                    }
                }
                System.out.println("\tPerforming the action: " + getActionType());
                killAction.invoke(context);
                System.out.println("\t\t" + "Creating: " + newEntityInstance.getId() + "" + newEntityInstance.getEntityName().charAt(0) + " Place on grid: (" + newEntityInstance.getPosition().getRow() + "," + newEntityInstance.getPosition().getCol() + ")");
                context.getGrid().addSackToGrid(new Sack(newEntityInstance), newEntityInstance.getPosition());
                break;
        }
    }

    @Override
    public String getPropertyName() {
        return null;
    }
}

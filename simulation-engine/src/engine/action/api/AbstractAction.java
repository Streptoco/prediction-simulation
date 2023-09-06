package engine.action.api;

import engine.action.impl.condition.impl.Condition;
import engine.entity.impl.EntityInstance;

import java.util.List;

public abstract class AbstractAction implements ActionInterface {

    protected final ActionType actionType;
    protected final String entityOfTheAction;
    protected boolean hasSecondaryEntity = false;
    protected SecondaryEntityChooser secondaryEntityChooser = null;
    protected List<EntityInstance> secondaryEntityList = null;


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

    @Override
    public void addSecondEntity(String entityName, String count, Condition condition) {
        this.secondaryEntityChooser = new SecondaryEntityChooser(entityName, count, condition);
        this.hasSecondaryEntity = true;
    }
    @Override
    public boolean haveSecondaryEntity() {
        return hasSecondaryEntity;
    }

    @Override
    public String getSecondEntityName() {
        if(secondaryEntityChooser != null) {
            return secondaryEntityChooser.getSecondEntityName();
        } else {
            return null;
        }
    }

    @Override
    public SecondaryEntityChooser getSecondaryEntityChooser() {
        return secondaryEntityChooser;
    }
}

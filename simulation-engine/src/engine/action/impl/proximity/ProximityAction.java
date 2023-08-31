package engine.action.impl.proximity;

import engine.action.api.AbstractAction;
import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.context.api.Context;
import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.grid.impl.Grid;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProximityAction extends AbstractAction {
    private String sourceEntity;
    private EntityInstance primarySourceEntity;
    private String targetEntity;
    private Expression depth;
    private List<ActionInterface> actionList;
    private Grid grid;

    public ProximityAction(ActionType actionType, String sourceEntity, String targetEntity, Expression depth, List<ActionInterface> actionList) {
        super(actionType);
        this.actionList = actionList;
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.depth = depth;
    }

    @Override
    public void invoke(Context context) {
        primarySourceEntity = context.getPrimaryEntityInstance();
        this.grid = context.getGrid();
        Set<EntityInstance> surroundEntitiesSet = new HashSet<>();
        grid.getAllInstancesAroundMe(primarySourceEntity.getPosition(), primarySourceEntity.getPosition(), depth.getCastedNumber().intValue(), surroundEntitiesSet);
        List<EntityInstance> surroundEntities = new ArrayList<>(surroundEntitiesSet);
        // iterate over the surround entities that the function found
        for(EntityInstance currentEntity : surroundEntities) {
            if(currentEntity.getEntityName().equalsIgnoreCase(targetEntity)) {
                //iterate over the action in the actionList to invoke in the currentEntity
                for(ActionInterface action : actionList) {
                    //The entityInstanceManager in the context that the function hold is the manager of the souce entity
                    //Do we need to send the entityInstanceManager of the target entity, or it doesn't matter in the possible action of the actionsList?
                    Context currentContext = new ContextImpl(currentEntity, null, context.getEnv());
                    action.invoke(currentContext);
                }
            }
        }
        
    }

    @Override
    public String getPropertyName() {
        return null;
    }
}

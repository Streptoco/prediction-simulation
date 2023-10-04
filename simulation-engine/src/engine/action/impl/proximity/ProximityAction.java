package engine.action.impl.proximity;

import engine.action.api.AbstractAction;
import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.context.api.Context;
import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityInstance;
import engine.grid.impl.Grid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProximityAction extends AbstractAction {
    private EntityInstance sourceEntity;
    private String targetEntityName;
    private Expression depth;
    private final List<ActionInterface> actionList;
    private Grid grid;

    public ProximityAction(ActionType actionType, String sourceEntity, String targetEntity, Expression depth, List<ActionInterface> actionList) {
        super(actionType, sourceEntity);
        this.actionList = actionList;
        this.targetEntityName = targetEntity;
        this.depth = depth;
    }

    @Override
    public void invoke(Context context) {
        sourceEntity = context.getInstance(this.getEntityOfTheAction());
        this.grid = context.getGrid();
        Set<EntityInstance> surroundEntitiesSet = new HashSet<>();
        this.depth.evaluateExpression(context);
        grid.getAllInstancesAroundMe(sourceEntity.getPosition(), sourceEntity.getPosition(), ((Double) depth.getValue()).intValue(), surroundEntitiesSet);
        List<EntityInstance> surroundEntities = new ArrayList<>(surroundEntitiesSet);
//        System.out.print("\tPerforming the action: " + getActionType());
//        System.out.print(" the entities around: " + sourceEntity.getId() + sourceEntity.getEntityName().charAt(0) + " are: ");
//        surroundEntities.forEach(currentEntity -> System.out.print(currentEntity.getId() + "" + currentEntity.getEntityName().charAt(0) + ", "));
//        System.out.print("\n");
        // iterate over the surround entities that the function found
        for (EntityInstance currentEntity : surroundEntities) {
            if (currentEntity.getEntityName().equalsIgnoreCase(targetEntityName)) {
                //iterate over the action in the actionList to invoke in the currentEntity
                if (sourceEntity.isAlive() && currentEntity.isAlive()) {
                    for (ActionInterface action : actionList) {
                        Context currentContext = new ContextImpl(sourceEntity, currentEntity, context.getManager(), context.getEnv(), context.getCurrentTick());
                        currentContext.setGrid(grid);
                        action.invoke(currentContext);
                    }
                }
            }
        }

    }
    public Expression getDepth() {
        return depth;
    }

    @Override
    public String getPropertyName() {
        return null;
    }
}

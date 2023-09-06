package engine.action.api;

import engine.action.expression.ReturnType;
import engine.action.impl.condition.impl.Condition;
import engine.context.api.Context;
import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.exception.XMLVariableTypeException;

import java.util.ArrayList;
import java.util.List;

public class SecondaryEntityChooser {

    private String secondEntityName;
    private boolean allEntities;
    private Condition condition;
    private int count;

    public SecondaryEntityChooser(String entityName, String count, Condition condition) {
        this.secondEntityName = entityName;
        if (count.equalsIgnoreCase("all")) {
            allEntities = true;
            condition = null;
        } else {
            this.condition = condition;
            try {
                this.count = Integer.parseInt(count);
            } catch (NumberFormatException e) {
                throw new XMLVariableTypeException("", count, ReturnType.INT);
            }
        }

    }

    public String getSecondEntityName() {
        return secondEntityName;
    }

    public List<EntityInstance> secondaryEntitiesListBuilder(Context context, List<EntityInstance> entitiesList) {
        if (allEntities) {
            return entitiesList;
        } else {
            int currentAmount = 0;
            List<EntityInstance> resultList = new ArrayList<>();
            for (EntityInstance entity : entitiesList) {
                ContextImpl currentContext = new ContextImpl(entity, context.getManager(), context.getEnv(), context.getCurrentTick());
                if (condition.evaluate(currentContext)) {
                    resultList.add(entity);
                    ++currentAmount;
                }
                if (currentAmount == this.count) {
                    break;
                }

            }
            return resultList;
        }

    }
}


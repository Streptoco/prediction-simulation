package engine.action.api;

import engine.action.impl.condition.impl.Condition;
import engine.context.api.Context;
import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;

import java.util.ArrayList;
import java.util.List;

public class SecondaryEntityChooser {

    boolean allEntities;
    Condition condition;
    int count;

    public SecondaryEntityChooser(String count, Condition condition) {
        if (count.equalsIgnoreCase("all")) {
            allEntities = true;
            condition = null;
        } else {
            this.condition = condition;
            this.count = Integer.parseInt(count);
        }

    }

    public List<EntityInstance> secondaryEntitiesListBuilder(Context context, List<EntityInstance> entitiesList) {
        if (allEntities) {
            return entitiesList;
        } else {
            int currentAmount = 0;
            List<EntityInstance> resultList = new ArrayList<>();
            for (EntityInstance entity : entitiesList) {
                ContextImpl currentContext = new ContextImpl(entity, context.getManager(), context.getEnv());
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


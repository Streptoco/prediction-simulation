package engine.worldbuilder.factory;

import engine.actions.api.ActionInterface;
import engine.actions.api.ActionType;
import engine.actions.impl.increasedecrease.IncreaseDecreaseAction;
import engine.entity.impl.EntityDefinition;
import engine.worldbuilder.prdobjects.PRDAction;

public class ActionFactory {
    public static ActionInterface ActionFactory(PRDAction prdAction) {
        ActionType actionType = ActionType.convert(prdAction.getType());
        switch (actionType) {
            case INCREASE:
            case DECREASE:
                ActionInterface increaseDecreaseAction = new IncreaseDecreaseAction(prdAction.)
        }

    }
}

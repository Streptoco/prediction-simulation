package enginetoui.dto.basic.impl;

import engine.action.api.ActionInterface;
import engine.action.impl.calculation.CalculationAction;
import engine.action.impl.condition.impl.ConditionAction;
import engine.action.impl.condition.impl.MultipleConditionAction;
import engine.action.impl.increasedecrease.IncreaseDecreaseAction;
import engine.action.impl.proximity.ProximityAction;
import enginetoui.dto.basic.api.AbstractActionDTO;

import java.util.ArrayList;
import java.util.List;

public class RuleDTO {
    public final String name;
    public final int tick;
    public final double probability;
    public final int numOfActions;
    public final List<AbstractActionDTO> actionNames;

    public RuleDTO(String name, int tick, double probability, int numOfActions, List<ActionInterface> actions) {
        this.name = name;
        this.tick = tick;
        this.probability = probability;
        this.numOfActions = numOfActions;
        this.actionNames = new ArrayList<>();
        if (actions != null) {
            for(ActionInterface action : actions) {
                switch (action.getActionType()) {
                    case INCREASE:
                    case DECREASE:
                        IncreaseDecreaseAction actionHandlerIncrease = (IncreaseDecreaseAction) action;
                        actionNames.add(new IncreaseActionDTO(actionHandlerIncrease.getActionType(), actionHandlerIncrease.getPropertyName(),actionHandlerIncrease.getExpression()));
                        break;
                    case CALCULATION:
                        CalculationAction actionHandlerCalculation = (CalculationAction) action;
                        actionNames.add(new CalculationActionDTO(actionHandlerCalculation.getActionType(), actionHandlerCalculation.getPropertyName(),
                                actionHandlerCalculation.getFirstExpression(), actionHandlerCalculation.getSecondArgument(), actionHandlerCalculation.getCalculationType()));
                        break;
                    case CONDITION:
                        if (action.getClass().equals(MultipleConditionAction.class)) {
                            MultipleConditionAction actionHandlerMultiple = (MultipleConditionAction) action;
                            actionNames.add(new ConditionActionDTO(action.getActionType(), actionHandlerMultiple.getConditionsSize(), actionHandlerMultiple.getOperator(), null,null));
                        } else {
                            ConditionAction actionHandleSingle = (ConditionAction) action;
                            actionNames.add(new ConditionActionDTO(action.getActionType(), 0, actionHandleSingle.getValueOperator(), actionHandleSingle.getProperty(),
                                    actionHandleSingle.getValueExpression()));
                        }
                        break;
                    case PROXIMITY:
                        ProximityAction proximityAction = (ProximityAction) action;
                        actionNames.add(new ProximityActionDTO(action.getActionType(),proximityAction.getEntityOfTheAction(), proximityAction.getSecondEntityName(), proximityAction.getDepth().getName()));

                }
            }
        }
    }

    public String getName() {
        return this.name;
    }
}

package engine.actions.impl.condition.impl;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionInterface;
import engine.actions.api.ActionType;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleConditionAction extends AbstractAction {

    private List<ConditionAction> conditionActionList;
    private LogicalOperatorForSingularity logicalOperator;
    private List<ActionInterface> thenAction;
    private List<ActionInterface> elseAction;
    private boolean wasInvoked;
    public MultipleConditionAction(List<ActionInterface> thenAction, List<ActionInterface> elseAction,
                                   String logicalOperator, ConditionAction... conditions) {
        super(ActionType.CONDITION);
        this.logicalOperator = logicalOperator.equalsIgnoreCase("and") ? LogicalOperatorForSingularity.AND : logicalOperator.equalsIgnoreCase("or") ? LogicalOperatorForSingularity.OR : null;
        this.thenAction = thenAction;
        if (elseAction != null) {
            this.elseAction = elseAction;
        }
        conditionActionList = Arrays.asList(conditions);
    }

    public void invoke(Context context) {
        this.wasInvoked = false;
        switch (logicalOperator) {
            case OR:
                for (ConditionAction condition : conditionActionList) {
                    if (condition.getIsConditionHappening(context)) {
                        for (ActionInterface action : thenAction) {
                            action.invoke(context);
                        }
                        wasInvoked = true;
                        break;
                    }
                }
                if (!wasInvoked && (elseAction != null)) {
                    for (ActionInterface action : elseAction) {
                        action.invoke(context);
                    }
                }
                break;
            case AND:
                for (ConditionAction condition : conditionActionList) {
                    if (!condition.getIsConditionHappening(context)) {
                        if(elseAction != null) {
                            for (ActionInterface action : elseAction) {
                                action.invoke(context);
                            }
                        }
                        wasInvoked = true;
                        break;
                    }
                }
                if (!wasInvoked && (thenAction != null)) {
                    for (ActionInterface action : thenAction) {
                        action.invoke(context);
                    }
                }
                break;
        }
    }
}

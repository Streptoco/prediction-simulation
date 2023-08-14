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
    private ActionInterface thenAction;
    private ActionInterface elseAction;
    private boolean wasInvoked;
    public MultipleConditionAction(EntityDefinition entityDefinition, ActionInterface thenAction, ActionInterface elseAction,
                                   LogicalOperatorForSingularity logicalOperator, ConditionAction... conditions) {
        super(ActionType.CONDITION, entityDefinition);
        this.logicalOperator = logicalOperator;
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
                        thenAction.invoke(context);
                        wasInvoked = true;
                        break;
                    }
                }
                if (!wasInvoked && (elseAction != null)) {
                    elseAction.invoke(context);
                }
                break;
            case AND:
                for (ConditionAction condition : conditionActionList) {
                    if (!condition.getIsConditionHappening(context)) {
                        if(elseAction != null) {
                            elseAction.invoke(context);
                        }
                        wasInvoked = true;
                        break;
                    }
                }
                if (!wasInvoked && (thenAction != null)) {
                    thenAction.invoke(context);
                }
                break;
        }
    }
}

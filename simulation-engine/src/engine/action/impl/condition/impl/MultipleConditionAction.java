package engine.action.impl.condition.impl;

import engine.action.api.AbstractAction;
import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.context.api.Context;

import java.util.List;

public class MultipleConditionAction extends AbstractAction {

    private List<Condition> conditionList;
    private LogicalOperatorForSingularity logicalOperator;
    private List<ActionInterface> thenAction;
    private List<ActionInterface> elseAction;
    private boolean wasInvoked;
    public MultipleConditionAction(List<ActionInterface> thenAction, List<ActionInterface> elseAction,
                                   String logicalOperator, List<Condition> conditions, String actionEntity) {
        super(ActionType.CONDITION, actionEntity);
        this.logicalOperator = logicalOperator.equalsIgnoreCase("and") ? LogicalOperatorForSingularity.AND : logicalOperator.equalsIgnoreCase("or") ? LogicalOperatorForSingularity.OR : null;
        this.thenAction = thenAction;
        if (elseAction != null) {
            this.elseAction = elseAction;
        }
        conditionList = conditions;
    }

    public void invoke(Context context) {
        this.wasInvoked = false;
        switch (logicalOperator) {
            case OR:
                for (Condition condition : conditionList) {
                    if (condition.evaluate(context)) {
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
                for (Condition condition : conditionList) {
                    if (!condition.evaluate(context)) {
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

    @Override
    public String getPropertyName() {
        return "";
    }
}

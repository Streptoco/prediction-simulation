package engine.action.impl.condition.impl;

import engine.context.api.Context;

import java.util.List;

public class MultipleCondition implements Condition {
    List<Condition> conditionList;
    LogicalOperatorForSingularity logicalOperator;
    public MultipleCondition(String logicalOperator, List<Condition> conditions) {
        this.logicalOperator = logicalOperator.equalsIgnoreCase("and") ? LogicalOperatorForSingularity.AND : logicalOperator.equalsIgnoreCase("or") ? LogicalOperatorForSingularity.OR : null;
        this.conditionList = conditions;
    }

    @Override
    public boolean evaluate(Context context) {
        if (logicalOperator.equals(LogicalOperatorForSingularity.AND)) {
            for (Condition condition : conditionList) {
                if (!condition.evaluate(context)) {
                    return false;
                }
            }
            return true;
        }
        else if (logicalOperator.equals(LogicalOperatorForSingularity.OR)) {
            for (Condition condition : conditionList) {
                if (condition.evaluate(context)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}

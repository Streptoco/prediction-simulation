package engine.actions.impl.condition.impl;

import engine.actions.expression.Expression;

import java.util.Arrays;
import java.util.List;

public class MultipleCondition implements Condition {
    List<Condition> conditionList;
    LogicalOperatorForSingularity logicalOperator;
    public MultipleCondition(String logicalOperator, Condition... conditions) {
        this.logicalOperator = logicalOperator.equalsIgnoreCase("and") ? LogicalOperatorForSingularity.AND : logicalOperator.equalsIgnoreCase("or") ? LogicalOperatorForSingularity.OR : null;
        this.conditionList = Arrays.asList(conditions);
    }
}

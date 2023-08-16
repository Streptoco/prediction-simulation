package engine.actions.impl.condition.impl;

import engine.actions.expression.Expression;

import java.util.List;

public class MultipleCondition implements Condition {
    List<Condition> conditionList;
    LogicalOperatorForSingularity operator;
    public MultipleCondition(String operator, Condition... conditions) {

    }
}

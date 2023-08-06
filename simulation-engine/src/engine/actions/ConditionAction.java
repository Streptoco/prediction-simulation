package engine.actions;

import engine.Entity;

enum Singularity {
    SINGLE,
    MULTIPLE
}

enum LogicalOperator {
    AND,
    OR
}

public class ConditionAction extends Action {
    Singularity singularity;
    LogicalOperator operator;
    Action thenAction;
    Action elseAction;

    public ConditionAction(Entity entity, byte singularity, byte operator) {
        super(entity);
        this.singularity = Singularity.values()[singularity];
        this.operator = LogicalOperator.values()[operator];
    }
}

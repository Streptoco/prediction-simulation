package engine.actions.condition;

import engine.Entity;
import engine.actions.Action;
import engine.actions.Expression;
import engine.properties.Property;

import java.util.ArrayList;

enum Singularity {
    SINGLE,
    MULTIPLE
}

enum LogicalOperatorForSingularity {
    AND,
    OR
}

public class ConditionAction extends Action {

    Property propertyInstance;
    Singularity singularity;
    LogicalOperatorForSingularity operator;
    ArrayList<Action> actionList;
    Action thenAction;
    Action elseAction;

    Expression value;

    public ConditionAction(Entity entity, byte singularity, byte operator, String propertyName, Action thenAction, Action elseAction) {
        super(entity);
        this.singularity = Singularity.values()[singularity];
        this.operator = LogicalOperatorForSingularity.values()[operator];
        this.propertyInstance = super.getEntity().getPropertyByName(propertyName);
        this.thenAction = thenAction;
        this.elseAction = elseAction;
        if (this.singularity.equals(Singularity.SINGLE)) {
            // TODO: fill what happens if it's a singular occurrence.

        }
    }
}

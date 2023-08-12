package engine.actions.impl.condition.impl;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionInterface;
import engine.actions.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;
import engine.actions.api.ActionType;
import engine.actions.expression.Expression;
import engine.properties.api.PropertyInterface;
import java.util.ArrayList;

public class ConditionAction extends AbstractAction {

    PropertyInterface propertyInstance;
    Singularity singularity;
    LogicalOperatorForSingularity operator;
    ArrayList<AbstractAction> actionList;
    ActionInterface thenAction;
    ActionInterface elseAction;
    Expression valueExpression;
    String valueOperator;
    String propertyName;
    public ConditionAction(EntityDefinition entityDefinition, Singularity singularity, LogicalOperatorForSingularity operator, String propertyName, ActionInterface thenAction, ActionInterface elseAction, Expression valueExpression,
                           String valueOperator) {
        super(ActionType.CONDITION, entityDefinition);
        this.singularity = singularity;
        this.operator = operator;
        this.thenAction = thenAction;
        this.elseAction = elseAction;
        this.valueExpression = valueExpression;
        this.valueOperator = valueOperator;
        this.propertyName = propertyName;
    }

    public ConditionAction(EntityDefinition entityDefinition, String propertyName, String operator, Expression valueExpression, ActionInterface thenAction, ActionInterface elseAction) {
        super(ActionType.CONDITION, entityDefinition);
        this.propertyName = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
        this.thenAction = thenAction;
        this.elseAction = elseAction;
    }

    public void invoke(Context context) {
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName); // TODO: need to pass the string
        PropertyExpressionEvaluation result = propertyInstance.evaluate(valueExpression);

        if (EvaluateExpression(result)) {
            thenAction.invoke(context);
        }
    }

    public boolean EvaluateExpression(PropertyExpressionEvaluation result) {
        if (valueOperator.equals("=")) {
            return result.isEqual();
        }
        else if (valueOperator.equals("!=")) {
            return !result.isEqual();
        }
        else if (valueOperator.equalsIgnoreCase("bt")) {
            return result.isGreater();
        }
        else {
            return !result.isGreater();
        }
    }

    private void MultipleEvaluation() {
        // TODO: handle
    }
}

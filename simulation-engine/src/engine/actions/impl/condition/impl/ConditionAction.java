package engine.actions.impl.condition.impl;

import engine.actions.api.AbstractAction;
import engine.actions.api.ActionInterface;
import engine.actions.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.entity.impl.EntityDefinition;
import engine.actions.api.ActionType;
import engine.actions.impl.calculation.CalculationAction;
import engine.actions.expression.Expression;
import engine.actions.impl.increasedecrease.IncreaseDecreaseAction;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

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

    public void invoke(Context context) {
        if (this.singularity.equals(Singularity.SINGLE)) {
            // TODO: fill what happens if it's a singular occurrence.
            // TODO: cut the fat
            // TODO: figure out a way to cast the object returning from expression (would be ugly as well)
        }

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

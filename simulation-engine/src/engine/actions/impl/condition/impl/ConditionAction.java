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

    private PropertyInterface propertyInstance;
    private ActionInterface thenAction;
    private ActionInterface elseAction;
    private Expression valueExpression;
    private String valueOperator;
    private String propertyName;
    private boolean isConditionHappening;

    public ConditionAction(EntityDefinition entityDefinition, String propertyName, String operator, Expression valueExpression, ActionInterface thenAction, ActionInterface elseAction) {
        super(ActionType.CONDITION, entityDefinition);
        this.propertyName = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
        this.thenAction = thenAction;
        this.elseAction = elseAction;
    }

    public ConditionAction(EntityDefinition entityDefinition, String propertyName, String operator, Expression valueExpression, ActionInterface thenAction) {
        super(ActionType.CONDITION, entityDefinition);
        this.propertyName = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
        this.thenAction = thenAction;
        this.elseAction = null;
    }

    public ConditionAction(EntityDefinition entityDefinition, String propertyName, String operator, Expression valueExpression, Context context) {
        super(ActionType.CONDITION, entityDefinition);
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        isConditionHappening = EvaluateExpression(propertyInstance.evaluate(valueExpression));
    }

    public void invoke(Context context) {
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        PropertyExpressionEvaluation result = propertyInstance.evaluate(valueExpression);

        if (EvaluateExpression(result)) {
            thenAction.invoke(context);
        } else {
            if (elseAction != null) {
                elseAction.invoke(context);
            }
        }
    }

    public boolean EvaluateExpression(PropertyExpressionEvaluation result) {
        if (valueOperator.equals("=")) {
            return result.isEqual();
        } else if (valueOperator.equals("!=")) {
            return !result.isEqual();
        } else if (valueOperator.equalsIgnoreCase("bt")) {
            return result.isGreater();
        } else {
            return !result.isGreater();
        }
    }

    private void MultipleEvaluation() {
        // TODO: handle
    }

    public boolean getIsConditionHappening() { return isConditionHappening; }
}

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
import java.util.List;

public class ConditionAction extends AbstractAction {

    private PropertyInterface propertyInstance;
    private List<ActionInterface> thenAction;
    private List<ActionInterface> elseAction;
    private Expression valueExpression;
    private String valueOperator;
    private String propertyName;
    private boolean isConditionHappening;

    public ConditionAction(String propertyName, String operator, Expression valueExpression, List<ActionInterface> thenAction, List<ActionInterface> elseAction) {
        super(ActionType.CONDITION);
        this.propertyName = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
        this.thenAction = thenAction;
        if(elseAction.size() != 0) {
            this.elseAction = elseAction;
        } else {
            this.elseAction = null;
        }
    }

    public ConditionAction(String propertyName, String operator, Expression valueExpression) {
        super(ActionType.CONDITION);
        this.valueExpression = valueExpression;
        this.valueOperator = operator;
        this.propertyName = propertyName;
    }

    public void invoke(Context context) {
        propertyInstance = context.getPrimaryEntityInstance().getPropertyByName(propertyName);
        PropertyExpressionEvaluation result = propertyInstance.evaluate(valueExpression);

        if (EvaluateExpression(result)) {
            for (ActionInterface action : thenAction) {
                action.invoke(context);
            }
        } else {
            if (elseAction != null) {
                for (ActionInterface action: elseAction) {
                    action.invoke(context);
                }
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
}

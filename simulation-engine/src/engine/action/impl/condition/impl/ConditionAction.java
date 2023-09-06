package engine.action.impl.condition.impl;

import engine.action.api.AbstractAction;
import engine.action.api.ActionInterface;
import engine.action.impl.condition.api.PropertyExpressionEvaluation;
import engine.context.api.Context;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.property.api.PropertyInterface;
import engine.property.impl.BooleanProperty;
import engine.property.impl.DecimalProperty;
import engine.property.impl.IntProperty;
import engine.property.impl.StringProperty;

import java.util.List;

public class ConditionAction extends AbstractAction {

    private PropertyInterface propertyInstance;
    private List<ActionInterface> thenAction;
    private List<ActionInterface> elseAction;
    private Expression valueExpression;
    private String valueOperator;

    //TODO: need to make the property as expression
    private Expression property;
    private boolean isConditionHappening;

    public ConditionAction(Expression propertyName, String operator, Expression valueExpression, List<ActionInterface> thenAction, List<ActionInterface> elseAction, String actionEntity) {
        super(ActionType.CONDITION, actionEntity);
        this.property = propertyName;
        this.valueOperator = operator;
        this.valueExpression = valueExpression;
        this.thenAction = thenAction;
        if (elseAction.size() != 0) {
            this.elseAction = elseAction;
        } else {
            this.elseAction = null;
        }
    }

    public void invoke(Context context) {
        System.out.println("\tPerforming the action: " + getActionType() + " " + Singularity.SINGLE);
        propertyInstance = context.getInstance(this.getEntityOfTheAction()).getPropertyByName(property.getExpression());
        System.out.print("\t\tChecking if: " + property.getExpression() + "" + this.valueOperator + valueExpression.getExpression() + ": ");
        PropertyExpressionEvaluation result = null;
        if (propertyInstance != null) {
            valueExpression.evaluateExpression(context);
            result = propertyInstance.evaluate(valueExpression);
        } else {
            property.evaluateExpression(context);
            PropertyInterface propertyAsExpression = null;
            switch (property.getReturnType()) {
                case INT:
                    propertyAsExpression = new IntProperty(((Double)property.getValue()).intValue(), property.getExpression(), ((Double)property.getValue()).intValue(), ((Double)property.getValue()).intValue(), false);
                    break;
                case DECIMAL:
                    propertyAsExpression = new DecimalProperty((Double) property.getValue(), property.getExpression(), (Double) property.getValue(), (Double) property.getValue(), false);
                    break;
                case BOOLEAN:
                    propertyAsExpression = new BooleanProperty((boolean) property.getValue(), property.getExpression(), 0, 0, false);
                    break;
                case STRING:
                    propertyAsExpression = new StringProperty((String) property.getValue(), (String) property.getValue(), 0,0, false);
                    break;
            }
            if(propertyAsExpression != null) {
                result = propertyAsExpression.evaluate(valueExpression);
            }
        }

        if (EvaluateExpression(result)) {
            System.out.print("true\n");
            for (ActionInterface action : thenAction) {
                action.invoke(context);
            }
        } else {
            if (elseAction != null) {
                System.out.print("false\n");
                for (ActionInterface action : elseAction) {
                    action.invoke(context);
                }
            }
        }
    }

    @Override
    public String getPropertyName() {
        return property.getExpression();
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

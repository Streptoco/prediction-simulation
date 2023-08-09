package engine.actions.condition;

import engine.entity.EntityDefinition;
import engine.actions.api.ActionType;
import engine.actions.calculation.CalculationAction;
import engine.actions.expression.Expression;
import engine.actions.impl.IncreaseAction;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

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
    Expression byExpression;

    public ConditionAction(EntityDefinition entityDefinition, byte singularity, byte operator, String propertyName, Action thenAction, Action elseAction, Expression byExpression,
                           String valueOperator) {
        super(entityDefinition);
        super.setActionType(ActionType.CONDITION);
        this.singularity = Singularity.values()[singularity];
        this.operator = LogicalOperatorForSingularity.values()[operator];
        this.propertyInstance = super.getEntity().getPropertyByName(propertyName);
        this.thenAction = thenAction;
        this.elseAction = elseAction;
        this.byExpression = byExpression;
        if (this.singularity.equals(Singularity.SINGLE)) {
            // TODO: fill what happens if it's a singular occurrence.
            // TODO: cut the fat
            // TODO: figure out a way to cast the object returning from expression (would be ugly as well)
            if (valueOperator.equals("=")) {
                // TODO: add something that checks if the expression returns a number.
                if (propertyInstance.equals(byExpression.getValue())) {
                    if (thenAction.getActionType().equals(ActionType.INCREASE)) {
                        IncreaseAction resultAction = (IncreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                    else if (thenAction.getActionType().equals(ActionType.DECREASE))  {
                        DecreaseAction resultAction = (DecreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                    else {
                        CalculationAction resultAction = (CalculationAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                }
            }
            else if (valueOperator.equals("!=")) {
                if (!propertyInstance.equals(byExpression.getValue())) {
                    if (thenAction.getActionType().equals(ActionType.INCREASE)) {
                        IncreaseAction resultAction = (IncreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else if (thenAction.getActionType().equals(ActionType.DECREASE)) {
                        DecreaseAction resultAction = (DecreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else {
                        CalculationAction resultAction = (CalculationAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                }
            }
            else if (valueOperator.equals("bt")) {
                if (propertyInstance > byExpression.getValue()) {
                    if (thenAction.getActionType().equals(ActionType.INCREASE)) {
                        IncreaseAction resultAction = (IncreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else if (thenAction.getActionType().equals(ActionType.DECREASE)) {
                        DecreaseAction resultAction = (DecreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else {
                        CalculationAction resultAction = (CalculationAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                }
            }
            else if (valueOperator.equals("lt")) {
                if (propertyInstance < byExpression.getValue()) {
                    if (thenAction.getActionType().equals(ActionType.INCREASE)) {
                        IncreaseAction resultAction = (IncreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else if (thenAction.getActionType().equals(ActionType.DECREASE)) {
                        DecreaseAction resultAction = (DecreaseAction) this.thenAction;
                        resultAction.invokeAction();
                    } else {
                        CalculationAction resultAction = (CalculationAction) this.thenAction;
                        resultAction.invokeAction();
                    }
                }
            }
        }
    }
    private void ParseInformationForEvaluation() {
        if (this.singularity.equals(Singularity.SINGLE)) {
            // TODO: handle single type cast.
            SingularityEvaluation();
        }
        else {
            // TODO: handle multiple
            MultipleEvaluation();
        }
    }

    private void SingularityEvaluation() {
        if (propertyInstance.getClass().equals(IntProperty.class)) {
            IntProperty intProperty = (IntProperty)propertyInstance;
            int intValue = intProperty.getValue();
        }
        else if (propertyInstance.getClass().equals(DecimalProperty.class)) {
            DecimalProperty decimalProperty = (DecimalProperty)propertyInstance;
            double doubleValue = decimalProperty.getValue();
        }
        else {
            // TODO: should string and boolean be part of this?
        }
    }

    private void MultipleEvaluation() {
        // TODO: handle
    }
}

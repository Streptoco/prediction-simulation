package engine.worldbuilder.factory;

import engine.actions.api.ActionInterface;
import engine.actions.api.ActionType;
import engine.actions.expression.Expression;
import engine.actions.impl.calculation.CalculationAction;
import engine.actions.impl.condition.impl.ConditionAction;
import engine.actions.impl.condition.impl.MultipleConditionAction;
import engine.actions.impl.increasedecrease.IncreaseDecreaseAction;
import engine.entity.impl.EntityDefinition;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDCondition;
import engine.worldbuilder.prdobjects.PRDDivide;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static ActionInterface BuildAction(PRDAction prdAction) {
        ActionType actionType = ActionType.convert(prdAction.getType());
        ActionInterface resultAction = null;
        switch (actionType) {
            case INCREASE:
            case DECREASE:
                resultAction = new IncreaseDecreaseAction(prdAction.getProperty(),
                        new Expression(prdAction.getBy()), prdAction.getType());
                break;
            case CALCULATION:
                String calculationType = "";
                String arg1 = "", arg2 = "";
                PRDDivide prdDivide = prdAction.getPRDDivide();
                if (prdAction.getPRDMultiply() == null && prdAction.getPRDMultiply() == null) {
                    // TODO: throw exception
                }
                else if (prdAction.getPRDDivide() == null) {
                    calculationType = "multiply";
                    arg1 = prdAction.getPRDMultiply().getArg1();
                    arg2 = prdAction.getPRDMultiply().getArg2();
                }
                else {
                    calculationType = "divide";
                    arg1 = prdAction.getPRDDivide().getArg1();
                    arg2 = prdAction.getPRDDivide().getArg2();
                }
                resultAction = new CalculationAction(prdAction.getResultProp(), calculationType, new Expression(arg1), new Expression(arg2));
                break;
            case CONDITION:
                List<ActionInterface> thenList = new ArrayList<>();
                List<ActionInterface> elseList = new ArrayList<>();
                List<PRDAction> prdThenList = prdAction.getPRDThen().getPRDAction();
                List<PRDAction> prdElseList = prdAction.getPRDElse() == null ? null : prdAction.getPRDElse().getPRDAction();
                for(PRDAction currentAction : prdThenList) {
                    thenList.add(ActionFactory.BuildAction(currentAction));
                }
                if(prdElseList != null) {
                    for (PRDAction currentAction : prdElseList) {
                        elseList.add(ActionFactory.BuildAction(currentAction));
                    }
                }

                if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
                    resultAction = new ConditionAction(prdAction.getProperty(), prdAction.getPRDCondition().getOperator(),
                            new Expression(prdAction.getPRDCondition().getValue()), thenList, elseList);
                }
                else if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("multiple")) {
                    resultAction = new MultipleConditionAction(thenList,elseList, prdAction.getPRDCondition().getLogical(),
                            ConditionFactory.BuildConditionFromList(prdAction.getPRDCondition().getPRDCondition()));
                }
        }
        return resultAction;
    }
}

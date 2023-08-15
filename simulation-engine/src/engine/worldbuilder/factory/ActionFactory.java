package engine.worldbuilder.factory;

import engine.actions.api.ActionInterface;
import engine.actions.api.ActionType;
import engine.actions.expression.Expression;
import engine.actions.impl.calculation.CalculationAction;
import engine.actions.impl.increasedecrease.IncreaseDecreaseAction;
import engine.entity.impl.EntityDefinition;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDDivide;

import javax.swing.*;

public class ActionFactory {
    public static ActionInterface ActionFactory(PRDAction prdAction) {
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
                resultAction = new CalculationAction(prdAction.getProperty(), calculationType, new Expression(arg1), new Expression(arg2));
                break;
            case CONDITION:

        }

    }
}

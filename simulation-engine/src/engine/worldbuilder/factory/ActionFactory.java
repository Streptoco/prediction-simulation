package engine.worldbuilder.factory;

import engine.action.api.ActionInterface;
import engine.action.api.ActionType;
import engine.action.expression.Expression;
import engine.action.expression.ReturnType;
import engine.action.impl.calculation.CalculationAction;
import engine.action.impl.condition.impl.ConditionAction;
import engine.action.impl.condition.impl.MultipleConditionAction;
import engine.action.impl.increasedecrease.IncreaseDecreaseAction;
import engine.action.impl.kill.KillAction;
import engine.action.impl.set.SetAction;
import engine.exception.XMLVariableTypeException;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDDivide;
import engine.xml.NewXMLReader;

import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static ActionInterface BuildAction(PRDAction prdAction) {
        ActionType actionType = ActionType.convert(prdAction.getType());
        ActionInterface resultAction = null;
        switch (actionType) {
            case INCREASE:
            case DECREASE:
                Expression expression = new Expression(prdAction.getBy());
                expression.FreeValuePositioning();
                resultAction = new IncreaseDecreaseAction(prdAction.getProperty(),
                        expression, prdAction.getType());
                if(!expression.getReturnType().equals(ReturnType.INT) || !expression.getReturnType().equals(ReturnType.DECIMAL)) {
                    String expressionValue = (String) expression.getValue();
                    if(expressionValue.startsWith("environment(")) {
                        String envVariableName = "";
                        int startIndex = expressionValue.indexOf("(");
                        int endIndex = expressionValue.indexOf(")");
                        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                            envVariableName = expressionValue.substring(startIndex + 1, endIndex);
                        }
                        for(PropertyInterface envProperty : NewXMLReader.envVariables) {
                            if(envProperty.getName().equals(envVariableName)) {
                                if(!(envProperty.getPropertyType().equals(ReturnType.INT) || envProperty.getPropertyType().equals(ReturnType.DECIMAL))) {
                                    throw new XMLVariableTypeException("", expression.getReturnType() ,envProperty.getPropertyType());
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    else if(expressionValue.startsWith("random(")) {
                        //TODO: make this work - RONNY
                    }
                }

                break;
            case CALCULATION:
                String calculationType = "";
                String arg1 = "", arg2 = "";
                PRDDivide prdDivide = prdAction.getPRDDivide();
                if (prdAction.getPRDDivide() == null) {
                    calculationType = "multiply";
                    arg1 = prdAction.getPRDMultiply().getArg1();
                    arg2 = prdAction.getPRDMultiply().getArg2();
                } else if (prdAction.getPRDMultiply() == null) {
                    calculationType = "divide";
                    arg1 = prdAction.getPRDDivide().getArg1();
                    arg2 = prdAction.getPRDDivide().getArg2();
                } else {
                    // TODO: throw exception
                }
                resultAction = new CalculationAction(prdAction.getResultProp(), calculationType, new Expression(arg1), new Expression(arg2));
                break;
            case CONDITION:
                List<ActionInterface> thenList = new ArrayList<>();
                List<ActionInterface> elseList = new ArrayList<>();
                List<PRDAction> prdThenList = prdAction.getPRDThen().getPRDAction();
                List<PRDAction> prdElseList = prdAction.getPRDElse() == null ? null : prdAction.getPRDElse().getPRDAction();
                for (PRDAction currentAction : prdThenList) {
                    thenList.add(ActionFactory.BuildAction(currentAction));
                }
                if (prdElseList != null) {
                    for (PRDAction currentAction : prdElseList) {
                        elseList.add(ActionFactory.BuildAction(currentAction));
                    }
                }

                if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("single")) {
                    resultAction = new ConditionAction(prdAction.getPRDCondition().getProperty(), prdAction.getPRDCondition().getOperator(),
                            new Expression(prdAction.getPRDCondition().getValue()), thenList, elseList);
                } else if (prdAction.getPRDCondition().getSingularity().equalsIgnoreCase("multiple")) {
                    resultAction = new MultipleConditionAction(thenList, elseList, prdAction.getPRDCondition().getLogical(),
                            ConditionFactory.BuildConditionFromList(prdAction.getPRDCondition().getPRDCondition()));
                }
                break;
            case SET:
                resultAction = new SetAction(prdAction.getProperty(), new Expression(prdAction.getValue()));
                break;
            case KILL:
                resultAction = new KillAction();
                break;
            case REPLACE:
                break;
            case PROXIMITY:
                break;
        }
        return resultAction;
    }
}

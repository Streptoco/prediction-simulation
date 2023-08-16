package engine.worldbuilder.factory;

import engine.actions.api.ActionInterface;
import engine.actions.expression.Expression;
import engine.actions.impl.condition.impl.Condition;
import engine.actions.impl.condition.impl.MultipleCondition;
import engine.actions.impl.condition.impl.SingleCondition;
import engine.worldbuilder.prdobjects.PRDCondition;

import java.util.ArrayList;
import java.util.List;

public class ConditionFactory{
    public static Condition BuildCondition (PRDCondition prdCondition) {
            if (prdCondition.getSingularity().equalsIgnoreCase("single")) {
                return (new SingleCondition(prdCondition.getProperty(), prdCondition.getOperator(), new Expression(prdCondition.getValue())));
            } else if (prdCondition.getSingularity().equalsIgnoreCase("multiple")) {
                return new MultipleCondition(prdCondition.getLogical(), BuildConditionFromList(prdCondition.getPRDCondition()));
            }
            else {
                return null;
            }
    }

    public static List<Condition> BuildConditionFromList(List<PRDCondition> prdConditionList) {
        List<Condition> conditionList = new ArrayList<>();
        for(PRDCondition currentCondition : prdConditionList) {
            conditionList.add(BuildCondition(currentCondition));
        }
        return conditionList;
    }

}

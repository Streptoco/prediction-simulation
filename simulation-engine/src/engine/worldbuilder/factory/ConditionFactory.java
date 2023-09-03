package engine.worldbuilder.factory;

import engine.action.expression.Expression;
import engine.action.impl.condition.impl.Condition;
import engine.action.impl.condition.impl.MultipleCondition;
import engine.action.impl.condition.impl.SingleCondition;
import engine.worldbuilder.prdobjects.*;
import java.util.ArrayList;
import java.util.List;

public class ConditionFactory{
    public static Condition BuildCondition (PRDCondition prdCondition) {
            if (prdCondition.getSingularity().equalsIgnoreCase("single")) {
                return (new SingleCondition(prdCondition.getEntity(), prdCondition.getProperty(), prdCondition.getOperator(), new Expression(prdCondition.getValue())));
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

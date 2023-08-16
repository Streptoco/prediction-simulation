package engine.worldbuilder.factory;

import engine.actions.api.ActionInterface;
import engine.actions.expression.Expression;
import engine.actions.impl.condition.impl.Condition;
import engine.actions.impl.condition.impl.SingleCondition;
import engine.worldbuilder.prdobjects.PRDCondition;

import java.util.ArrayList;
import java.util.List;

public class ConditionFactory{
    public static Condition BuildCondition (PRDCondition prdCondition) {
        List<Condition> conditionList = new ArrayList<>();
        if(prdCondition.getSingularity().equalsIgnoreCase("single")) {
            conditionList.add(new SingleCondition(prdCondition.getProperty(), prdCondition.getOperator(), new Expression(prdCondition.getValue())));
        }

        else if (prdCondition.getSingularity().equalsIgnoreCase("multiple")) {

            }
        }
    }
}

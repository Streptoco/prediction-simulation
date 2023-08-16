package engine.worldbuilder.factory;

import engine.Rule;
import engine.RuleActivation;
import engine.actions.api.ActionInterface;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDActions;
import engine.worldbuilder.prdobjects.PRDRule;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
    public static Rule BuildRule(PRDRule rule) {
        String ruleName = rule.getName();
        List<ActionInterface> actionList = new ArrayList<>();
        for (PRDAction action : rule.getPRDActions().getPRDAction()) {
            actionList.add(ActionFactory.BuildAction(action));
        }
        Rule newRule = new Rule(ruleName, actionList, new RuleActivation(rule.getPRDActivation().getTicks(), rule.getPRDActivation().getProbability()));
    }
}

package engine.worldbuilder.factory;

import engine.general.object.Rule;
import engine.general.object.RuleActivation;
import engine.action.api.ActionInterface;
import engine.worldbuilder.prdobjects.*;
import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
    public static Rule BuildRule(PRDRule rule) {
        String ruleName = rule.getName();
        List<ActionInterface> actionList = new ArrayList<>();
        for (PRDAction action : rule.getPRDActions().getPRDAction()) {
            actionList.add(ActionFactory.BuildAction(action));
        }
        RuleActivation activation = null;
        if (rule.getPRDActivation() != null) {
            if (rule.getPRDActivation().getTicks() != null && rule.getPRDActivation().getProbability() != null) {
                activation = new RuleActivation(rule.getPRDActivation().getTicks(), rule.getPRDActivation().getProbability());
            } else if (rule.getPRDActivation().getProbability() != null) {
                activation = new RuleActivation(rule.getPRDActivation().getProbability());
            } else if (rule.getPRDActivation().getTicks() != null) {
                activation = new RuleActivation(rule.getPRDActivation().getTicks());
            } else {
                // TODO: handle exception
            }
            return new Rule(ruleName, actionList, activation);
        }
        else {
            return new Rule(ruleName,actionList, new RuleActivation());
        }
    }
}

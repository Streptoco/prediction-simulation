package engine;

import engine.actions.api.ActionInterface;
import engine.context.api.Context;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String name;
    RuleActivation activation;
    private List<ActionInterface> actions;

    public Rule(String name, List<ActionInterface> actions, RuleActivation activation) {
        this.name = name;
        this.activation = activation;
        this.actions = actions;
    }

    public void addAction(ActionInterface action) {
        actions.add(action);
    }

    public boolean activation(int currentTickCount) {
        return activation.checkActivation(currentTickCount);
    }

    public void invokeAction(Context context) {
        for (ActionInterface action : actions) {
            action.invoke(context);
        }
    }
}

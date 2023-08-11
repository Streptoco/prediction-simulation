package engine;

import engine.actions.api.ActionInterface;
import engine.context.api.Context;

import java.util.ArrayList;

public class Rule {
    private String name;
    private int tickIntervals;
    private double probability;
    private ArrayList<ActionInterface> actions;

    public Rule(String name, int tickIntervals, double probability) {
        this.name = name;
        this.tickIntervals = tickIntervals;
        this.probability = probability;
        this.actions = new ArrayList<ActionInterface>();
    }

    public void addAction(ActionInterface action) {
        actions.add(action);
    }

    public boolean activation(int currentTickCount) {
        //TODO: add timing and probability generator so that we could see if it should be activated.
        //TODO: add "isActive" method?
        return true;
    }

    public void invokeAction(Context context) {
        for (ActionInterface action : actions) {
            action.invoke(context);
        }
    }
}

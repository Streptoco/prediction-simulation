package engine;

import engine.actions.api.AbstractAction;
import engine.actions.impl.IncreaseAction;

import java.util.ArrayList;

public class Rule {
    private String name;
    private int tickIntervals;
    private double probability;
    private ArrayList<AbstractAction> actions;

    public Rule(String name, int tickIntervals, double probability) {
        this.name = name;
        this.tickIntervals = tickIntervals;
        this.probability = probability;
        this.actions = new ArrayList<AbstractAction>();
    }

    public void addAction(AbstractAction action) {
        actions.add(action);
    }

    public boolean activation(int currentTickCount) {
        //TODO: add timing and probability generator so that we could see if it should be activated.
        //TODO: add "isActive" method?
        return true;
    }

    public void invokeAction() {
        for (AbstractAction action : actions) {
            if (action.getClass().equals(IncreaseAction.class)) {
                ((IncreaseAction) action).invokeAction();
            }
        }
    }
}

package engine;

import java.util.ArrayList;

public class Rule {
    private String name;
    private int tickIntervals;
    private double probability;
    private ArrayList<Action> actions;

    public Rule(String name, int tickIntervals, double probability) {
        this.name = name;
        this.tickIntervals = tickIntervals;
        this.probability = probability;
        this.actions = new ArrayList<Action>();
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public boolean activation(int currentTickCount) {
        //TODO: add timing and probability generator so that we could see if it should be activated.
        //TODO: add "isActive" method?
        return true;
    }

    public void invokeAction() {
        for (Action action : actions) {
            if (action.getClass().equals(IncreaseAction.class)) {
                ((IncreaseAction) action).invokeAction();
            }
        }
    }
}

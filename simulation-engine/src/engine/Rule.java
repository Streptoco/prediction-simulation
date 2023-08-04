package engine;

import java.util.ArrayList;

public class Rule {
    private String name;
    private int tickIntervals;
    private double probability;
    private ArrayList<Action> actions;

    public boolean activation(int currentTickCount) {
        //TODO: add timing and probability generator so that we could see if it should be activated.
        //TODO: add "isActive" method?
        return true;
    }
}

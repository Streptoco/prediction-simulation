package engine;

/*
* World contains a main loop that ticks one at a time, and also has a list of rules.
* The list of rules, we'll go by them and for every iteration in the loop we'll see if it can be invoked.
* */

//TODO: in the loop, when it ends, return why it ended.

import java.util.ArrayList;

public class World {
    private int tickCounter;
    private ArrayList<Entity> entities;
    private ArrayList<Rule> rules;

    //Constructors

    public World(int tickCounter, ArrayList<Entity> entities, ArrayList<Rule> rules) {
        this.tickCounter = tickCounter;
        this.entities = entities;
        this.rules = rules;
    }
}

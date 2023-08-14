package engine;

/*
* World contains a main loop that ticks one at a time, and also has a list of rules.
* The list of rules, we'll go by them and for every iteration in the loop we'll see if it can be invoked.
* */

//TODO: in the loop, when it ends, return why it ended.

import engine.actions.expression.ReturnType;
import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.properties.api.AbstractProperty;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private int tickCounter;
    private EntityInstanceManager manager;
    private ArrayList<Rule> rules;
    private Environment activeEnvironment;
    //Constructors

    public World(int tickCounter, EntityInstanceManager manager, ArrayList<Rule> rules) {
        this.tickCounter = tickCounter;
        this.manager = manager;
        this.rules = rules;
        this.activeEnvironment = new Environment();
    }

    public void Run() {
        for (int i = 0; i < tickCounter; i++) {
            for(EntityInstance currentInstance : manager.getInstances()) {
                ContextImpl context = new ContextImpl(currentInstance, manager, activeEnvironment);
                for (Rule rule : rules) {
                    rule.invokeAction(context);
                }
            }

        }
    }

    public static int randomGetter(int randomNumberUpperBound) {
        Random random = new Random();
        return random.nextInt(randomNumberUpperBound);
    }

    public Environment getEnvironment() { return activeEnvironment; }
}

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
import engine.properties.api.PropertyInterface;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;

import java.util.*;

public class World {
    private Termination termination;
    private Map<String, EntityInstanceManager> managers;
    private List<Rule> rules;
    private List<EntityDefinition> entities;
    private Environment activeEnvironment;
    //Constructors

    public World(Termination termination, List<EntityDefinition> entities, List<PropertyInterface> properties,
                 List<Rule> rules) {
        this.termination = termination;
        this.entities = entities;
        this.rules = rules;
        this.activeEnvironment = new Environment();
        for (PropertyInterface property : properties) {
            activeEnvironment.setProperty(property);
        }
        managers = new HashMap<>();
        for (EntityDefinition entity : entities) {
            managers.put(entity.getName(), new EntityInstanceManager());
            for (int i = 0; i < entity.getPopulation(); i++) {
                managers.get(entity.getName()).create(entity);
            }
        }

    }

    public void Run() {
        for (int i = 0; i < tickCounter; i++) {
            for(EntityInstance currentInstance : manager.getInstances()) {
                ContextImpl context = new ContextImpl(currentInstance, manager, activeEnvironment);
                for (Rule rule : rules) {
                    rule.invokeAction(context);
                }
            }
            // TODO: make by termination...
        }
    }

    public static int randomGetter(int randomNumberUpperBound) {
        Random random = new Random();
        return random.nextInt(randomNumberUpperBound);
    }

    public Environment getEnvironment() { return activeEnvironment; }
}

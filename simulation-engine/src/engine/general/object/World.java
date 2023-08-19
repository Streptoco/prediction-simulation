package engine.general.object;

/*
* World contains a main loop that ticks one at a time, and also has a list of rules.
* The list of rules, we'll go by them and for every iteration in the loop we'll see if it can be invoked.
* */

//TODO: in the loop, when it ends, return why it ended.

import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;

import java.util.*;

public class World {
    private Termination termination;
    private Map<String, EntityInstanceManager> managers;
    private List<Rule> rules;
    private List<EntityDefinition> entities;
    private Environment activeEnvironment;
    private long currentTime;
    //Constructors

    public World(Termination termination, List<EntityDefinition> entities, Environment environment,
                 List<Rule> rules) {
        this.termination = termination;
        this.entities = entities;
        this.rules = rules;
        this.activeEnvironment = environment;
        managers = new HashMap<>();
        for (EntityDefinition entity : entities) {
            managers.put(entity.getName(), new EntityInstanceManager());
            for (int i = 0; i < entity.getPopulation(); i++) {
                managers.get(entity.getName()).create(entity);
            }
        }
    }

    public void Run() {
        int ticks = 0;
        this.currentTime = System.currentTimeMillis();
        while (termination.getTermination(ticks, currentTime)) {
            for(EntityDefinition currentEntity : entities) {
                for (EntityInstance currentInstance : managers.get(currentEntity.getName()).getInstances()) {
                    if (currentInstance.isAlive()) {
                        ContextImpl context = new ContextImpl(currentInstance, managers.get(currentEntity.getName()), activeEnvironment);
                        for (Rule rule : rules) {
                            if (rule.activation(ticks)) {
                                rule.invokeAction(context);
                            }
                        }
                    }
                }
            }
            ticks++;
        }
    }

    public static double NumberRandomGetter(double rangeMin, double rangeMax) {
        Random random = new Random();
        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    public static boolean BooleanRandomGetter() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public static String StringRandomGetter() {
        Random randomInt = new Random();
        int length =  randomInt.nextInt(50 - 1 + 1) + 1;
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!?,-_.() ";
        StringBuilder randomString = new StringBuilder();
        Random randomStr = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = randomStr.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();

    }

    public Environment getEnvironment() { return activeEnvironment; }

    public List<EntityDefinition> GetEntities() { return this.entities; }

    public List<Rule> getRules() {
        return rules;
    }

    public int GetSimulationTotalTicks() { return this.termination.getAllTicks();}
    public long GetSimulationTotalTime() { return this.termination.getHowManySecondsToRun();}
}

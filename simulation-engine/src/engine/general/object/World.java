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
import engine.grid.impl.Grid;

import java.text.SimpleDateFormat;
import java.util.*;

public class World {
    private Termination termination;
    private final Map<String, EntityInstanceManager> managers;
    private final List<Rule> rules;
    private final List<EntityDefinition> entities;
    private final Environment activeEnvironment;
    private long currentTime;
    private final SimpleDateFormat simulationDate;
    private int numOfThreads;
    private final Date simDate;
    private Grid grid;
    private List<EntityInstance> allInstances;
    //Constructors

    public World(Termination termination, List<EntityDefinition> entities, Environment environment,
                 List<Rule> rules, int numOfThreads, Grid grid) {
        this.termination = termination;
        this.entities = entities;
        this.rules = rules;
        this.activeEnvironment = environment;
        this.numOfThreads = numOfThreads;
        this.simulationDate = new SimpleDateFormat("dd-MM-yyyy | HH.mm.ss");
        this.simDate = new Date();
        this.simulationDate.format(this.simDate);
        this.grid = grid;
        managers = new HashMap<>();
        for (EntityDefinition entity : entities) {
            managers.put(entity.getName(), new EntityInstanceManager());
            managers.get(entity.getName()).setNumberOfAllInstances(entity.getPopulation());
            managers.get(entity.getName()).setEntityName(entity.getName());
            for (int i = 0; i < entity.getPopulation(); i++) {
                managers.get(entity.getName()).create(entity);
            }
        }
    }

    public void Run() {
        int ticks = 0;
        getAllInstances();
        grid.assignSacks(this.allInstances);
        grid.drawGrid();
        this.currentTime = System.currentTimeMillis();
        while (termination.getTermination(ticks, currentTime)) {
            if (ticks != 0) {
                grid.MoveSacks();
                System.out.println("Move number " + ticks);
                grid.drawGrid();
            }
            for (EntityDefinition currentEntity : entities) {
                for (EntityInstance currentInstance : managers.get(currentEntity.getName()).getInstances()) {
                    if (currentInstance.isAlive()) {
                        ContextImpl context = new ContextImpl(currentInstance, this.managers, activeEnvironment, ticks);
                        context.setGrid(this.grid);
                        for (Rule rule : rules) {
                            if (rule.activation(ticks)) {
                                rule.invokeAction(context);
                            }
                        }
                    }
                }
            }
            removeSpecifiedEntities();
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
        int length = randomInt.nextInt(50 - 1 + 1) + 1;
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

    public Environment getEnvironment() {
        return activeEnvironment;
    }

    public List<EntityDefinition> GetEntities() {
        return this.entities;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public int GetSimulationTotalTicks() {
        return this.termination.getAllTicks();
    }

    public long GetSimulationTotalTime() {
        return this.termination.getHowManySecondsToRun();
    }

    public SimpleDateFormat getSimulationDate() {
        return simulationDate;
    }

    public List<EntityInstanceManager> getAllInstancesManager() {
        return new ArrayList<>(managers.values());
    }

    public Date getSimDate() {
        return simDate;
    }

    public EntityInstanceManager GetInstances(String entityName) {
        return managers.get(entityName);
    }

    public void createPopulationOfEntity(EntityDefinition entity, int population) {
        EntityInstance currentEntity;
        for (int i = 0; i < population; i++) {
            currentEntity = managers.get(entity.getName()).create(entity);
        }
    }

    public void getAllInstances() {
        this.allInstances = new ArrayList<>();
        for (EntityDefinition entityDefinition : entities) {
            allInstances.addAll(managers.get(entityDefinition.getName()).getInstances());
        }
    }

    public void removeSpecifiedEntities() {
        Iterator<EntityInstance> it = allInstances.iterator();
        while (it.hasNext()) {
            EntityInstance instance = it.next();
            if (!instance.isAlive()) {
                managers.get(instance.getEntityName()).killEntity(instance);
                it.remove();
            }
        }
    }

    public void NewRun() {
        int ticks = 0;
        this.currentTime = System.currentTimeMillis();
        getAllInstances();
        grid.assignSacks(this.allInstances);
        while (termination.getTermination(ticks, currentTime)) {
            if (ticks != 0) {
                //grid.drawGrid();
                grid.MoveSacks();
                System.out.println("Move number " + ticks);
                grid.drawGrid();
            } else {
                grid.drawGrid();
            }
            for (Rule rule : rules) {
                if (rule.CheckTicks(ticks)) {
                    rule.NewInvokeAction(this.managers, this.activeEnvironment, this.grid, ticks);
                }
            }
            removeSpecifiedEntities();
            ticks++;
        }

    }
}


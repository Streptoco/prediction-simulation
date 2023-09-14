package engine.general.object;

import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.grid.api.Coordinate;
import engine.grid.impl.Grid;

import java.text.SimpleDateFormat;
import java.util.*;

public class World {
    private final Termination termination;
    private final Map<String, EntityInstanceManager> managers;
    private final List<Rule> rules;
    private final List<EntityDefinition> entities;
    private final Environment activeEnvironment;
    private long currentTime;
    private final SimpleDateFormat simulationDate;
    private final int numOfThreads;
    private final Date simDate;
    private final Grid grid;
    private List<EntityInstance> allInstances;
    private final int maxEntitiesAmount;
    private int currentEntitiesAmount;
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
        this.maxEntitiesAmount = grid.getRows() * grid.getCols();
        this.currentEntitiesAmount = 0;
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


    public int getNumOfThreads() {
        return numOfThreads;
    }

    public int getRows() {
        return this.grid.getRows();
    }

    public int getCols() {
        return this.grid.getCols();
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

    public Grid getGrid() {
        return grid;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public Map<String, EntityInstanceManager> getManagers() {
        return managers;
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

    public void createPopulationOfEntity(String entityName, int population) {
        if(currentEntitiesAmount + population <= maxEntitiesAmount) {
            currentEntitiesAmount += population;
            EntityDefinition entityToCreate = null;
            boolean found = false;
            for (EntityDefinition currentEntity : entities) {
                if (currentEntity.getName().equalsIgnoreCase(entityName)) {
                    entityToCreate = currentEntity;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("Couldn't find entity definition in the name: " + entityName);
            } else {
                for (int i = 0; i < population; i++) {
                    managers.get(entityToCreate.getName()).create(entityToCreate);
                }
            }
        } else {
            throw new RuntimeException("The size of the population " + entityName + " exceeded the size on the grid");
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

    public void assignSacks() {
        getAllInstances();
        grid.assignSacks(this.allInstances);
    }

    public void setCurrentTime() {
        this.currentTime = System.currentTimeMillis();
    }


    public void NewRun() {
        int ticks = 0;
        this.currentTime = System.currentTimeMillis();
        getAllInstances();
        grid.assignSacks(this.allInstances);
        while (termination.getTermination(ticks, currentTime)) {
            if (ticks != 0) {
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

    synchronized public void doWhenTickIsOver() {
        removeSpecifiedEntities();
    }

    public Termination getTermination() {
        return this.termination;
    }


    //=============================================================================================

    public void placeEntityOnGrid(EntityInstance entity, int row, int col) {
        // THIS IS A DUMMY FUNCTION FOR DEBUG PURPOSE
        grid.addSackToGrid(entity, row, col);
    }

    public void printGrid() {
        grid.drawGrid();
    }

    public List<EntityInstance> changeGrid(EntityInstance entity, int row, int col) {
        Set<EntityInstance> set = new HashSet<>();
        Coordinate coordinate = new Coordinate(row, col);
        grid.getAllInstancesAroundMe(coordinate, coordinate, 1, set);
        return new ArrayList<>(set);
    }


}



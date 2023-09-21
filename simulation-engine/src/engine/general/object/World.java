package engine.general.object;

import engine.context.impl.ContextImpl;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.grid.api.Coordinate;
import engine.grid.impl.Grid;
import simulations.dto.PopulationsDTO;
import simulations.dto.SimulationDTO;
import uitoengine.filetransfer.EntityAmountDTO;

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
    private Map<Integer, PopulationsDTO> entitiesAmountPerTick;

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

    public void initializeEntitiesAmount() {
        this.entitiesAmountPerTick = new HashMap<>();
        List<EntityAmountDTO> amounts = new ArrayList<>();
        for (Map.Entry<String, EntityInstanceManager> entry : managers.entrySet()) {
            amounts.add(new EntityAmountDTO(entry.getKey(), entry.getValue().getCountInstances()));
        }
        this.entitiesAmountPerTick.put(0, new PopulationsDTO(amounts));
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

    public Map<Integer, PopulationsDTO> getEntitiesAmountPerTick() {
        return entitiesAmountPerTick;
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
        if (currentEntitiesAmount + population <= maxEntitiesAmount) {
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
        /*
         while (it.hasNext()) {
            EntityInstance instance = it.next();
            if (!instance.isAlive()) {
                it.remove();
            }
        }
         */
        allInstances.removeIf(instance -> !instance.isAlive());
    }

    public void assignSacks() {
        getAllInstances();
        grid.assignSacks(this.allInstances);
    }

    public void setCurrentTime() {
        this.currentTime = System.currentTimeMillis();
    }


    public synchronized void doWhenTickIsOver(int currentTick) {
        removeSpecifiedEntities();
        if(currentTick != 0) {
            List<EntityAmountDTO> amounts = new ArrayList<>();
            for (Map.Entry<String, EntityInstanceManager> entry : managers.entrySet()) {
                amounts.add(new EntityAmountDTO(entry.getKey(), entry.getValue().getCountInstances()));
            }
            this.entitiesAmountPerTick.put(currentTick, new PopulationsDTO(amounts));
        }
    }

    public double getConsistency(String entityName, String propertyName) {
        double result = 0;
        EntityInstanceManager currentManager = this.managers.get(entityName);
        for(EntityInstance currentEntity : currentManager.getInstances()) {
            if(currentEntity.isAlive()) {
                result += currentEntity.getPropertyByName(propertyName).getAverageTimeOfChange();
            }
        }
        return result / currentManager.getCountInstances();
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



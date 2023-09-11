package engine.general.multiThread.impl;

import engine.general.multiThread.api.SimulationRunner;
import engine.general.multiThread.api.Status;
import engine.general.object.World;
import enginetoui.dto.basic.impl.WorldDTO;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationExecutionManager {
    private final Map<Integer, SimulationRunner> simulations;
    private int simulationCounter;
    private ExecutorService executor = null;

    public SimulationExecutionManager() {
        simulations = new HashMap<>();
        simulationCounter = 0;
    }

    public int CreateSimulation(World world) {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(world.getNumOfThreads());
        }
        SimulationRunner currentSimulation = new SimulationRunner(world, simulationCounter);
        simulations.put(simulationCounter, currentSimulation);
        return simulationCounter++;
    }

    public void StartSimulation(int id) {
        executor.execute(simulations.get(id));
    }

    public int getLatestSimulation() {
        return simulationCounter - 1;
    }

    public List<WorldDTO> GetSimulations() {
        List<WorldDTO> resultList = new ArrayList<>();
        for (Map.Entry<Integer, SimulationRunner> entry : simulations.entrySet()) {
            resultList.add(new WorldDTO(entry.getKey(), entry.getValue().getWorld().getSimulationDate(), entry.getValue().getWorld().getAllInstancesManager(),
                    entry.getValue().getWorld().getSimDate(), entry.getValue().getWorld().getTermination(),
                    entry.getValue().getWorld().getRules(), entry.getValue().getWorld().GetEntities(), entry.getValue().getWorld().getEnvironment(),
                    entry.getValue().getWorld().getRows(), entry.getValue().getWorld().getCols()));
        }
        return resultList;
    }

    public WorldDTO getWorldDTO(int id) {
        Date date = new Date();
        World world = simulations.get(id).getWorld();
        return new WorldDTO(id, world.getSimulationDate(), world.getAllInstancesManager(),
                date, world.getTermination(), world.getRules(),
                world.GetEntities(), world.getEnvironment(),
                world.getRows(), world.getCols());
    }

    public World getWorld(int id) {
        return simulations.get(id).getWorld();
    }

    public World getSimulation(int id) {
        return getWorld(id);
    }

    public void pauseSimulation(int id) {
        simulations.get(id).setStatus(Status.PAUSED);
    }

    public void resumeSimulation(int id) {
        simulations.get(id).resumeSimulation();
    }

    public void abortSimulation(int id) {
        simulations.get(id).setStatus(Status.ABORTED);
    }

    public void simulationManualStep(int id) {
        executor.execute(simulations.get(id)::simulationManualStep);
    }

    public Status getSimulationStatus(int id) {
        return simulations.get(id).getStatus();
    }

    public void setSimulationStatus(Status status, int id) {
        simulations.get(id).setStatus(status);
    }

    public int getSimulationTick(int id) {
        return simulations.get(id).getTick();
    }

    public Map<String, Integer> getSimulationEntitiesAmount(int id) {
        return simulations.get(id).getAllEntitiesAmount();
    }

}

package engine.general.multiThread.impl;

import engine.entity.impl.EntityInstanceManager;
import engine.general.multiThread.api.SimulationRunner;
import engine.general.multiThread.api.Status;
import engine.general.object.World;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import enginetoui.dto.basic.impl.WorldDTO;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationExecutionManager {
    private final Map<Integer, SimulationRunner> simulations;
    private int simulationCounter;
    private ExecutorService executor = null;

    public int getSimulationCounter() {
        return simulationCounter;
    }

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
        executor.execute(simulations.get(id)::pauseSimulation);
    }

    public void resumeSimulation(int id) {
        executor.execute(simulations.get(id)::resumeSimulation);
    }

    public void abortSimulation(int id) {
        //executor.execute(simulations.get(id)::abortSimulation);
        simulations.get(id).abortSimulation();
    }

    public void manualStopSimulation(int id) {
        simulations.get(id).manualStopSimulation();
    }

    public void simulationManualStep(int id) {
        executor.execute(simulations.get(id)::simulationManualStep);
    }

    public Status getSimulationStatus(int id) {
        return simulations.get(id).getStatus();
    }


    public int getSimulationTick(int id) {
        return simulations.get(id).getTick();
    }

    public Map<String, Integer> getSimulationEntitiesAmount(int id) {
        return simulations.get(id).getAllEntitiesAmount();
    }

    public SimulationStatusDTO getSimulationDetails(int id) {
        SimulationStatusDTO resultDTO = null;
        synchronized (simulations.get(id)) {
            SimulationRunner currentSim = simulations.get(id);
            resultDTO = new SimulationStatusDTO(id, currentSim.getStatus(), currentSim.getTick(), currentSim.getSimulationRunningTimeInMillis());
            World currentWorld = currentSim.getWorld();
            for (Map.Entry<String, EntityInstanceManager> entry : currentWorld.getManagers().entrySet()) {
                resultDTO.updateEntityAmount(entry.getKey(), entry.getValue().getCountInstances());
            }
        }
        return resultDTO;


    }

    public int getNumberOfRunningSimulations() {
        int count = 0;
        for (Map.Entry<Integer, SimulationRunner> entry : simulations.entrySet()) {
            if (entry.getValue().getStatus().equals(Status.RUNNING)) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfWaitingSimulations() {
        int count = 0;
        for (Map.Entry<Integer, SimulationRunner> entry : simulations.entrySet()) {
            if (entry.getValue().getStatus().equals(Status.LOADING)) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfDoneSimulations() {
        int count = 0;
        for (Map.Entry<Integer, SimulationRunner> entry : simulations.entrySet()) {
            if (entry.getValue().getStatus().equals(Status.DONE)) {
                count++;
            }
        }
        return count;
    }

}

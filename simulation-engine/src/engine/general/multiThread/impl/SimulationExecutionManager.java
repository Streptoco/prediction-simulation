package engine.general.multiThread.impl;

import engine.general.multiThread.api.SimulationRunner;
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
        if(executor == null) {
            executor = Executors.newFixedThreadPool(world.getNumOfThreads());
        }
        SimulationRunner currentSimulation = new SimulationRunner(world);
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
        for(Map.Entry<Integer, SimulationRunner> entry : simulations.entrySet()) {
            resultList.add(new WorldDTO(entry.getKey(), entry.getValue().getWorld().getSimulationDate(), entry.getValue().getWorld().getAllInstancesManager(),
                    entry.getValue().getWorld().getSimDate(), entry.getValue().getWorld().getTermination(),
                    entry.getValue().getWorld().getRules(), entry.getValue().getWorld().GetEntities(), entry.getValue().getWorld().getEnvironment(),
                    entry.getValue().getWorld().getRows(), entry.getValue().getWorld().getCols()));
        }
        return resultList;
    }

    public WorldDTO getWorldDTO(int id) {
        Date date = new Date();
        return new WorldDTO(id, simulations.get(id).getWorld().getSimulationDate(), simulations.get(id).getWorld().getAllInstancesManager(),
                date, simulations.get(id).getWorld().getTermination(), simulations.get(id).getWorld().getRules(),
                simulations.get(id).getWorld().GetEntities(), simulations.get(id).getWorld().getEnvironment(), simulations.get(id).getWorld().getRows(),
                simulations.get(id).getWorld().getCols());
    }



}

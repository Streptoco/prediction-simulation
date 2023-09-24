package handler.controller;

import engine.general.multiThread.api.Status;
import engine.general.object.Engine;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SimulationManager {
    Engine engine; // TODO: maybe change idk

    public ObservableList<Simulation> getSimulations() {
        return simulations;
    }

    private ObservableList<Simulation> simulations;
    private List<Simulation> runningSimulations;

    public SimulationManager(Engine engine) {
        this.engine = engine;
        simulations = FXCollections.observableArrayList();
        runningSimulations = new ArrayList<>();
    }

    public void addSimulation(Simulation simulationToAdd) {
        simulations.add(simulationToAdd);
        runningSimulations.add(simulationToAdd);
    }

    public void update() {
        for(Simulation simulation : simulations) {
            SimulationStatusDTO simulationStatusDTO = engine.getSimulationDetails(simulation.getSimulationID());
            if (!(simulationStatusDTO.status.equals(Status.DONE) || simulationStatusDTO.status.equals(Status.PAUSED))) {
                simulation.setSeconds(simulationStatusDTO.simulationRunningTimeInMillis/1000);
                simulation.setTicks(simulationStatusDTO.currentTick);
                simulation.setProgress((double)(simulationStatusDTO.simulationRunningTimeInMillis / simulationStatusDTO.totalSecondsInMillis) * 0.001);
                simulation.setTickProgress((double)simulationStatusDTO.currentTick / (double)simulationStatusDTO.totalTicks);
                System.out.println((double)simulationStatusDTO.currentTick / (double)simulationStatusDTO.totalTicks);
            }
            simulation.setStatus("Simulation status: " + simulationStatusDTO.status.toString().toLowerCase());
        }
        // TODO: more info
    }
}

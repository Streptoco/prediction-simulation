package handler.controller;

import javafx.concurrent.Task;
import javafx.scene.chart.ScatterChart;

public class ResultGraphTask extends Task<ScatterChart<Number, Number>> {

    private final SimulationManager simulationManager;
    private final int simId;
    public ResultGraphTask(SimulationManager manager, int id) {
        this.simulationManager = manager;
        this.simId = id;
    }

    @Override
    protected ScatterChart<Number, Number> call() throws Exception {
        return simulationManager.getScatter(simId);
    }
}

package handler.controller;

import engine.entity.impl.EntityDefinition;
import engine.general.multiThread.api.SimulationRunner;
import engine.general.multiThread.api.Status;
import engine.general.object.Engine;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import simulation.dto.PopulationsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        for (Simulation simulation : simulations) {
            SimulationStatusDTO simulationStatusDTO = engine.getSimulationDetails(simulation.getSimulationID());
            if (!(simulationStatusDTO.status.equals(Status.DONE) || simulationStatusDTO.status.equals(Status.PAUSED))) {
                simulation.setSeconds(simulationStatusDTO.simulationRunningTimeInMillis / 1000);
                simulation.setTicks(simulationStatusDTO.currentTick);
                simulation.setProgress((double) (simulationStatusDTO.simulationRunningTimeInMillis / simulationStatusDTO.totalSecondsInMillis) * 0.001);
                simulation.setTickProgress((double) simulationStatusDTO.currentTick / (double) simulationStatusDTO.totalTicks);
            }
            simulation.setStatus("Simulation status: " + simulationStatusDTO.status.toString().toLowerCase());
            simulation.setEntityList(FXCollections.observableList(simulationStatusDTO.entityDefinitions)); // We're trying to take a regular list, and make it an observable list so that it could fit the PropertyList we have in the Simulation class.
            if ((simulationStatusDTO.status.equals(Status.DONE)) || simulationStatusDTO.status.equals(Status.ABORTED)) {
                simulation.setIsSimulationDone(true);
            }

        }
        // TODO: more info
    }

    public ScatterChart<Number, Number> getEntitiesAmountPerTickWhenSimulationIsDone(int id) {
        SimulationRunner currentSim = engine.getSimulationRunner(id);
        NumberAxis xAxisTick = new NumberAxis();
        xAxisTick.setLabel("Tick");
        xAxisTick.setAutoRanging(false);
        xAxisTick.setTickUnit((double) currentSim.getTick() / 50);
        NumberAxis yAxisAmount = new NumberAxis();
        yAxisAmount.setLabel("Amount");
        ScatterChart<Number, Number> resultChart = new ScatterChart<>(xAxisTick, yAxisAmount);
        resultChart.setTitle("Entities Amount per Tick");
        resultChart.setVisible(false);
        List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
        if (currentSim.getStatus().equals(Status.DONE)) {
            for (EntityDefinition currentEntity : currentSim.getWorld().GetEntities()) {
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.getData().add(new XYChart.Data<>(0,0)); // Dummy data in order to make the legend appear, but it doesn't
                series.setName(currentEntity.getName());
                seriesList.add(series);

            }
            Map<Integer, PopulationsDTO> entitiesAmount = currentSim.getEntitiesAmountPerTick();
            for (Map.Entry<Integer, PopulationsDTO> currentTick : entitiesAmount.entrySet()) {
                for (Map.Entry<String, Integer> currentEntityDef : currentTick.getValue().getEntities().entrySet()) {
                    for (XYChart.Series<Number, Number> currentSeries : seriesList) {
                        if (currentSeries.getName().equalsIgnoreCase(currentEntityDef.getKey())) {
                            currentSeries.getData().add(new XYChart.Data<>(currentTick.getKey(), currentEntityDef.getValue()));
                        }
                    }
                }
            }
            resultChart.getData().addAll(seriesList);
        }
        for (Simulation simulation : simulations) {
            if (simulation.getSimulationID() == id) {
                simulation.setEntitiesAmountScatter(resultChart);
            }
        }

        resultChart.setVisible(true);
        resultChart.setPrefWidth(300);
        resultChart.setPrefHeight(190);
        resultChart.setLegendVisible(true);
        resultChart.setLegendSide(Side.BOTTOM);
        resultChart.setStyle("-fx-legend-side: bottom;");
        return resultChart;
    }

    public ScatterChart<Number, Number> getScatter(int id) {
        ScatterChart<Number, Number> result = null;
        for (Simulation simulation : simulations) {
            if (simulation.getSimulationID() == id) {
                result = simulation.getEntitiesAmountScatter();
                if (result == null && simulation.getStatus().contains("done")) {
                    result = getEntitiesAmountPerTickWhenSimulationIsDone(id);
                }
            }
        }
        return result;
    }

    public double getConsistency(String entityName, String propertyName, int id) {
        return engine.getConsistency(entityName, propertyName, id);
    }

    public double getAverage(String entityName, String propertyName, int id) {
        return engine.averageValueOfProperty(entityName, propertyName, id);
    }


}

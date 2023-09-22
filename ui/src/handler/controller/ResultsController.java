package handler.controller;

import engine.general.object.Engine;
import engine.property.impl.StringProperty;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import tree.item.impl.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {
    private int currentSimID;
    private Engine engine;
    @FXML
    private TreeView treeView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label secondsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button resumeButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.engine = (Engine) resources.getObject("Engine");
        SimulationTreeItem rootItem = new SimulationTreeItem();
        treeView.setShowRoot(false);
        treeView.setRoot(rootItem);
        for (int i = 1; i < engine.getSimulationManager().getSimulationCounter(); i++) {
            SimulationStatusDTO simulationStatusDTO = new SimulationStatusDTO(i, engine.getSimulationManager().getSimulationStatus(i),
                    engine.getSimulationManager().getSimulationTick(i), 0);
            SimulationTreeItem simulationTreeItem = new SimulationTreeItem(simulationStatusDTO);
            rootItem.getChildren().add(simulationTreeItem);
        }

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                // Check if the selected item is a RuleTreeItem or EntityTreeItem
//                if (newValue instanceof RuleTreeItem) {
//                    ((RuleTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof EntityTreeItem) {
//                    ((EntityTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof TerminationTreeItem) {
//                    ((TerminationTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof GridTreeItem) {
//                    ((GridTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof PropertyTreeItem) {
//                    ((PropertyTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof EnvironmentTreeItem) {
//                    ((EnvironmentTreeItem) newValue).ApplyText(mainTextArea);
//                } else if (newValue instanceof ActionTreeItem) {
//                    ((ActionTreeItem) newValue).ApplyText(mainTextArea);
//                }
//            }
            if (newValue != null) {
                SimulationStatusDTO currentDTO = ((SimulationTreeItem) newValue).getSimulationStatusDto();
                currentSimID = currentDTO.simID;
                long totalTime = engine.getSimulationManager().getSimulationDetails(currentDTO.simID).simulationRunningTimeInMillis * 1000;
                double updateIntervalInSeconds = 0.01;
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(updateIntervalInSeconds), event -> {
                            double elapsedTimeInSeconds = progressBar.getProgress() * totalTime;
                            if (elapsedTimeInSeconds < totalTime) {
                                double newProgress = (elapsedTimeInSeconds + updateIntervalInSeconds) / totalTime;
                                progressBar.setProgress(newProgress);
                                secondsLabel.setText(String.valueOf(elapsedTimeInSeconds));
                            }
                        })
                );

                timeline.setCycleCount((int) (totalTime / updateIntervalInSeconds));
                timeline.play();
//                secondsLabel.textProperty().bind(new SimpleLongProperty(engine.getSimulationManager().getSimulationDetails(currentDTO.simID).simulationRunningTimeInMillis).asString());

                statusLabel.setText("Simulation status: " + engine.getSimulationManager().getSimulationStatus(currentDTO.simID).toString().toLowerCase());
            }
        });
    }

    public void increaseProgress() {
        String progress = treeView.getSelectionModel().getSelectedItem().toString();
    }

    public void stopButtonAction(ActionEvent actionEvent) {
        engine.getSimulationManager().manualStopSimulation(currentSimID);
    }

    public void resumeButtonAction(ActionEvent actionEvent) {
        engine.getSimulationManager().resumeSimulation(currentSimID);
    }

    public void pauseButtonAction(ActionEvent actionEvent) {
        engine.getSimulationManager().pauseSimulation(currentSimID);
    }
}

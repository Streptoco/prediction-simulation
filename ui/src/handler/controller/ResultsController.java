package handler.controller;

import engine.general.multiThread.api.Status;
import engine.general.object.Engine;
import engine.property.impl.StringProperty;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.w3c.dom.css.Counter;
import tree.item.impl.*;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class ResultsController implements Initializable {
    private Task<Void> statusUpdateTask;
    private int currentSimID;
    private SimulationManager simulationManager;
    @FXML
    private ListView<Simulation> listView;
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
        this.simulationManager = (SimulationManager) resources.getObject("SimulationManager");

        listView.setItems(simulationManager.getSimulations());

        listView.getSelectionModel().selectFirst();

//        listView.setSelectionModel(listView.getSelectionModel());

        secondsLabel.textProperty().bind(listView.getSelectionModel().getSelectedItem().secondsProperty().asString());

        statusLabel.textProperty().bind(listView.getSelectionModel().getSelectedItem().statusProperty());

        progressBar.progressProperty().bind(listView.getSelectionModel().getSelectedItem().secondsProperty());
//        this.engine = (Engine) resources.getObject("Engine");
//        SimulationTreeItem rootItem = new SimulationTreeItem();
//        treeView.setShowRoot(false);
//        treeView.setRoot(rootItem);
//        for (int i = 1; i < engine.getSimulationManager().getSimulationCounter(); i++) {
//            SimulationStatusDTO simulationStatusDTO = new SimulationStatusDTO(i, engine.getSimulationManager().getSimulationStatus(i),
//                    engine.getSimulationManager().getSimulationTick(i), 0);
//            SimulationTreeItem simulationTreeItem = new SimulationTreeItem(simulationStatusDTO);
//            rootItem.getChildren().add(simulationTreeItem);
//        }
//
//        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
////            if (newValue != null) {
////                // Check if the selected item is a RuleTreeItem or EntityTreeItem
////                if (newValue instanceof RuleTreeItem) {
////                    ((RuleTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof EntityTreeItem) {
////                    ((EntityTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof TerminationTreeItem) {
////                    ((TerminationTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof GridTreeItem) {
////                    ((GridTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof PropertyTreeItem) {
////                    ((PropertyTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof EnvironmentTreeItem) {
////                    ((EnvironmentTreeItem) newValue).ApplyText(mainTextArea);
////                } else if (newValue instanceof ActionTreeItem) {
////                    ((ActionTreeItem) newValue).ApplyText(mainTextArea);
////                }
////            }
//            if (newValue != null) {
//                SimulationStatusDTO currentDTO = ((SimulationTreeItem) newValue).getSimulationStatusDto();
//                currentSimID = currentDTO.simID;
////                long totalTime = engine.getSimulationManager().getSimulationRunningTimeInMillis(currentSimID) / 1000;
////                double updateIntervalInSeconds = 0.01;
////                Timeline timeline = new Timeline(
////                        new KeyFrame(Duration.seconds(updateIntervalInSeconds), event -> {
////                            double elapsedTimeInSeconds = progressBar.getProgress() * totalTime;
////                            if (elapsedTimeInSeconds < engine.getSimulationManager().getSimulation(currentSimID).GetSimulationTotalTime()) {
////                                double newProgress = (elapsedTimeInSeconds + updateIntervalInSeconds) / totalTime;
////                                progressBar.setProgress(newProgress);
////                                secondsLabel.setText(String.valueOf(elapsedTimeInSeconds));
////                            }
////                            else {
////                            }
////                        })
////                );
////
////                if (statusUpdateTask != null && statusUpdateTask.isRunning()) {
////                    statusUpdateTask.cancel(); // Cancel the previous task if it's still running
////                }
////
////                statusUpdateTask = createStatusUpdateTask(currentSimID);
////                Thread statusUpdateThread = new Thread(statusUpdateTask);
////                statusUpdateThread.setDaemon(true);
////                statusUpdateThread.start();
////
////
////
////                timeline.setCycleCount((int) (totalTime / updateIntervalInSeconds));
////                timeline.play();
//////                secondsLabel.textProperty().bind(new SimpleLongProperty(engine.getSimulationManager().getSimulationDetails(currentDTO.simID).simulationRunningTimeInMillis).asString());
////
////                statusLabel.setText("Simulation status: " + engine.getSimulationManager().getSimulationStatus(currentDTO.simID).toString().toLowerCase());
//                Runnable timeTask = new Runnable() {
//                    @Override
//                    public void run() {
//                        while (engine.getSimulationRunner(currentSimID).getStatus().equals(Status.RUNNING)) {
//                            Platform.runLater(() -> {
//                                secondsLabel.setText(String.valueOf(engine.getSimulationManager().getSimulationRunner(currentSimID).getSimulationRunningTimeInMillis() / 1000));
//                            });
//
//                            try {
//                                Thread.sleep(1000); // Sleep for 1 second (1000 milliseconds)
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                };
//
//                new Thread(timeTask).start();
//            }
//        });
    }

    public void stopButtonAction(ActionEvent actionEvent) {
    }

    public void resumeButtonAction(ActionEvent actionEvent) {
    }

    public void pauseButtonAction(ActionEvent actionEvent) {
    }

//    public void stopButtonAction(ActionEvent actionEvent) {
//        engine.getSimulationManager().manualStopSimulation(currentSimID);
//        statusLabel.setText("Simulation status: done");
//    }
//
//    public void resumeButtonAction(ActionEvent actionEvent) {
//        engine.getSimulationManager().resumeSimulation(currentSimID);
//        statusLabel.setText("Simulation status: running");
//    }
//
//    public void pauseButtonAction(ActionEvent actionEvent) {
//        engine.getSimulationManager().pauseSimulation(currentSimID);
//        statusLabel.setText("Simulation status: paused");
//    }
}

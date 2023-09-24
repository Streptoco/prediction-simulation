package handler.controller;

import engine.general.multiThread.api.Status;
import engine.general.object.Engine;
import engine.property.impl.StringProperty;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.w3c.dom.css.Counter;
import tree.item.impl.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class ResultsController implements Initializable {
    private Task<Void> statusUpdateTask;
    private int currentSimID;
    private SimulationManager simulationManager;
    @FXML
    private ListView<Simulation> listView;
    @FXML
    private TableColumn<Simulation, IntegerProperty> entityAmount;
    @FXML
    private TableColumn<Simulation, String> entityName;
    @FXML
    private TableView<Simulation> tableView;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar tickProgress;
    @FXML
    private Label secondsLabel;
    @FXML
    private Label tickLabel;
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

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null){
                secondsLabel.textProperty().bind(Bindings.concat("Current second: ", newValue.secondsProperty().asString()));

                tickLabel.textProperty().bind(Bindings.concat("Current tick: ", newValue.ticksProperty().asString()));

                statusLabel.textProperty().bind(newValue.statusProperty());

                progressBar.progressProperty().bind(newValue.progressProperty());

                tickProgress.progressProperty().bind(newValue.tickProgressProperty());

                amountColumn.setCellValueFactory(new PropertyValueFactory<>(""));
            }

        });

        listView.getSelectionModel().selectFirst();
    }

    public void stopButtonAction(ActionEvent actionEvent) {
    }

    public void resumeButtonAction(ActionEvent actionEvent) {
        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
        simulationManager.engine.resumeSimulation(selectedSimulation.getSimulationID());
    }

    public void pauseButtonAction(ActionEvent actionEvent) {
        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
        simulationManager.engine.pauseSimulation(selectedSimulation.getSimulationID());
    }
}

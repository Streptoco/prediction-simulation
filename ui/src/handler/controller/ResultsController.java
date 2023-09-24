package handler.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

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

                entityName.setCellValueFactory(new PropertyValueFactory<Simulation, String>("entityAmount")); // add "simulation" type to column. this needs to be changed

                tableView.getItems().add(newValue); // add an item to the table view

                tableView.setItems(tableView.getItems()); // set the tableview. none of this works and requires attention.

            }

        });

        listView.getSelectionModel().selectFirst();
    }

    public void stopButtonAction(ActionEvent actionEvent) {
        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
        simulationManager.engine.abortSimulation(selectedSimulation.getSimulationID());
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

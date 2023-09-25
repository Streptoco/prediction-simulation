package handler.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
    @FXML
    private VBox resultBox;
    @FXML
    private Button rerunButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.simulationManager = (SimulationManager) resources.getObject("SimulationManager");

        listView.setItems(simulationManager.getSimulations());

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                secondsLabel.textProperty().bind(Bindings.concat("Current second: ", newValue.secondsProperty().asString()));

                tickLabel.textProperty().bind(Bindings.concat("Current tick: ", newValue.ticksProperty().asString()));

                statusLabel.textProperty().bind(newValue.statusProperty());

                progressBar.progressProperty().bind(newValue.progressProperty());

                tickProgress.progressProperty().bind(newValue.tickProgressProperty());

                entityName.setCellValueFactory(new PropertyValueFactory<Simulation, String>("entityAmount")); // add "simulation" type to column. this needs to be changed

                rerunButton.visibleProperty().bind(newValue.isSimulationDoneProperty());

                tableView.getItems().add(newValue); // add an item to the table view

                tableView.setItems(tableView.getItems()); // set the tableview. none of this works and requires attention.


            }

        });

        listView.getSelectionModel().selectFirst();
        onStatusLabelChange();
        onChooseSimulation();
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

    public void onStatusLabelChange() {
        statusLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (statusLabel.textProperty().get().contains("done")) {
                    int currentSelection = listView.getSelectionModel().getSelectedIndex() + 1;
                    if (listView.getSelectionModel().getSelectedIndex() + 1 == currentSelection) {
                        showGraph();
                    } else {
                        ResultGraphTask entitiesGraph = new ResultGraphTask(simulationManager, currentSelection);
                        Thread thread = new Thread(entitiesGraph);
                        thread.start();
                    }
                }
            }
        });
    }

    public void onChooseSimulation() {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showGraph();
        });
    }

    public void showGraph() {
        ResultGraphTask entitiesGraph = new ResultGraphTask(simulationManager, listView.getSelectionModel().getSelectedIndex() + 1);
        Thread thread = new Thread(entitiesGraph);
        thread.start();
        entitiesGraph.setOnSucceeded(event -> {
            ScatterChart<Number, Number> resultGraph = entitiesGraph.getValue();
            Platform.runLater(() -> {
                if (resultBox.getChildren().isEmpty()) {
                    if (resultGraph != null) {
                        resultBox.getChildren().add(resultGraph);
                    }
                } else {
                    if (resultGraph == null) {
                        resultBox.getChildren().clear();
                    } else {
                        resultBox.getChildren().set(0, resultGraph);
                    }
                }
            });
        });
    }


}

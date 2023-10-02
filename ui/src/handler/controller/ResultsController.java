//package handler.controller;
//
//import engine.entity.impl.EntityDefinition;
//import javafx.application.Platform;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.concurrent.Task;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.chart.ScatterChart;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import javax.xml.bind.JAXBException;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//public class ResultsController implements Initializable {
//    private Task<Void> statusUpdateTask;
//    private int currentSimID;
//    private SimulationManager simulationManager;
//    @FXML
//    private ListView<Simulation> listView;
//    @FXML
//    private ListView<EntityDefinition> entityList;
//    @FXML
//    private ProgressBar progressBar;
//    @FXML
//    private ProgressBar tickProgress;
//    @FXML
//    private Label secondsLabel;
//    @FXML
//    private Label tickLabel;
//    @FXML
//    private Label statusLabel;
//    @FXML
//    private Button resumeButton;
//    @FXML
//    private Button pauseButton;
//    @FXML
//    private Button stopButton;
//    @FXML
//    private VBox resultBox;
//    @FXML
//    private Button rerunButton;
//    @FXML
//    private ComboBox<String> entityMenu;
//    @FXML
//    private ComboBox<String> propertyMenu;
//    @FXML
//    private ComboBox<String> methodMenu;
//    @FXML
//    private Button sumbitStatisticButton;
//    @FXML
//    private TextArea resultLabel;
//    @FXML
//    private Label statisticsLabel;
//    @FXML
//    private Label entityAmountLabel;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        this.simulationManager = (SimulationManager) resources.getObject("SimulationManager");
//
//        listView.setItems(simulationManager.getSimulations());
//
//        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//
//            if (newValue != null) {
//                entityList.setItems(newValue.getEntityList());
//
//                entityList.getSelectionModel().selectedItemProperty().addListener((observableNew, oldValueNew, newValueNew) -> {
//                    if (newValueNew != null) {
//                        entityAmountLabel.textProperty().bind(Bindings.concat("Amount: ", newValueNew.populationPropertyProperty().asString()));
//                    }
//                });
//
//                secondsLabel.textProperty().bind(Bindings.concat("Current second: ", newValue.secondsProperty().asString()));
//
//                tickLabel.textProperty().bind(Bindings.concat("Current tick: ", newValue.ticksProperty().asString()));
//
//                statusLabel.textProperty().bind(newValue.statusProperty());
//
//                progressBar.progressProperty().bind(newValue.progressProperty());
//
//                tickProgress.progressProperty().bind(newValue.tickProgressProperty());
//
//                rerunButton.visibleProperty().bind(newValue.isSimulationDoneProperty());
//
//                statisticsLabel.visibleProperty().bind(newValue.isSimulationDoneProperty());
//
//                entityMenu.visibleProperty().bind(newValue.isSimulationDoneProperty());
//
//                propertyMenu.visibleProperty().bind(entityMenu.visibleProperty());
//
//                methodMenu.visibleProperty().bind(propertyMenu.visibleProperty());
//
//                sumbitStatisticButton.visibleProperty().bind(methodMenu.visibleProperty());
//            }
//
//        });
//
//        listView.getSelectionModel().selectFirst();
//        onStatusLabelChange();
//        onChooseSimulation();
//    }
//
//    public void stopButtonAction(ActionEvent actionEvent) {
//        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
//        simulationManager.engine.abortSimulation(selectedSimulation.getSimulationID());
//    }
//
//    public void resumeButtonAction(ActionEvent actionEvent) {
//        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
//        simulationManager.engine.resumeSimulation(selectedSimulation.getSimulationID());
//    }
//
//    public void pauseButtonAction(ActionEvent actionEvent) {
//        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
//        simulationManager.engine.pauseSimulation(selectedSimulation.getSimulationID());
//    }
//
//    public void rerunButtonAction(ActionEvent actionEvent) throws JAXBException {
//        int simulationID = simulationManager.engine.reRunSimulation(listView.getSelectionModel().getSelectedItem().getSimulationID());
//        Simulation simulation = new Simulation();
//        simulation.setSimulationID(simulationID);
//        simulationManager.addSimulation(simulation);
//        simulationManager.engine.runSimulation(simulationID);
//    }
//
//    public void onStatusLabelChange() {
//        statusLabel.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (statusLabel.textProperty().get().contains("done") || statusLabel.textProperty().get().contains("aborted")) {
//                    int currentSelection = listView.getSelectionModel().getSelectedIndex() + 1;
//                    if (listView.getSelectionModel().getSelectedIndex() + 1 == currentSelection) {
//                        showGraph();
//                    } else {
//                        ResultGraphTask entitiesGraph = new ResultGraphTask(simulationManager, currentSelection);
//                        Thread thread = new Thread(entitiesGraph);
//                        thread.start();
//                    }
//                    createStatisticMenu();
//                }
//            }
//        });
//    }
//
//    public void onChooseSimulation() {
//        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            showGraph();
//            entityMenu.getSelectionModel().clearSelection();
//            propertyMenu.getSelectionModel().clearSelection();
//            methodMenu.getSelectionModel().clearSelection();
//            resultLabel.setVisible(false);
//        });
//    }
//
//    public void showGraph() {
//        ResultGraphTask entitiesGraph = new ResultGraphTask(simulationManager, listView.getSelectionModel().getSelectedIndex() + 1);
//        Thread thread = new Thread(entitiesGraph);
//        thread.start();
//        entitiesGraph.setOnSucceeded(event -> {
//            ScatterChart<Number, Number> resultGraph = entitiesGraph.getValue();
//            Platform.runLater(() -> {
//                if (resultBox.getChildren().isEmpty()) {
//                    if (resultGraph != null) {
//                        resultBox.getChildren().add(resultGraph);
//                    }
//                } else {
//                    if (resultGraph == null) {
//                        resultBox.getChildren().clear();
//                    } else {
//                        resultBox.getChildren().set(0, resultGraph);
//                    }
//                }
//            });
//        });
//    }
//
//    public void createStatisticMenu() {
//        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
//        createEntityMenu(selectedSimulation);
//    }
//
//    public void createEntityMenu(Simulation selectedSimulation) {
//        entityMenu.getItems().clear();
//        selectedSimulation.getEntityList().forEach(currentEntity -> {
//            entityMenu.getItems().add(currentEntity.getName());
//        });
//    }
//
//    public void createPropertyMenu(ActionEvent event) {
//        Simulation selectedSimulation = listView.getSelectionModel().getSelectedItem();
//        propertyMenu.getItems().clear();
//        String chosenEntity = entityMenu.getSelectionModel().getSelectedItem();
//        for (EntityDefinition entity : selectedSimulation.getEntityList()) {
//            if (entity.getName().equalsIgnoreCase(chosenEntity)) {
//                entity.getProps().forEach(currentProperty -> {
//                    propertyMenu.getItems().add(currentProperty.getName());
//                });
//            }
//        }
//    }
//
//    public void createMethodMenu(ActionEvent event) {
//        methodMenu.getItems().clear();
//        methodMenu.getItems().add("Histogram");
//        methodMenu.getItems().add("Consistency");
//        methodMenu.getItems().add("Average");
//    }
//
//    public void showSubmitButton(ActionEvent event) {
//        return;
//    }
//
//    public void submitCalc(ActionEvent event) throws IOException {
//        String chosenEntity = entityMenu.getSelectionModel().getSelectedItem();
//        String chosenProperty = propertyMenu.getSelectionModel().getSelectedItem();
//        int id = listView.getSelectionModel().getSelectedIndex() + 1;
//        switch (methodMenu.getSelectionModel().getSelectedItem()) {
//            case "Histogram":
//                Stage popupStage = new Stage();
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("histogram.fxml"));
//                Parent root = loader.load();
//                popupStage.setScene(new Scene(root, 500, 500));
//                HistogramController histogramController = loader.getController();
//                Map<String, Integer> dataMap = simulationManager.GetHistogram(chosenEntity, chosenProperty, id);
//                histogramController.setStage(popupStage, dataMap);
//                popupStage.setResizable(false);
//                popupStage.show();
//                break;
//            case "Consistency":
//                resultLabel.setText("Consistency: " + String.valueOf(simulationManager.getConsistency(chosenEntity, chosenProperty, id)));
//                resultLabel.setVisible(true);
//                break;
//            case "Average":
//                try {
//                    resultLabel.setText("Average: " + String.valueOf(simulationManager.getAverage(chosenEntity, chosenProperty, id)));
//                    resultLabel.setVisible(true);
//                } catch (RuntimeException e) {
//                    resultLabel.setText(e.getMessage());
//                    resultLabel.setVisible(true);
//                }
//                break;
//
//        }
//    }
//
//
//}

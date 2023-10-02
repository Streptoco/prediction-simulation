//package handler.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//
//
//public class ThreadQueueController {
//    private Stage stage;
//    @FXML
//    private AnchorPane mainAnchor;
//    @FXML
//    private Label numOfRunning;
//    @FXML
//    private Label numOfWaiting;
//    @FXML
//    private Label numOfDone;
//
//    public void setStage(Stage stage, SimulationManager manager) {
//        this.stage = stage;
//        numOfRunning.setText("Number of running simulations: " + manager.engine.getSimulationManager().getNumberOfRunningSimulations());
//        numOfDone.setText("Number of done simulations: " + manager.engine.getSimulationManager().getNumberOfDoneSimulations());
//        numOfWaiting.setText("Number of waiting simulations: " + manager.engine.getSimulationManager().getNumberOfWaitingSimulations());
//    }
//
//    private void closeQueue(ActionEvent event) {
//        stage.close();
//    }
//
//}

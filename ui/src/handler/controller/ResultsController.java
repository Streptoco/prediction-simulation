package handler.controller;

import engine.general.object.Engine;
import enginetoui.dto.basic.impl.SimulationStatusDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import tree.item.impl.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {
    private Engine engine;
    @FXML
    private TreeView treeView;
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

            }
        });
    }
}

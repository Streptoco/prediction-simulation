package handler.controller;

import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.RuleDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import tree.item.impl.*;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private TreeView treeView;

    private WorldDTO worldDTO;

    @FXML
    private TextArea mainTextArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainTextArea.setDisable(true);
        mainTextArea.setStyle("-fx-font-size: 24px");


        this.worldDTO = (WorldDTO) resources.getObject("World");

        WorldTreeItem worldTreeItem = new WorldTreeItem(worldDTO);

        RuleTreeFatherItem ruleTreeFatherItem = new RuleTreeFatherItem();
        EntitiesTreeFatherItem entitiesTreeFatherItem = new EntitiesTreeFatherItem();


        TerminationTreeItem terminationTreeItem = new TerminationTreeItem(worldDTO.termination);


        EnvironmentTreeItem environmentTreeItem = new EnvironmentTreeItem(worldDTO.environment);

        GridTreeItem gridTreeItem = new GridTreeItem(worldDTO.gridDTO);

        worldTreeItem.getChildren().setAll(ruleTreeFatherItem, entitiesTreeFatherItem, environmentTreeItem, terminationTreeItem, gridTreeItem);

        for (RuleDTO ruleDTO : worldDTO.getRules()) {
            RuleTreeItem newRule = new RuleTreeItem(ruleDTO);
            ruleTreeFatherItem.getChildren().add(newRule);
        }

        for (EntityDTO entity : worldDTO.getEntities()) {
            EntityTreeItem newEntity = new EntityTreeItem(entity);
            entitiesTreeFatherItem.getChildren().add(newEntity);
        }

        treeView.setShowRoot(false);
        treeView.setRoot(worldTreeItem);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainTextArea.clear();
            if (newValue != null) {
                // Check if the selected item is a RuleTreeItem or EntityTreeItem
                if (newValue instanceof RuleTreeItem) {
                    ((RuleTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof EntityTreeItem) {
                    ((EntityTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof TerminationTreeItem) {
                    ((TerminationTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof GridTreeItem) {
                    ((GridTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof PropertyTreeItem) {
                    ((PropertyTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof EnvironmentTreeItem) {
                    ((EnvironmentTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof ActionTreeItem) {
                    ((ActionTreeItem) newValue).ApplyText(mainTextArea);
                }
            }
        });
    }
}

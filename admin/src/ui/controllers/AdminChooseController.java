package ui.controllers;

import client.AdminClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tree.item.impl.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminChooseController implements Initializable {
    private AdminClient client;
    private File xmlFile;
    private List<WorldTreeItem> worldTreeItemList = new ArrayList<>();
    private WorldFatherTreeItem worldFatherTreeItem = new WorldFatherTreeItem();

    @FXML
    private TreeView simulationsTreeView;
    @FXML
    private TextArea mainTextArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
        // listener for tree view
        simulationsTreeView.setShowRoot(false);
        simulationsTreeView.setRoot(worldFatherTreeItem);

        simulationsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    public void onFileLoad(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        xmlFile = fileChooser.showOpenDialog(new Stage());
        client.uploadFile(xmlFile);
        // set up fictitious simulation
        WorldTreeItem worldTreeItem = new WorldTreeItem(client.getWorld());
        worldTreeItemList.add(worldTreeItem);
        worldFatherTreeItem.getChildren().add(worldTreeItem);
    }
}

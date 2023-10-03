package ui.controllers;

import client.UserClient;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import tree.item.impl.*;

import java.net.URL;
import java.util.*;

public class UserDetailsController implements Initializable {

    private UserClient client;
    private List<WorldTreeItem> worldTreeItemList = new ArrayList<>();
    private WorldFatherTreeItem worldFatherTreeItem = new WorldFatherTreeItem();
    private WorldTreeItem worldTreeItem;
    @FXML
    private TextArea mainTextArea;
    @FXML
    private TreeView simulationsTreeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (UserClient) resources.getObject("client");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    worldTreeItem = new WorldTreeItem(client.getWorld());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 100);

        worldTreeItemList.add(worldTreeItem);
        worldFatherTreeItem.getChildren().add(worldTreeItem);

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
}

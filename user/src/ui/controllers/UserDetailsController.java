package ui.controllers;

import client.UserClient;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import tree.item.impl.*;

import java.io.IOException;
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
        List<WorldDTO> worldDTOList;
        try {
            worldDTOList = client.getAllWorlds();
            worldDTOList.forEach(worldDTO -> {
                worldTreeItem = new WorldTreeItem(worldDTO);
                worldTreeItemList.add(worldTreeItem);
                worldFatherTreeItem.getChildren().add(worldTreeItem);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    boolean changed = false;
                    try {
                        WorldDTO currentDTO = client.getWorld();
                        if(currentDTO != null) {
                            worldTreeItem = new WorldTreeItem(currentDTO);
                            System.out.println("Get the world: " + currentDTO.worldName + " in version" + currentDTO.worldVersion);
                            if (worldTreeItemList.isEmpty()) {
                                worldTreeItemList.add(worldTreeItem);
                                worldFatherTreeItem.getChildren().add(worldTreeItem);
                            } else {
                                Iterator<WorldTreeItem> it = worldTreeItemList.iterator();
                                while (it.hasNext()) {
                                    WorldTreeItem treeItem = it.next();
                                    if (treeItem.getWorldName().equalsIgnoreCase(worldTreeItem.getWorldName())) {
                                        changed = true;
                                        if (worldTreeItem.getWorldVersion() > treeItem.getWorldVersion()) {
                                            it.remove();
                                            worldTreeItemList.add(worldTreeItem);
                                            worldFatherTreeItem.getChildren().add(worldTreeItem);
                                        }
                                    }
                                }
                                if (!changed) {
                                    worldTreeItemList.add(worldTreeItem);
                                    worldFatherTreeItem.getChildren().add(worldTreeItem);
                                }
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }, 0, 500);
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

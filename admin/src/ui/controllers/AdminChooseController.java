package ui.controllers;

import client.AdminClient;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tree.item.impl.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminChooseController implements Initializable {
    private AdminClient client;
    private File xmlFile;
    private List<WorldTreeItem> worldTreeItemList = new ArrayList<>();
    private WorldFatherTreeItem worldFatherTreeItem = new WorldFatherTreeItem();
    private WorldTreeItem worldTreeItem;
    private StringExpression labelTextBinding = null;
    @FXML
    private TreeView simulationsTreeView;
    @FXML
    private TextArea mainTextArea;
    @FXML
    private TextField filePathField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
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
                    boolean changed = false, toDelete = false;
                    WorldTreeItem objectToRemove = null;
                    try {
                        WorldDTO currentDTO = client.getWorld();
                        if (currentDTO != null) {
                            worldTreeItem = new WorldTreeItem(currentDTO);
                            if (worldTreeItemList.isEmpty()) {
                                worldTreeItemList.add(worldTreeItem);
                                worldFatherTreeItem.getChildren().add(worldTreeItem);
                            } else {
                                for (WorldTreeItem treeItem : worldTreeItemList) {
                                    if (treeItem.getWorldName().equalsIgnoreCase(worldTreeItem.getWorldName())) {
                                        changed = true;
                                        if (worldTreeItem.getWorldVersion() > treeItem.getWorldVersion()) {
                                            objectToRemove = treeItem;
                                            toDelete = true;
                                            worldTreeItemList.add(worldTreeItem);
                                            worldFatherTreeItem.getChildren().add(worldTreeItem);
                                        }
                                    }
                                }
                                if (toDelete) {
                                    //worldFatherTreeItem.getChildren().remove(objectToRemove);
                                    worldTreeItemList.remove(objectToRemove);
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
        }, 0, 2000);
        // listener for tree view
        simulationsTreeView.setShowRoot(false);
        simulationsTreeView.setRoot(worldFatherTreeItem);
        labelTextBinding = Bindings.concat("Please upload a file.");

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
//        WorldTreeItem worldTreeItem = new WorldTreeItem(client.getWorld());
//        worldTreeItemList.add(worldTreeItem);
//        worldFatherTreeItem.getChildren().add(worldTreeItem);
        labelTextBinding = Bindings.concat("Chosen file: ", xmlFile.getAbsolutePath());
        filePathField.textProperty().bind(labelTextBinding);
    }
}

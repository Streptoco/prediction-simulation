package ui.controllers;

import client.UserClient;
import enginetoui.dto.basic.RequestDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tree.item.impl.WorldTreeItem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserRequestController implements Initializable {
    @FXML
    private TextField amountRunField;

    @FXML
    private Button executeButton;

    @FXML
    private ComboBox simulationChooseMenu;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox terminationMenu;

    @FXML
    private TextField tickField;

    @FXML
    private TextField timeField;

    private UserClient client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (UserClient) resources.getObject("client");
        terminationMenu.getItems().clear();
        terminationMenu.getItems().add("Tick");
        terminationMenu.getItems().add("Time");
        terminationMenu.getItems().add("Tick and Time");
        terminationMenu.getItems().add("By user");
        simulationChooseMenu.getItems().clear();
        List<WorldTreeItem> worldTreeItemList = (List<WorldTreeItem>) resources.getObject("treeList");
        for (WorldTreeItem treeItem : worldTreeItemList) {
            simulationChooseMenu.getItems().add(treeItem.getWorldName());
        }
    }


    public void setComboMenuItemTermination(ActionEvent event) {
        timeField.setDisable(false);
        timeField.setDisable(false);
        if (terminationMenu.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Tick")) {
            timeField.setDisable(true);
            tickField.setDisable(false);
        } else if (terminationMenu.getSelectionModel().getSelectedItem().equals("Time")) {
            timeField.setDisable(false);
            tickField.setDisable(true);
                    } else if (terminationMenu.getSelectionModel().getSelectedItem().equals("By user")) {
            timeField.setDisable(true);
            tickField.setDisable(true);
        } else {
            timeField.setDisable(false);
            tickField.setDisable(false);
        }
    }

    public void setSimulations(ActionEvent event) {

    }

    public void createNewRequest(ActionEvent event) throws IOException {
        String worldName = (String) simulationChooseMenu.getSelectionModel().getSelectedItem();
        int numOfRuns = Integer.parseInt(amountRunField.getText());
        int ticks = Integer.parseInt(tickField.getText());
        int seconds = Integer.parseInt(timeField.getText());
        RequestDTO requestDTO = new RequestDTO(worldName, numOfRuns, ticks, seconds);
        client.newRequest(requestDTO);
    }
}

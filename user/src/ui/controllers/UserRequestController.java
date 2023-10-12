package ui.controllers;

import client.UserClient;
import enginetoui.dto.basic.RequestDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TableView requestTable;

    @FXML
    private TableColumn<RequestDTO, String> nameColumn;

    @FXML
    private TableColumn<RequestDTO, String> statusColumn;
    private ObservableList<RequestDTO> TableData;
    private UserClient client;
    private List<WorldTreeItem> worldTreeItemList;
    private boolean isChooseSim;
    private boolean isChooseTermination;
    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (UserClient) resources.getObject("client");
        TableData = (ObservableList<RequestDTO>) resources.getObject("requestList");
        username = (String) resources.getObject("username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("simulationName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestTable.setItems(TableData);
        terminationMenu.getItems().clear();
        terminationMenu.getItems().add("Tick");
        terminationMenu.getItems().add("Time");
        terminationMenu.getItems().add("Tick and Time");
        terminationMenu.getItems().add("By user");
        simulationChooseMenu.getItems().clear();
        worldTreeItemList = (List<WorldTreeItem>) resources.getObject("treeList");
        for (WorldTreeItem treeItem : worldTreeItemList) {
            simulationChooseMenu.getItems().add(treeItem.getWorldName());
        }
        isChooseSim = isChooseTermination = false;
    }


    public void setComboMenuItemTermination(ActionEvent event) {
        timeField.setDisable(false);
        tickField.setDisable(false);
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
        isChooseTermination = true;
        if (isChooseSim) {
            submitButton.setDisable(false);
        }
    }

    public void setSimulations(ActionEvent event) {
        isChooseSim = true;
        if (isChooseTermination) {
            submitButton.setDisable(false);
        }
    }

    public void createNewRequest(ActionEvent event) throws IOException {
        String worldName = (String) simulationChooseMenu.getSelectionModel().getSelectedItem();
        int seconds, ticks, numOfRuns;
        try {
            numOfRuns = Integer.parseInt(amountRunField.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            numOfRuns = 0;
        }
        try {
            ticks = Integer.parseInt(tickField.getText());
        } catch (NumberFormatException e) {
            ticks = Integer.MAX_VALUE;
        }
        try {
            seconds = Integer.parseInt(timeField.getText());
        } catch (NumberFormatException e) {
            seconds = Integer.MAX_VALUE;
        }
        RequestDTO requestDTO = new RequestDTO(worldName, numOfRuns, ticks, seconds, username);
        client.newRequest(requestDTO);
        TableData.add(requestDTO);
        requestTable.setItems(TableData);

        amountRunField.setText("");
        timeField.setText("");
        tickField.setText("");
        simulationChooseMenu.getSelectionModel().clearSelection();
        //terminationMenu.getSelectionModel().clearSelection();
        timeField.setDisable(true);
        tickField.setDisable(true);
        isChooseSim = isChooseTermination = false;
        submitButton.setDisable(true);
    }
}

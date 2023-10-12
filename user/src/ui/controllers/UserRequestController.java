package ui.controllers;

import client.UserClient;
import enginetoui.dto.basic.RequestDTO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import request.impl.AllocationRequest;
import tree.item.impl.WorldTreeItem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    private TableColumn<AllocationRequest, String> nameColumn;

    @FXML
    private TableColumn<AllocationRequest, String> statusColumn;
    private ObservableList<AllocationRequest> TableData;
    private UserClient client;
    private List<WorldTreeItem> worldTreeItemList;
    private boolean isSimulationChosen;
    private boolean isTerminationChosen;
    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (UserClient) resources.getObject("client");
        TableData = (ObservableList<AllocationRequest>) resources.getObject("requestList");
        username = (String) resources.getObject("username");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("simulationName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestTable.setItems(TableData);
        setTerminationMenu();
        worldTreeItemList = (List<WorldTreeItem>) resources.getObject("treeList");
        setSimulationChooseMenu();
        isSimulationChosen = isTerminationChosen = false;

        Thread TimerCheckUpdateInRequests = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            TableData.addAll(client.getAllRequests());
                            requestTable.setItems(TableData);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },0,500);
            }
        });
    }

    private void setTerminationMenu() {
        terminationMenu.getItems().clear();
        terminationMenu.getItems().add("Tick");
        terminationMenu.getItems().add("Time");
        terminationMenu.getItems().add("Tick and Time");
        terminationMenu.getItems().add("By user");
    }

    private void setSimulationChooseMenu() {
        simulationChooseMenu.getItems().clear();
        for (WorldTreeItem treeItem : worldTreeItemList) {
            simulationChooseMenu.getItems().add(treeItem.getWorldName());
        }
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
        isTerminationChosen = true;
        if (isSimulationChosen) {
            submitButton.setDisable(false);
        }
    }

    public void setSimulations(ActionEvent event) {
        isSimulationChosen = true;
        if (isTerminationChosen) {
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
//        TableData.add(requestDTO);
//        requestTable.setItems(TableData);

        amountRunField.setText("");
        timeField.setText("");
        tickField.setText("");
        simulationChooseMenu.getSelectionModel().clearSelection();
        //terminationMenu.getSelectionModel().clearSelection();
        timeField.setDisable(true);
        tickField.setDisable(true);
        isSimulationChosen = isTerminationChosen = false;
        submitButton.setDisable(true);
    }
}

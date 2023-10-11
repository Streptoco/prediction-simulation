package ui.controllers;

import client.AdminClient;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import request.impl.AllocationRequest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class AdminAllocationController implements Initializable {
    private AdminClient client;
    private ObservableList<AllocationRequest> data;
    @FXML
    private ListView<AllocationRequest> requestListView;
    @FXML
    private TextArea requestTextArea;
    @FXML
    private Button approveButton;
    @FXML
    private Button denyButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
        data = (ObservableList<AllocationRequest>) resources.getObject("AllocationData");
        try {
            List<AllocationRequest> allRequests = client.getAllRequests();
            data.addAll(allRequests);
            requestListView.setItems(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    AllocationRequest newRequest = client.getLatestRequest();
                    if (newRequest != null) {
                        Platform.runLater(() -> {
                            data.add(newRequest);
                            System.out.println("[AdminAllocationController] - [initialize]: " + newRequest);
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 500);

        requestListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("[AdminAllocationController] - [initialize]: Selected: " + newValue);
                    approveButton.setDisable(false);
                    denyButton.setDisable(false);
                    requestTextArea.setText(newValue.toString());
                    ;
                }
        );

    }


    public void approveRequest(ActionEvent event) throws IOException {
        requestListView.getSelectionModel().getSelectedItem().approveRequest();
        client.changeRequestStatus(requestListView.getSelectionModel().getSelectedItem());
        //requestListView.getSelectionModel().clearSelection();
        approveButton.setDisable(true);
        denyButton.setDisable(true);
    }

    public void denyRequest(ActionEvent event) throws IOException {
        requestListView.getSelectionModel().getSelectedItem().denyRequest();
        client.changeRequestStatus(requestListView.getSelectionModel().getSelectedItem());
        //requestListView.getSelectionModel().clearSelection();
        approveButton.setDisable(true);
        denyButton.setDisable(true);
    }
}

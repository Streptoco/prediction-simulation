package ui.controllers;

import client.AdminClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import request.impl.AllocationRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAllocationController implements Initializable {
    private AdminClient client;
    @FXML
    private ListView<AllocationRequest> requestListView;
    @FXML
    private TextArea requestTextArea;

    private ObservableList<AllocationRequest> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
        try {
            AllocationRequest ltsRequest = client.getLatestRequest();
            data.add(ltsRequest);
            requestListView.setItems(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        requestListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("Selected: " + newValue);
                    requestTextArea.setText(newValue.toString());;
                }
        );

    }


}

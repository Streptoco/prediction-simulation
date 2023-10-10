package ui.controllers;

import client.AdminClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private ListView<AllocationRequest> requestListView;
    @FXML
    private TextArea requestTextArea;

    private ObservableList<AllocationRequest> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
        try {
            List<AllocationRequest> ltsRequest = client.getAllRequests();
            data.addAll(ltsRequest);
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
                            System.out.println(newRequest);
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }, 0, 500);

        requestListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("Selected: " + newValue);
                    requestTextArea.setText(newValue.toString());
                    ;
                }
        );

    }


}

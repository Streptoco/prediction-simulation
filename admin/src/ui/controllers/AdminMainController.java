package ui.controllers;

import client.AdminClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class AdminMainController extends ResourceBundle implements Initializable {
    @FXML
    private Button allocationsButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button mngButton;

    @FXML
    private Label titleLabel;
    @FXML
    private VBox currentSceneHolder;
    private final AdminClient client = new AdminClient();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected Object handleGetObject(String key) {
        switch (key) {
            case "client":
                return client;
            default:
                return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    private void changeDynamicAdminChooseScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("admin-choose-component.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        VBox executionBox = fxmlLoader.load();
        AdminChooseController adminChooseController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(executionBox);
    }

    private void changeDynamicAdminAllocationScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("admin-allocation-component.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        VBox executionBox = fxmlLoader.load();
        AdminAllocationController adminAllocationController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(executionBox);
    }

    public void handleManagementButton(ActionEvent event) throws IOException {
        changeDynamicAdminChooseScreen();
    }


    public void handleAllocationButton(ActionEvent event) throws IOException {
        changeDynamicAdminAllocationScreen();
    }
}
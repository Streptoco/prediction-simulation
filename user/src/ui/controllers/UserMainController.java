package ui.controllers;

import client.UserClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tree.item.impl.WorldTreeItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class UserMainController extends ResourceBundle implements Initializable {
    @FXML
    private VBox currentSceneHolder;

    @FXML
    private Button executionButton;

    @FXML
    private Button requestButton;

    @FXML
    private Button resultsButton;

    @FXML
    private Button simDetailsButton;

    @FXML
    private Label usernameLabel;
    private final UserClient client = new UserClient();

    private List<WorldTreeItem> worldTreeItemList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    protected Object handleGetObject(String key) {
        switch (key) {
            case "client":
                return client;
            case "treeList":
                return worldTreeItemList;
            default:
                return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    private void changeDynamicDetailsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("user-details-component.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        VBox detailBox = fxmlLoader.load();
        UserDetailsController userDetailsController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(detailBox);
    }

    private void changeDynamicRequestScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("user-request-component.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        VBox detailBox = fxmlLoader.load();
        UserRequestController userRequestController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(detailBox);
    }


    public void handleSimDetailsButton(ActionEvent event) throws IOException {
        changeDynamicDetailsScreen();

    }

    public void handleRequestButton(ActionEvent event) throws IOException {
        changeDynamicRequestScreen();
    }
}

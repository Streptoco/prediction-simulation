package ui.controllers;

import client.UserClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import request.impl.AllocationRequest;
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
    private boolean isLoggedIn = false;
    private StringProperty userNameProperty;
    private final UserClient client = new UserClient();
    private List<WorldTreeItem> worldTreeItemList = new ArrayList<>();
    private ObservableList<AllocationRequest> TableData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameProperty = new SimpleStringProperty();
        loginPopup();
    }

    @Override
    protected Object handleGetObject(String key) {
        switch (key) {
            case "client":
                return client;
            case "treeList":
                return worldTreeItemList;
            case "requestList":
                return TableData;
            case "username":
                return getActualUsername();
            default:
                return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    private void loginPopup() {
        try {
            if (!isLoggedIn) {
                Stage popupStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("user-login-popup.fxml"));
                Parent root = loader.load();
                popupStage.setScene(new Scene(root, 350, 200));
                UserLoginController loginController = loader.getController();
                loginController.setStage(popupStage);
                popupStage.setResizable(false);
                popupStage.show();
                userNameProperty.bind(loginController.getUserName());
                usernameLabel.textProperty().bind(userNameProperty);
                this.isLoggedIn = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String getActualUsername() {
        String userName = "";
        if (userNameProperty != null) {
            int beginIndex = userNameProperty.getValue().indexOf(":");
            if (beginIndex != -1) {
                //Plus 2 because of the space after ":"
                userName = userNameProperty.getValue().substring(beginIndex + 2);
            }
        }
        return userName;
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

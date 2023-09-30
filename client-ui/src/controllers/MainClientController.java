package controllers;

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

public class MainClientController extends ResourceBundle implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    protected Object handleGetObject(String key) {
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    private void changeDynamicDetailsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("client-details-component.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        VBox detailBox = fxmlLoader.load();
        ClientDetailsController clientDetailsController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(detailBox);
    }

    public void handleSimDetailsButton(ActionEvent event) throws IOException {
        changeDynamicDetailsScreen();
    }
}

package ui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UserLoginController {
    private Stage stage;
    @FXML
    private TextField userNameTextField;
    @FXML
    private Button loginButton;
    private StringProperty userName;


    public void setStage(Stage stage) {
        this.stage = stage;
        userName = new SimpleStringProperty();
        stage.setAlwaysOnTop(true);
    }

    private void closeQueue(ActionEvent event) {
        stage.close();
    }
    @FXML
    private void getUsername(ActionEvent event) {
        this.userName.setValue("Username: " + this.userNameTextField.getText());
        stage.close();
    }

    public StringProperty getUserName() {
        return this.userName;
    }
}

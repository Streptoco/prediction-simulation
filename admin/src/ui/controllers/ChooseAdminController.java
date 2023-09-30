package ui.controllers;

import client.AdminClient;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseAdminController implements Initializable {
    private AdminClient client;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
    }

    public void onFileLoad(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        client.uploadFile(file);
    }
}

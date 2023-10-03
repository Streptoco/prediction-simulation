package ui.controllers;

import client.AdminClient;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminChooseController implements Initializable {
    private AdminClient client;
    private File xmlFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = (AdminClient) resources.getObject("client");
    }

    public void onFileLoad(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        xmlFile = fileChooser.showOpenDialog(new Stage());
        client.uploadFile(xmlFile);
    }
}

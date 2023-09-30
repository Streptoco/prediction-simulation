package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseAdminController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onFileLoad(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
    }
}

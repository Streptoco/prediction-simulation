package handler.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    @FXML
    private Button loadFileButton;
    @FXML
    public Button detailsButton;
    @FXML
    private Button newExecutionButton;
    @FXML
    private Button resultsButton;
    @FXML
    private Button queueManagementButton;
    @FXML
    private TextField textField;
    @FXML
    private Label programLabel;

    private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();
    @FXML
    public void initialize() {
        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        textField.textProperty().bind(labelTextBinding);
    }

    @FXML
    public void openFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            selectedFile.set(file);
        }

        // TODO: keep operating

    }
}

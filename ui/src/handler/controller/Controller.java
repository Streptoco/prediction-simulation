package handler.controller;

import engine.general.object.Engine;
import enginetoui.dto.basic.WorldDTO;
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
import testing.testDTO;

import javax.xml.bind.JAXBException;
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
    private Engine engine;
    private WorldDTO currentWorldDTO;

    private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();
    @FXML
    public void initialize() {
        engine = new Engine();
        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        textField.textProperty().bind(labelTextBinding);
    }

    @FXML
    public void openFileChooser(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            selectedFile.set(file);
        }

        //engine.addSimulation(selectedFile.asString().get());
        //currentWorldDTO = engine.getWorldDTO();
        //textField.textProperty().set(currentWorldDTO.GetSimulationDateString());

        // TESTING PURPOSES:
        testDTO test = new testDTO();
        textField.textProperty().set(test.entityName);



    }
}

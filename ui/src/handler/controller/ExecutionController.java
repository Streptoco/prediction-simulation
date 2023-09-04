package handler.controller;

import engine.general.object.Engine;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import uitoengine.filetransfer.FileTransferDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class ExecutionController implements Initializable {
    private FileTransferDTO fileTransferDTO;
    private WorldDTO world;

    @FXML
    ComboBox<EntityDTO> entityComboBox;
    @FXML
    ComboBox<PropertyDTO> propertyComboBox;
    @FXML
    Label entityComboBoxLabel;
    @FXML
    Label propertyComboBoxLabel;
    @FXML
    Spinner<Integer> entityComboBoxSpinner;
    @FXML
    Button clearButton;
    @FXML
    Button runButton;
    @FXML
    CheckBox randomize;
    @FXML
    Label statusLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.world = (WorldDTO) resources.getObject("World");
        this.entityComboBox = new ComboBox<>();
        this.propertyComboBox = new ComboBox<>();

        for (EntityDTO entityDTO : world.getEntities()) {
            entityComboBox.getItems().add(entityDTO); // add all entities to combo box
        }

        for (PropertyDTO propertyDTO : world.environment.propertyDTOs) {
            propertyComboBox.getItems().add(propertyDTO); // add all properties to combo box
        }

        entityComboBoxLabel.textProperty().bind(entityComboBox.accessibleTextProperty()); // Don't know if right
        propertyComboBoxLabel.textProperty().bind(propertyComboBox.accessibleTextProperty());

        propertyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Perform your desired action here with the selected DTO (newValue)
                System.out.println("Selected DTO: " + newValue.getName());
            }
        }); // LISTENER FOR WHEN PROPERTY VALUE CHANGES



    }
}

package handler.controller;

import engine.general.object.Engine;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.FileTransferDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class ExecutionController implements Initializable {
    private FileTransferDTO fileTransferDTO;
    private WorldDTO world;
    private Engine engine;
    private int currentSimulationID;
    private int currentPopulation = 0;
    @FXML
    Button setPopulation;
    @FXML
    Label entityMaxPopulationLabel;
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
    Slider entitySlider;
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
        this.engine = (Engine) resources.getObject("Engine");
        this.currentSimulationID = (int) resources.getObject("SimulationID");

        for (EntityDTO entityDTO : world.getEntities()) {
            entityComboBox.getItems().add(entityDTO); // add all entities to combo box
        }

        for (PropertyDTO propertyDTO : world.environment.propertyDTOs) {
            propertyComboBox.getItems().add(propertyDTO); // add all properties to combo box
        }

//        entityComboBoxLabel.textProperty().bind(entityComboBox.accessibleTextProperty()); // Don't know if right
//        propertyComboBoxLabel.textProperty().bind(propertyComboBox.accessibleTextProperty());

        propertyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Perform your desired action here with the selected DTO (newValue)
                System.out.println("Selected DTO: " + newValue.getName());
            }
        }); // LISTENER FOR WHEN PROPERTY VALUE CHANGES



    }

    public void selectEntity(ActionEvent actionEvent) {
        // TODO: maybe in here we want to create a DTO to the engine OR use that API Afik made.
        // TODO: get the information from the world DTO regarding limitations.
        // TODO: handle exceedings of the population size (it currently breaks the whole shabang)
        entityComboBoxLabel.setText("Range: From " + 0 + " To " + ((world.gridDTO.cols * world.gridDTO.rows) - currentPopulation));
        entitySlider.setMax((world.gridDTO.cols * world.gridDTO.rows) - currentPopulation);
    }

    public void sliderValue(MouseEvent mouseEvent) {
        entityMaxPopulationLabel.setText(String.valueOf((int)entitySlider.getValue()));
    }

    public void updatePopulation(ActionEvent actionEvent) {
        EntityAmountDTO entityAmountDTO = new EntityAmountDTO(entityComboBox.getSelectionModel().getSelectedItem().toString(),(int)entitySlider.getValue());
        engine.setupPopulation(entityAmountDTO,currentSimulationID);
        currentPopulation += (int)entitySlider.getValue();
    }
}

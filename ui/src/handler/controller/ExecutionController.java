package handler.controller;

import engine.general.object.Engine;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.PropertyDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private int amountOfEntities = 0;
    @FXML
    Button setPopulation;
    @FXML
    Slider propertySlider;
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
        runButton.setDisable(true);
        entitySlider.setDisable(true);

        for (EntityDTO entityDTO : world.getEntities()) {
            entityComboBox.getItems().add(entityDTO); // add all entities to combo box
            amountOfEntities++;
        }

        for (PropertyDTO propertyDTO : world.environment.propertyDTOs) {
            propertyComboBox.getItems().add(propertyDTO); // add all properties to combo box
        }

        propertyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Perform your desired action here with the selected DTO (newValue)
                System.out.println("Selected DTO: " + newValue.getName());
            }
        }); // LISTENER FOR WHEN PROPERTY VALUE CHANGES

        entitySlider.valueProperty().addListener((ObservableValue<? extends Number> num, Number oldVal, Number newVal) -> {
            int intValue = newVal.intValue();
            entityMaxPopulationLabel.setText("Population: " + String.valueOf(intValue));
        });

        propertySlider.valueProperty().addListener((ObservableValue<? extends Number> num, Number oldVal, Number newVal) -> {
            int intValue = newVal.intValue();
            propertyComboBoxLabel.setText("Value: " + String.valueOf(intValue));
        });

    }

    public void selectEntity(ActionEvent actionEvent) {
        // TODO: maybe in here we want to create a DTO to the engine OR use that API Afik made.
        // TODO: get the information from the world DTO regarding limitations.
        // TODO: handle exceedings of the population size (it currently breaks the whole shabang)
        entityComboBoxLabel.setText("Range: From " + 0 + " To " + ((world.gridDTO.cols * world.gridDTO.rows) - currentPopulation));
        entitySlider.setMax((world.gridDTO.cols * world.gridDTO.rows) - currentPopulation);
        entitySlider.setDisable(false);
    }

    public void selectProperty(ActionEvent actionEvent) {
        propertySlider.setMin(propertyComboBox.getSelectionModel().getSelectedItem().from);
        propertySlider.setMax(propertyComboBox.getSelectionModel().getSelectedItem().to);
        statusLabel.setText("Now adjusting: " + propertyComboBox.getSelectionModel().getSelectedItem().getName());
    }

    public void updatePopulation(ActionEvent actionEvent) {
        if (entityComboBox.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        EntityAmountDTO entityAmountDTO = new EntityAmountDTO(entityComboBox.getSelectionModel().getSelectedItem().toString(),(int)entitySlider.getValue());
        engine.setupPopulation(entityAmountDTO,currentSimulationID);
        currentPopulation += (int)entitySlider.getValue();
        entitySlider.setDisable(true);
        entityComboBox.getItems().remove(entityComboBox.getSelectionModel().getSelectedItem());
        if ((--amountOfEntities) == 0) {
            entityComboBox.setDisable(true);
            entitySlider.setDisable(true);
            entityComboBoxLabel.setText("No more entities to define!");
            entityMaxPopulationLabel.setText("Population: 0");
        }
        // TODO: add even handler for when there's no longer things in the combobox.
    }
}

package handler.controller;

import engine.general.object.Engine;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class MainController extends ResourceBundle implements Initializable {

    NewXMLReader xmlReader = new NewXMLReader();

    @FXML
    private HBox currentSceneHolder;
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

    public MainController() throws JAXBException {
        System.out.println("hey lol");
    }

    @Override
    protected Object handleGetObject(String key) {
        switch (key) {
            case "World":
                return currentWorldDTO;
            default:
                return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();

    @FXML
    public void openFileChooser(ActionEvent event) throws JAXBException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            selectedFile.set(file);
            engine.loadWorld(selectedFile.getValue().getAbsolutePath());
            {
                //TODO: This piece of code shouldn't be here, this is the part of the XML reading and it should be on the start of the simulation
                int simID = engine.setupSimulation();
                currentWorldDTO = engine.getWorldDTO(simID);
            }
            StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
            textField.textProperty().bind(labelTextBinding);
        }
    }

    public void selectItemTermination() {
        System.out.println("This is a test to see if this works.");
        // TODO: generify somethings that we could show in the scene
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = new Engine();

//        try {
//            //engine.addSimulation("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\tests\\ex2\\test-xml.xml");
//            engine.addSimulation(selectedFile.getValue().getAbsolutePath());
//        } catch (JAXBException e) {
//            throw new RuntimeException(e);
//        }
//
//        currentWorldDTO = engine.getWorldDTO();
//
//        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
//        textField.textProperty().bind(labelTextBinding);
//        // TODO: make this relevant lel
    }


    private void ChangeDynamicDetailsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("details-fxml.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        BorderPane detailsBox = fxmlLoader.load();
        DetailsController detailsController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(detailsBox);
    }

    private void ChangeDynamicExecutionScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("execution-fxml.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        BorderPane executionBox = fxmlLoader.load();
        ExecutionController executionController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(executionBox);
    }

    public void handleDetailsButton(ActionEvent actionEvent) throws IOException {
        ChangeDynamicDetailsScreen();
    }

    public void handleExecutionButton(ActionEvent actionEvent) throws IOException {
        ChangeDynamicExecutionScreen();
    }
}
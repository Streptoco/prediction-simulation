package handler.controller;

import engine.general.object.Engine;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.application.Platform;
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
import uitoengine.filetransfer.EntityAmountDTO;
import uitoengine.filetransfer.PropertyInitializeDTO;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController extends ResourceBundle implements Initializable {
    private int currentSimulation;
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
    private SimulationManager simulationManager;
    private WorldDTO currentWorldDTO;

    public MainController() throws JAXBException {
        System.out.println("hey lol");
    }

    @Override
    protected Object handleGetObject(String key) {
        switch (key) {
            case "World":
                return currentWorldDTO;
            case "Engine":
                return engine;
            case "SimulationID":
                return currentSimulation;
            case "SimulationManager":
                return simulationManager;
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
                // TODO: This piece of code shouldn't be here, this is the part of the XML reading and it should be on the start of the simulation
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
        simulationManager = new SimulationManager(engine);

        engine.loadWorld("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\simulation-engine\\TestFiles\\ex2-virus-modified-3.xml");
        try {
            currentSimulation = engine.setupSimulation();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        currentWorldDTO = engine.getWorldDTO(currentSimulation);
        // TODO: this is a test, don't touch

        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        textField.textProperty().bind(labelTextBinding);
//        // TODO: make this relevant lel

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    simulationManager.update();
                });
            }
        },0,100);
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

    private void ChangeDynamicResultsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFXML = getClass().getResource("results-fxml.fxml");
        fxmlLoader.setLocation(mainFXML);
        fxmlLoader.setResources(this);
        BorderPane resultsBox = fxmlLoader.load();
        ResultsController resultsController = fxmlLoader.getController();
        currentSceneHolder.getChildren().clear();
        currentSceneHolder.getChildren().add(resultsBox);
    }

    public void handleDetailsButton(ActionEvent actionEvent) throws IOException {
        ChangeDynamicDetailsScreen();
    }

    public void handleExecutionButton(ActionEvent actionEvent) throws IOException {
        ChangeDynamicExecutionScreen();
    }

    public void handleResultsButton(ActionEvent actionEvent) throws IOException {
        ChangeDynamicResultsScreen();
    }
}
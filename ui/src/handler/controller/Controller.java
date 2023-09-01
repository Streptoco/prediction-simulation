package handler.controller;

import engine.general.object.Engine;
import engine.general.object.Rule;
import engine.general.object.World;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.WorldDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import testing.testDTO;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    NewXMLReader xmlReader = new NewXMLReader();
    @FXML
    private TreeView treeView;
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

    public Controller() throws JAXBException {
        System.out.println("hey lol");
    }

    private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();

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

    public void selectItem() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = new Engine();
        try {
            engine.addSimulation("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\tests\\ex2\\test-xml.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        currentWorldDTO = engine.getWorldDTO();
        System.out.println("hey lol");

        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        textField.textProperty().bind(labelTextBinding);
        // TODO: make this relevant lel




        // branches should actually be of the same type of DTO, and just hold different values.
        // getValue returns getters, should I just hold strings? then I wouldn't be able to show properties of DTO.


//        TreeItem<String> rootItem = new TreeItem<>("Files");
//
//        TreeItem<String> branchItem1 = new TreeItem<>("Pictures");
//
//        TreeItem<String> branchItem2 = new TreeItem<>("Music");
//
//        TreeItem<String> branchItem3 = new TreeItem<>("Videos");
//
//        TreeItem<String> leafItem1 = new TreeItem<>("Pic1");
//
//        TreeItem<String> leafItem2 = new TreeItem<>("Pic2");
//
//        TreeItem<String> leafItem3 = new TreeItem<>("Video1");
//
//        TreeItem<String> leafItem4 = new TreeItem<>("Video2");
//
//        TreeItem<String> leafItem5 = new TreeItem<>("Music_song1");
//
//        TreeItem<String> leafItem6 = new TreeItem<>("Music_song2");
//
//        branchItem1.getChildren().setAll(leafItem1,leafItem2);
//
//        branchItem2.getChildren().setAll(leafItem5,leafItem6);
//
//        branchItem3.getChildren().setAll(leafItem3,leafItem4);
//
//        rootItem.getChildren().addAll(branchItem1,branchItem2,branchItem3);
//
//
//        treeView.setShowRoot(false);
//        treeView.setRoot(rootItem);
    }
}

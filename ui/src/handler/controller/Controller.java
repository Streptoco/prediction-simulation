package handler.controller;

import engine.general.object.Engine;
import engine.general.object.Environment;
import engine.xml.NewXMLReader;
import enginetoui.dto.basic.impl.EntityDTO;
import enginetoui.dto.basic.impl.RuleDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tree.item.impl.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    NewXMLReader xmlReader = new NewXMLReader();

    @FXML
    private TextArea mainTextArea;
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
    }

    public void selectItemTermination() {
        System.out.println("This is a test to see if this works.");
        // TODO: generify somethings that we could show in the scene
    }

    public void selectItemRule() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engine = new Engine();
        mainTextArea.setDisable(true);
        mainTextArea.setStyle("-fx-font-size: 24px");
        try {
            engine.addSimulation("D:\\MISC\\תואר\\Java\\ex1\\predictions-1\\tests\\ex2\\test-xml.xml");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        currentWorldDTO = engine.getWorldDTO();

        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        textField.textProperty().bind(labelTextBinding);
        // TODO: make this relevant lel

        WorldTreeItem worldTreeItem = new WorldTreeItem(currentWorldDTO);
        RuleTreeFatherItem ruleTreeFatherItem = new RuleTreeFatherItem();
        EntitiesTreeFatherItem entitiesTreeFatherItem = new EntitiesTreeFatherItem();


        TerminationTreeItem terminationTreeItem = new TerminationTreeItem(currentWorldDTO.termination);


        EnvironmentTreeItem environmentTreeItem = new EnvironmentTreeItem(currentWorldDTO.environment);

        GridTreeItem gridTreeItem = new GridTreeItem(currentWorldDTO.gridDTO);

        worldTreeItem.getChildren().setAll(ruleTreeFatherItem, entitiesTreeFatherItem, environmentTreeItem, terminationTreeItem, gridTreeItem);

        for (RuleDTO ruleDTO : currentWorldDTO.getRules()) {
            RuleTreeItem newRule = new RuleTreeItem(ruleDTO);
            ruleTreeFatherItem.getChildren().add(newRule);
        }

        for (EntityDTO entity : currentWorldDTO.getEntities()) {
            EntityTreeItem newEntity = new EntityTreeItem(entity);
            entitiesTreeFatherItem.getChildren().add(newEntity);
        }

        treeView.setRoot(worldTreeItem);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mainTextArea.clear();
            if (newValue != null) {
                // Check if the selected item is a RuleTreeItem or EntityTreeItem
                if (newValue instanceof RuleTreeItem) {
                    ((RuleTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof EntityTreeItem) {
                    ((EntityTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof TerminationTreeItem) {
                    ((TerminationTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof GridTreeItem) {
                    ((GridTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof PropertyTreeItem) {
                    ((PropertyTreeItem) newValue).ApplyText(mainTextArea);
                } else if (newValue instanceof EnvironmentTreeItem) {
                    ((EnvironmentTreeItem) newValue).ApplyText(mainTextArea);
                }
            }
        });


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

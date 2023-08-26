package component.test;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class testController {
    @FXML
    public Button button;
    public Label label;

    private ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();

    public ObjectProperty<File> selectedFileProperty() {
        return selectedFile;
    }

    @FXML
    public void showTest(ActionEvent event) {
        System.out.println("Code that is invisible to the user");
        button.setText("Stop touching me");
    }
    @FXML
    public void initialize() {
        StringExpression labelTextBinding = Bindings.concat("Chosen file: ", selectedFile.asString());
        label.textProperty().bind(labelTextBinding);
    }
    @FXML
    public void openFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            selectedFile.set(file);
        }


    }
}

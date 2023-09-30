import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class AdminDriver extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Prediction - Admin Panel");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("admin.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

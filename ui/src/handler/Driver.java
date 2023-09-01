package handler;

import handler.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Driver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions - Simulator");

        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("predictions-ui-2.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
        Controller controller = new Controller();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

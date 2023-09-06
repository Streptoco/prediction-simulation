package handler;

import handler.controller.MainController;
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
        URL url = getClass().getResource("main-controller.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
//        MainController mainController = new MainController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

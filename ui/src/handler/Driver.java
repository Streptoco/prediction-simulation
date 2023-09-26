package handler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Hi Roni Macaroni
 * Hoped you enjoyed London and you are ready for Targil3
 * There are a few things that I have noticed that need to be done and I don't think I will finish them
 *     1. The ability to re-run a simulation -
 *          Somehow we need to save the env properties and entities population in case we want to rerun the exact simulation,
 *          but I couldn't figure out how to do it
 *     2. All the statistic things -
 *          I built the base of some things at screen 3 of the simulation result, but I couldn't figure out what the hell is the hell is consistency shit,
 *          and we need to notify somehow the user that a simulation is done
 *     3. The EngineWrapper now is an example of using the api of engine, how to create and control the simulations, hope it's clear enough and if you have any questions don't hesitate asking
 *     4. I added the xml files I worked with, you can try and modify them and see if the engine is working as expected
 *
 **/
public class Driver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Predictions - Simulator");

        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("main-controller.fxml");
        loader.setLocation(url);
        Parent root = loader.load();
        //MainController mainController = new MainController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

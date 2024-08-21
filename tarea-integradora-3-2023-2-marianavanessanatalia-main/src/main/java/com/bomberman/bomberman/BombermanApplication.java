package com.bomberman.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main class of the application.
 */
public class BombermanApplication extends Application {

    /**
     * Starts the application.
     *
     * @param stage The stage for the application.
     * @throws IOException If the start-view.fxml file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BombermanApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 600);
        stage.setTitle("BOMB-MOON");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}
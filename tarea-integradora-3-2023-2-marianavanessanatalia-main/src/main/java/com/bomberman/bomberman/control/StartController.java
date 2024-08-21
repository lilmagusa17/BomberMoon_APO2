package com.bomberman.bomberman.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for StartView, handling user input and screen rendering.
 */

public class StartController {

    @FXML
    private ImageView startBtn;

    @FXML
    private AnchorPane ap;

    public Stage stage;
    public Scene scene;
    public Parent root;


    /**
     * Initializes the controller with the provided URL and ResourceBundle.
     *
     * @param event The location used to resolve relative paths for the root object, or null if the location is not known.
     */
    @FXML
    public void openGameView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/bomberman/bomberman/game-view.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ap.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}

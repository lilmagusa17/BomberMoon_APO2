package com.bomberman.bomberman.control;

import com.bomberman.bomberman.screens.ScreenLose;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for ScreenLose, handling user input and screen rendering.
 */

public class ScreenLoseController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas header;

    @FXML
    private Button again;

    @FXML
    private Button exit;


    private GraphicsContext graphicsContext;

    private ScreenLose screenLose;


    /**
     * Initializes the controller with the provided URL and ResourceBundle.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources specific to this controller, or null if no resources are needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.graphicsContext=this.canvas.getGraphicsContext2D();

        this.screenLose = new ScreenLose(this.canvas, this.header);
        this.screenLose.paint();

        initActions();

        new Thread(
                ()->{
                    while (true) {
                        Platform.runLater( () -> {
                            paint();

                        });

                        try {

                            Thread.sleep(200);

                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }


    /**
     * Initializes the actions to be performed on button clicks.
     */
    public void initActions() {
        this.again.setOnAction((event) -> {
            System.out.println("play again");
            returnToStartView(event);

        });

        this.exit.setOnAction((event) -> {
            System.out.println("exit");
            closeGameView();
            finishGame(event);
        });
    }



    /**
     * Closes the game view.
     */
    public void closeGameView() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }



    /**
     * Repaints the canvas based on the current state of ScreenLose.
     */
    public void paint(){
        screenLose.paint();
    }

    /**
     * Returns to the start view when the "Play Again" button is pressed.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    public void returnToStartView(ActionEvent event) {
        //this.screenLose.setOnButtonPressed(event);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bomberman/bomberman/start-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) canvas.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            canvas.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Finishes the game and exits the application when the "Exit" button is pressed.
     *
     * @param event The ActionEvent triggered by the button press.
     */
    public void finishGame(ActionEvent event) {
        //this.screenLose.setOnButtonPressed(event);
        Platform.exit();
    }

    /**
     * Gets the "Play Again" button.
     *
     * @return The "Play Again" button.
     */
    public Button getAgain() {
        return again;
    }

    /**
     * Gets the "Exit" button.
     *
     * @return The "Exit" button.
     */
    public Button getExitButton() {
        return exit;
    }
}


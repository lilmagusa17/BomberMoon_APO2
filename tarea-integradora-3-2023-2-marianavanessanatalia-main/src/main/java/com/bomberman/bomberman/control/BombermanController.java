package com.bomberman.bomberman.control;

import com.bomberman.bomberman.screens.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller class for the Bomberman game, handling user input and screen transitions.
 */
public class BombermanController implements Initializable {

    @FXML
    private Canvas canvas;


    @FXML
    private Canvas headerCanvas;


    private GraphicsContext graphicsContext;

    private GraphicsContext gcHeader;
    private BaseScreen currentScreen;
    private ScreenA screenA;
    private ScreenB screenB;
    private ScreenC screenC;
    private ScreenLose screenLose;

    private ScreenWin screenWin;

    private boolean loseScreenDisplayed = false;

    private boolean winScreenDisplayed = false;


    /**
     * Initializes the controller with the provided URL and ResourceBundle.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources specific to this controller, or null if no resources are needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.canvas != null && this.headerCanvas != null) {
            this.graphicsContext = this.canvas.getGraphicsContext2D();
            this.gcHeader = this.headerCanvas.getGraphicsContext2D();
           this.screenLose = new ScreenLose(this.canvas, this.headerCanvas);
            this.screenWin = new ScreenWin(this.canvas, this.headerCanvas);

            this.screenA = new ScreenA(this.canvas, this.headerCanvas);
            this.screenB = new ScreenB(this.canvas, this.headerCanvas);
            this.screenC = new ScreenC(this.canvas, this.headerCanvas);

            this.currentScreen = this.screenA;
            this.currentScreen.paint();


            initActions();

            new Thread(() -> {
                while (!loseScreenDisplayed || !winScreenDisplayed) {
                    Platform.runLater(() -> paint());

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } else {
            System.err.println("Error: Canvas or headerCanvas is null.");
        }

    }

    /**
     * Initializes the actions to be performed on key presses and releases.
     */
    public void initActions(){
        this.canvas.setOnKeyPressed(
                (event) -> {
                    //System.out.println("Key Pressed: " + event.getCode());
                    this.currentScreen.setOnKeyPressed(event);

                    if (currentScreen.isDead()) {
                        showLoseScreen();
                    }

                    if(currentScreen instanceof ScreenC && currentScreen.isInDoor()){
                        showWinScreen();
                    }
                }
        );

        this.canvas.setOnKeyReleased(
                (event) -> {
                    //System.out.println("Key Released: " + event.getCode());
                    this.currentScreen.setOnKeyReleased(event);
                }
        );
    }
    /**
     * Repaints the canvas based on the current game state.
     */
    public void paint(){

        changeScreen();
        showLoseScreen();

        if (!(currentScreen instanceof ScreenLose)) {
            currentScreen.paint();
        }

    }

    /**
     * Changes the current screen based on specific conditions.
     */
    public void changeScreen(){
        if(currentScreen.isInDoor() && currentScreen instanceof ScreenA){
            this.currentScreen=this.screenB;


        }else if(currentScreen.isInDoor() && currentScreen instanceof ScreenB){
            this.currentScreen=this.screenC;

        }  else if (currentScreen.isInDoor() && currentScreen instanceof ScreenC && !currentScreen.isDead()) {

            showWinScreen();
        }

    }


    /**
     * Displays the lose screen when the avatar is dead.
     */
    public void showLoseScreen() {

        if (currentScreen.isDead() && !loseScreenDisplayed) {
            loseScreenDisplayed = true;

            closeGameView();

            Platform.runLater(() -> {
                Stage loseStage = new Stage();
                loseStage.initModality(Modality.APPLICATION_MODAL);
                loseStage.setTitle("Game Over");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bomberman/bomberman/screenLose.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                Scene scene = new Scene(root);
                loseStage.setScene(scene);

                loseStage.show();
            });
        }
    }

    /**
     * Closes the game view.
     */
    public void closeGameView() {
        Stage stage = (Stage) canvas.getScene().getWindow();
        stage.close();
    }


    /**
     * Displays the win screen when the avatar reaches the exit door.
     */
    public void showWinScreen() {
        if (currentScreen.isInDoor() && currentScreen instanceof ScreenC && !winScreenDisplayed) {
            winScreenDisplayed = true;

            closeGameView();

            Platform.runLater(() -> {
                Stage loseStage = new Stage();
                loseStage.initModality(Modality.APPLICATION_MODAL);
                loseStage.setTitle("Game Over");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bomberman/bomberman/screenWin.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                Scene scene = new Scene(root);
                loseStage.setScene(scene);

                loseStage.show();
            });
        }
    }


}
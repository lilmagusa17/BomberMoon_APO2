package com.bomberman.bomberman.control;
import com.bomberman.bomberman.screens.ScreenA;
import com.bomberman.bomberman.screens.ScreenB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for ScreenB, handling user input and screen rendering.
 */
public class ScreenBController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Canvas headerCanvas;

    private GraphicsContext graphicsContext;

    private GraphicsContext gcHeader;

    private ScreenB screenB;

    /**
     * Initializes the controller with the provided URL and ResourceBundle.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources specific to this controller, or null if no resources are needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.gcHeader=this.headerCanvas.getGraphicsContext2D();

        this.screenB = new ScreenB(this.canvas, this.headerCanvas);//aqui solucionamos toda la inyeccion de dependencia
        this.screenB.paint();
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
     * Initializes the actions to be performed on key presses and releases.
     */
    public void initActions(){
        this.canvas.setOnKeyPressed(
                (event) -> {
                    System.out.println("Key Pressed: " + event.getCode());
                    this.screenB.setOnKeyPressed(event);
                }
        );

        this.canvas.setOnKeyReleased(
                (event) -> {
                    System.out.println("Key Released: " + event.getCode());
                    this.screenB.setOnKeyReleased(event);
                }
        );
    }



    /**
     * Repaints the canvas based on the current state of ScreenB.
     */
    public void paint(){
        screenB.paint();
    }
}

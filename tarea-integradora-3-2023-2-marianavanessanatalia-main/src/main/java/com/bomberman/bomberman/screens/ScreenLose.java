
package com.bomberman.bomberman.screens;

import com.bomberman.bomberman.control.ScreenLoseController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the screen displayed when the player loses the game.
 */
public class ScreenLose extends BaseScreen {

    private Canvas canvas;
    private Canvas header;

    private GraphicsContext graphicsContext;

    private Image image;



    /**
     * Initializes a new instance of the ScreenLose class with the specified main canvas and header canvas.
     *
     * @param canvas The main canvas for the lose screen.
     * @param header The canvas for displaying additional information (e.g., lives).
     */
    public ScreenLose(Canvas canvas, Canvas header) {
        super(canvas, header);
        this.canvas = canvas;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.header = header;

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //color the background
        graphicsContext.setFill(javafx.scene.paint.Color.BLACK);

        this.image = new Image(getClass().getResourceAsStream("/com/bomberman/bomberman/img/background/LoseScreen.png"));

    }

    /**
     * Paints the content of the ScreenLose.
     */
    @Override
    public void paint() {

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());

    }

}



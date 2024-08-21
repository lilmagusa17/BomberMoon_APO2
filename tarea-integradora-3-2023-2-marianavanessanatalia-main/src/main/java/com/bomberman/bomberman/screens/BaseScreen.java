package com.bomberman.bomberman.screens;

import com.bomberman.bomberman.model.Bomberman;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

/**
 * Represents the base class for game screens.
 */
public abstract class BaseScreen {

    private Canvas canvas;
    private Canvas header;
    private GraphicsContext graphicsContext;

    private GraphicsContext gcHeader;
    private Bomberman bomberman;

    private ArrayList<Image> lives;

    /**
     * Initializes a new instance of the BaseScreen class with the specified canvas and header.
     *
     * @param canvas The main canvas for the game screen.
     * @param header The canvas for displaying additional information (e.g., lives).
     */
    public BaseScreen(Canvas canvas, Canvas header){//recibe canvas por inyeccion de dependencia (patron de dise;o, asociacion entre el Screen y Canvas)
        this.canvas = canvas;
        this.header = header;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
        this.gcHeader = this.header.getGraphicsContext2D();
        this.bomberman = new Bomberman(this.canvas, this.header);

    }

    /**
     * Draws the content of the game screen.
     */
    public void paint(){

    }

    /**
     * Sets the key event handler for key presses.
     *
     * @param event The key event to handle.
     */
    public void setOnKeyPressed(KeyEvent event) {
        bomberman.setOnKeyPressed(event);
    }

    /**
     * Sets the key event handler for key releases.
     *
     * @param event The key event to handle.
     */
    public void setOnKeyReleased(KeyEvent event){
        bomberman.setOnKeyReleased(event);
    }


    /**
     * Checks if Bomberman is in the door (exit).
     *
     * @return True if Bomberman is in the door, false otherwise.
     */
    public boolean isInDoor(){
        System.out.println(bomberman.isInDoor());
        return bomberman.isInDoor();
    }

    /**
     * Checks if Bomberman is dead.
     *
     * @return True if Bomberman is dead, false otherwise.
     */
    public boolean isDead(){
        return bomberman.isDead();
    }

    /**
     * Gets the main canvas for the game screen.
     *
     * @return The main canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }


}

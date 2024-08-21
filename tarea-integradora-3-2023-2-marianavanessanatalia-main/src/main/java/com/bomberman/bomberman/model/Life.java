package com.bomberman.bomberman.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a life icon in the game.
 */
public class Life {
    private Image heart;
    private Position position;
    private Canvas header;
    private boolean isActive;
    private GraphicsContext graphicsContext; //contexto grafico

    /**
     * Initializes a new instance of the Life class.
     *
     * @param header   The canvas for the header where the life icon will be displayed.
     * @param position The position of the life icon.
     */
    public Life(Canvas header, Position position) {
        this.position = position;
        this.header = header;
        this.graphicsContext = this.header.getGraphicsContext2D();
        this.isActive = true;

        try {
            this.heart = new Image(getClass().getResourceAsStream("/com/bomberman/bomberman/img/btnIcons/heart1.png"), 50, 50, false, false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /**
     * Paints the life icon on the canvas if the life is still active.
     */
    public void paint(){
        if (isAlive()){
            this.graphicsContext.drawImage(heart, this.position.getX(), this.position.getY());
        }

    }

    /**
     * Checks if the life is still active.
     *
     * @return True if the life is active, false otherwise.
     */
    public boolean isAlive(){
        return isActive;
    }

    /**
     * Sets the status of the life.
     *
     * @param alive True to set the life as active, false to set it as inactive.
     */
    public void setAlive(boolean alive){
        isActive = alive;
    }
}

package com.bomberman.bomberman.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a brick element in the game.
 * Extends the {@link Element} class and provides functionality for painting, getting size and position,
 * and managing the activity state of the brick.
 */
public class Brick extends Element{
    private Image brick;
    private Position position;
    private Canvas canvas;
    private GraphicsContext graphicsContext; //contexto grafico

    private int size;

    private boolean isActive;

    /**
     * Constructs a brick with the specified position and canvas.
     *
     * @param position The initial position of the brick.
     * @param canvas   The canvas on which the brick will be drawn.
     */
    public Brick(Position position, Canvas canvas){
        super(canvas);
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.position=position;
        this.size=60;
        this.isActive=true;

        this.brick =new Image(getClass().getResourceAsStream("/com/bomberman/bomberman/img/Walls/breakWall1.png"), 60, 60, false, false);
    }

    /**
     * Paints the brick on the canvas if it is active.
     */
    public void paint(){
        if(this.isActive){
            this.graphicsContext.drawImage(this.brick, this.position.getX(), this.position.getY());
        }
    }


    /**
     * Gets the size of the brick.
     *
     * @return The size of the brick.
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Gets the position of the brick.
     *
     * @return The position of the brick.
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Checks if the brick is active.
     *
     * @return True if the brick is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the activity state of the brick.
     *
     * @param active True to activate the brick, false to deactivate.
     */
    public void setActive(boolean active) {
        isActive = active;
    }


}

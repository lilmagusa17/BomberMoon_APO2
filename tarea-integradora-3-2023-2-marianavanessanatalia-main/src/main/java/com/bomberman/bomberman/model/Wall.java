package com.bomberman.bomberman.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a wall element in the game.
 */
public class Wall extends Element{

    private Image wall;
    private Position position;
    private Canvas canvas;
    private GraphicsContext graphicsContext; //contexto grafico

    private int size;

    /**
     * Initializes a new instance of the Wall class with the specified position and canvas.
     *
     * @param position The position of the wall.
     * @param canvas   The canvas on which the wall will be drawn.
     */
    public Wall(Position position, Canvas canvas){
        super(canvas);
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.position=position;
        this.size=60;

        this.wall=new Image(getClass().getResourceAsStream("/com/bomberman/bomberman/img/Walls/NormalWall.png"), 60, 60, false, false);
    }

    /**
     * Draws the wall on the canvas.
     */
    public void paint(){
        this.graphicsContext.drawImage(wall, this.position.getX(), this.position.getY());
    }

    /**
     * Gets the position of the wall.
     *
     * @return The position of the wall.
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Gets the size of the wall.
     *
     * @return The size of the wall.
     */
    public int getSize(){
        return this.size;
    }

}

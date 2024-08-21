package com.bomberman.bomberman.model;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a generic game element with basic properties such as a canvas, graphics context, frame count, size, and position.
 */
public class Element {

    private Canvas canvas;
    private GraphicsContext graphicsContext; //contexto grafico
    private int frame;
    private int size;

    private Position position;

    /**
     * Constructs an element with the specified canvas.
     *
     * @param canvas The canvas on which the element will be drawn.
     */
    public Element(Canvas canvas){
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.frame=0;
    }

    /**
     * Paints the element. This method should be overridden by subclasses to define the appearance of the element.
     */
    public void paint(){
        this.frame++;
    }

    /**
     * Gets the position of the element.
     *
     * @return The position of the element.
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Gets the size of the element.
     *
     * @return The size of the element.
     */
    public int getSize(){
        return this.size;
    }



}

package com.bomberman.bomberman.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

/**
 * Represents a Bomb object in the Bomberman game, responsible for handling bomb logic and rendering.
 */
public class Bomb {

    //graphic elements
    private int frame;

    private Position position;
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private boolean isActive;
    private boolean isExploding;
    private long explosionStartTime;

    private final int BLAST_RADIUS = 100;
    private ArrayList<Image> bomb;

    private ArrayList<Image> explosion;


    /**
     * Creates a Bomb object with the specified canvas and position.
     *
     * @param canvas   The canvas on which the bomb is rendered.
     * @param position The position of the bomb.
     */
    public Bomb(Canvas canvas, Position position){
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.frame=0;
        this.bomb=new ArrayList<>();
        this.explosion=new ArrayList<>();
        this.position=position;
        this.isActive=false;

        this.isExploding = false;
        this.explosionStartTime = 0;

        for(int i=1;i<=3;i++){//IMAGE OF BOMB FIRST PLACED
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bomberman/bomberman/img/Bomb/Bomb" +i +".png")), 55, 55, false, false);
            bomb.add(image);
        }

        for(int i=1;i<=3;i++){//explosion
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/bomberman/bomberman/img/Bomb/Explotion" +i +".png")), 55, 55, false, false);
            explosion.add(image);
        }

    }


    /**
     * Renders the bomb on the canvas based on its state.
     */
    public void paint(){

        long now = System.currentTimeMillis();

        if (!isActive) {
            this.graphicsContext.drawImage(bomb.get(frame % 3), position.getX(), position.getY());
        } else if (System.currentTimeMillis() - now < 3000) {
            this.graphicsContext.drawImage(explosion.get(frame % 3), position.getX(), position.getY());
        }

        this.frame++;

    }


    /**
     * Checks if the avatar is within the blast radius of the bomb.
     *
     * @param avatar The Bomberman avatar to check.
     * @return True if the avatar is within the blast radius, false otherwise.
     */
    public boolean isAvatarInBlastRadius(Bomberman avatar) {
        double distance = this.position.calculateDistance(avatar.getPosition());
        return isExploding && distance < BLAST_RADIUS;
    }

    /**
     * Checks if an enemy is within the blast radius of the bomb.
     *
     * @param enemy The enemy to check.
     * @return True if the enemy is within the blast radius, false otherwise.
     */
    public boolean isEnemyInBlastRadius(Enemy enemy) {
        double distance = this.position.calculateDistance(enemy.getPosition());
        return isExploding && distance < BLAST_RADIUS;
    }

    /**
     * Initiates the bomb explosion, changing its state to exploding.
     */
    public void explode(){
        if (!isActive && !isExploding) {
            isActive = true;
            isExploding = true;
            explosionStartTime = System.currentTimeMillis();
        }
    }

    /**
     * Checks if the bomb is currently active.
     *
     * @return True if the bomb is active, false otherwise.
     */
    public boolean isActive(){
        return this.isActive;
    }

    /**
     * Gets the position of the bomb.
     *
     * @return The position of the bomb.
     */
    public Position getPosition(){
        return this.position;
    }


}

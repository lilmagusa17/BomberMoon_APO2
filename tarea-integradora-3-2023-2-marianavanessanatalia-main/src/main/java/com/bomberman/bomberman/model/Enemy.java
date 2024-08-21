package com.bomberman.bomberman.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents an enemy in the game with properties such as canvas, graphics context, frame count, size, position, and activity status.
 */
public class Enemy extends Element{

    //graphic elements
    private Canvas canvas;
    private GraphicsContext graphicsContext; //contexto grafico

    private ArrayList<Image> enemy;
    private int frame;
    private int size;
    private double speed = 10;

    private Position position;

    private boolean isActive;

    private final String PATH="/com/bomberman/bomberman/img/Enemies/Trompo/TrompoEnemy";

    /**
     * Constructs an enemy with the specified canvas and position.
     *
     * @param canvas The canvas on which the enemy will be drawn.
     * @param pos The initial position of the enemy.
     */
    public Enemy(Canvas canvas, Position pos){
        super(canvas);
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.position=pos;
        this.isActive=true;

        this.size=20;

        this.frame=0;

        this.enemy=new ArrayList<>();
        for (int i = 1; i <= 4; i++) { //idle
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH + i + ".png")), 40, 40, false, false);
            this.enemy.add(image);
        }

    }

    /**
     * Paints the enemy on the canvas if it is active.
     */
    public void paint(){
        if (this.isActive) {
            this.graphicsContext.drawImage(enemy.get(frame % 4), this.position.getX(), this.position.getY());
            this.frame++;
        }
    }

    /**
     * Gets the size of the enemy.
     *
     * @return The size of the enemy.
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Gets the position of the enemy.
     *
     * @return The position of the enemy.
     */
    public Position getPosition(){
        return this.position;
    }

    /**
     * Kills the player if the enemy is active and within a certain distance to the player.
     *
     * @param player The player to be killed.
     */
    public void kill(Bomberman player){
        if (isActive && position.calculateDistance(player.getPosition()) < 50) {
            player.flop();
        }
    }

    /**
     * Checks if the enemy is actively pursuing the player, considering obstacles like walls and bricks.
     *
     * @param player The player being chased by the enemy.
     * @param bricks The list of bricks in the game.
     * @param walls The list of walls in the game.
     */
    public void chase(Bomberman player, ArrayList<Brick> bricks, ArrayList<Wall> walls) {
        if (isActive) {
            double distance = position.calculateDistance(player.getPosition());
            //System.out.println("Distance to player: " + distance);
            if (distance < 170) {
                double dx = player.getPosition().getX() - position.getX();
                double dy = player.getPosition().getY() - position.getY();
                double length = Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                double newX = position.getX() + dx * speed;
                double newY = position.getY() + dy * speed;
                if (!collidesWithWallsOrBricks(newX, newY, bricks, walls)) {
                    position.setX(newX);
                    position.setY(newY);
                }
            }
        }
    }

    /**
     * Checks if the enemy is actively pursuing the player, considering obstacles like walls and bricks.
     *
     * @param newX The potential new X-coordinate of the enemy.
     * @param newY The potential new Y-coordinate of the enemy.
     * @param bricks The list of bricks in the game.
     * @param walls The list of walls in the game.
     * @return True if the enemy collides with walls or bricks at the specified coordinates, false otherwise.
     */
    private boolean collidesWithWallsOrBricks(double newX, double newY, ArrayList<Brick> bricks, ArrayList<Wall> walls) {
        if (newX < 0 || newX + size > canvas.getWidth() || newY < 0 || newY + size > canvas.getHeight()) {
            return true;
        }
        for (Brick brick : bricks) {
            if (brick.isActive() && intersects(newX, newY, brick)) {
                handleCollisionWithBrick(brick);
                return true;
            }
        }
        for (Wall wall : walls) {
            if (intersects(newX, newY, wall)) {
                handleCollisionWithWall(wall);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the specified coordinates intersect with a brick.
     *
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @param brick The brick to check for intersection.
     * @return True if the coordinates intersect with the brick, false otherwise.
     */
    private boolean intersects(double x, double y, Brick brick) {
        return x < brick.getPosition().getX() + brick.getSize() && x + size > brick.getPosition().getX() &&
                y < brick.getPosition().getY() + brick.getSize() && y + size > brick.getPosition().getY();
    }

    /**
     * Verifies if the specified coordinates intersect with a wall.
     *
     * @param x The X-coordinate to check.
     * @param y The Y-coordinate to check.
     * @param wall The wall to check for intersection.
     * @return True if the coordinates intersect with the wall, false otherwise.
     */
    private boolean intersects(double x, double y, Wall wall) {
        return x < wall.getPosition().getX() + wall.getSize() && x + size > wall.getPosition().getX() &&
                y < wall.getPosition().getY() + wall.getSize() && y + size > wall.getPosition().getY();
    }


    /**
     * Handles collision with a brick by adjusting the position of the enemy based on the collision.
     *
     * @param brick The brick with which the enemy collides.
     */
    private void handleCollisionWithBrick(Brick brick) {
        double overlapX = Math.min(position.getX() + size, brick.getPosition().getX() + brick.getSize()) - Math.max(position.getX(), brick.getPosition().getX());
        double overlapY = Math.min(position.getY() + size, brick.getPosition().getY() + brick.getSize()) - Math.max(position.getY(), brick.getPosition().getY());
        boolean horizontalCollision = overlapX < overlapY;
    
        if (horizontalCollision) {
            if (position.getX() < brick.getPosition().getX()) {
                position.setX(brick.getPosition().getX() - size);
            } else {
                position.setX(brick.getPosition().getX() + brick.getSize());
            }
        } else {
            if (position.getY() < brick.getPosition().getY()) {
                position.setY(brick.getPosition().getY() - size);
            } else {
                position.setY(brick.getPosition().getY() + brick.getSize());
            }
        }
    }

    /**
     * Handles collision with a wall by adjusting the position of the enemy based on the collision.
     *
     * @param wall The wall with which the enemy collides.
     */
    private void handleCollisionWithWall(Wall wall) {
        double overlapX = Math.min(position.getX() + size, wall.getPosition().getX() + wall.getSize()) - Math.max(position.getX(), wall.getPosition().getX());
        double overlapY = Math.min(position.getY() + size, wall.getPosition().getY() + wall.getSize()) - Math.max(position.getY(), wall.getPosition().getY());
        boolean horizontalCollision = overlapX < overlapY;
    
        if (horizontalCollision) {
            if (position.getX() < wall.getPosition().getX()) {
                position.setX(wall.getPosition().getX() - size);
            } else {
                position.setX(wall.getPosition().getX() + wall.getSize());
            }
        } else {
            if (position.getY() < wall.getPosition().getY()) {
                position.setY(wall.getPosition().getY() - size);
            } else {
                position.setY(wall.getPosition().getY() + wall.getSize());
            }
        }
    }

    /**
     * Checks if the enemy is active.
     *
     * @return True if the enemy is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }


    /**
     * Sets the activity status of the enemy.
     *
     * @param active True to set the enemy as active, false to set it as inactive.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

}
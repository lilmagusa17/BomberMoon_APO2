package com.bomberman.bomberman.screens;

import com.bomberman.bomberman.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Represents the first screen of the Bomberman game.
 */
public class ScreenA extends BaseScreen{

    private Canvas canvas;

    private Canvas header;
    private GraphicsContext graphicsContext;
    private GraphicsContext gcHeader;
    private Bomberman bomberman;

    private ArrayList<Element> elements;

    private ArrayList<Wall> paredes;
    private ArrayList<Brick> bricks;

    private ArrayList<Enemy> enemies;

    private ArrayList<Life> vidas ;

    /**
     * Initializes a new instance of the ScreenA class with the specified main canvas and header canvas.
     *
     * @param canvas The main canvas for the game screen.
     * @param header The canvas for displaying additional information (e.g., lives).
     */
    public ScreenA(Canvas canvas, Canvas header){//recibe canvas por inyeccion de dependencia (patron de dise;o, asociacion entre el Screen y Canvas)
        super(canvas, header);
        this.canvas=canvas;
        this.graphicsContext=this.canvas.getGraphicsContext2D();

        this.header=header;
        this.bomberman=new Bomberman(this.canvas, this.header);
        this.gcHeader=this.header.getGraphicsContext2D();


        //añadir paredes
        paredes = new ArrayList<>();
        paredes.add(new Wall(new Position(300,300), canvas));
        paredes.add(new Wall(new Position(360,300), canvas));
        paredes.add(new Wall(new Position(120,120),canvas));
        paredes.add(new Wall(new Position(120,300),canvas));
        paredes.add(new Wall(new Position(300,120),canvas));
        paredes.add(new Wall(new Position(360,120),canvas));
        paredes.add(new Wall(new Position(540,120),canvas));
        paredes.add(new Wall(new Position(540,300),canvas));

        bricks = new ArrayList<>();
        bricks.add(new Brick(new Position(300,60),canvas));
        bricks.add(new Brick(new Position(360,60),canvas));
        bricks.add(new Brick(new Position(420,60),canvas));
        bricks.add(new Brick(new Position(420,120),canvas));
        bricks.add(new Brick(new Position(180,120),canvas));
        bricks.add(new Brick(new Position(240,120),canvas));
        bricks.add(new Brick(new Position(240,180),canvas));
        bricks.add(new Brick(new Position(60,240),canvas));
        bricks.add(new Brick(new Position(60,300),canvas));
        bricks.add(new Brick(new Position(60,360),canvas));
        bricks.add(new Brick(new Position(120,240),canvas));
        bricks.add(new Brick(new Position(180,240),canvas));
        bricks.add(new Brick(new Position(240,240),canvas));
        bricks.add(new Brick(new Position(240,300),canvas));
        bricks.add(new Brick(new Position(600,180),canvas));
        bricks.add(new Brick(new Position(600,240),canvas));
        bricks.add(new Brick(new Position(480,420),canvas));
        bricks.add(new Brick(new Position(420,360),canvas));
        bricks.add(new Brick(new Position(360,420),canvas));

        enemies=new ArrayList<>();
        enemies.add(new Enemy(canvas, new Position(310, 190)));

        this.elements=new ArrayList<>();
        this.elements.addAll(paredes);
        this.elements.addAll(bricks);
        this.elements.addAll(enemies);

    }

    /**
     * Paints the content of the ScreenA.
     */
    @Override
    public void paint(){

        ArrayList<Element> toDelete = new ArrayList<>();
        graphicsContext.setFill(Color.LAVENDER);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());

        gcHeader.setFill(Color.rgb(222, 222, 222));
        gcHeader.fillRect(0,0,header.getWidth(),header.getHeight());
        /*for(Element actual: elements){
            bomberman.coallision(actual);
        }*/

        //FIXME arreglo toDELETE Y QUITARLOS
        if(toDelete.size()>0){
            elements.removeAll(toDelete);
        }
        toDelete.clear();
        bomberman.paint();//el avatar se pinta sobre el canvas donde esta el screen

        for(Element actual: elements){
            bomberman.checkBrickCollisions(bricks);
            //FIXME variable controladora que guarde el elemento a borrar y en la siguiente itera lo borre
            actual.paint();
            if(actual instanceof Enemy){
                ((Enemy) actual).kill(bomberman);
            }
            if(disappear(actual)){
                toDelete.add(actual);
            }

        }
        chasePlayer(bomberman);

    }

    /**
     * Causes enemies to chase the player.
     *
     * @param player The player (Bomberman) to chase.
     */
    public void chasePlayer(Bomberman player) {
        for (Enemy enemy : enemies) {
            enemy.chase(player, bricks, paredes);
        }
    }

    /**
     * Handles key pressed event for the screen.
     *
     * @param event The key event.
     */
    public void setOnKeyPressed(KeyEvent event) {
        bomberman.setOnKeyPressed(event);
    }

    /**
     * Handles key released event for the screen.
     *
     * @param event The key event.
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
     * Checks if an element should disappear.
     *
     * @param element The element to check.
     * @return True if the element should disappear, false otherwise.
     */
    public boolean disappear(Element element){
        if(bomberman.slay(element)){
            return true;
        }
        return false;
    }

}

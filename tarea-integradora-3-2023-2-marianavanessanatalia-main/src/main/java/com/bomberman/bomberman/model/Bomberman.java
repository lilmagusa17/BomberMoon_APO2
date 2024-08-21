package com.bomberman.bomberman.model;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Represents the player character (Bomberman) in the Bomberman game.
 */
public class Bomberman extends Element{


    //final variables
    private final String PATH_EXPLODE="/com/bomberman/bomberman/img/sailorBomber/explode/ex";
    private final String PATH_IDLE="/com/bomberman/bomberman/img/sailorBomber/idle/id";
    private final String PATH_JUMPDOWN="/com/bomberman/bomberman/img/sailorBomber/jumpDown/jd";
    private final String PATH_JUMPUP="/com/bomberman/bomberman/img/sailorBomber/jumpUp/ju";
    private final String PATH_JUMPRIGHT="/com/bomberman/bomberman/img/sailorBomber/jumpRight/jr";
    private final String PATH_JUMPLEFT="/com/bomberman/bomberman/img/sailorBomber/jumpLeft/jl";
    private final String PATH_WALKDOWN="/com/bomberman/bomberman/img/sailorBomber/moveDown/down";
    private final String PATH_WALKUP="/com/bomberman/bomberman/img/sailorBomber/moveUp/up";
    private final String PATH_WALKRIGHT="/com/bomberman/bomberman/img/sailorBomber/moveRight/right";
    private final String PATH_WALKLEFT="/com/bomberman/bomberman/img/sailorBomber/moveLeft/left";

    //graphic elements
    private Canvas canvas;

    private Canvas header;
    private GraphicsContext graphicsContext; //contexto grafico

    private GraphicsContext gcHeader;

    private ArrayList<Image> idles; //estatico

    private ArrayList<Image> walksDown; //caminando
    private ArrayList<Image> walksUp; //caminando
    private ArrayList<Image> walksRight; //caminando
    private ArrayList<Image> walksLeft; //caminando

    private ArrayList<Image> jumpsDown; //saltando
    private ArrayList<Image> jumpsUp; //saltando
    private ArrayList<Image> jumpsRight; //saltando
    private ArrayList<Image> jumpsLeft; //saltando
    private ArrayList<Image> dead; //muerto

    private Bomb bomb; //bomba

    private ArrayList<Life> vidas; //vidas

    //elementos espaciales
    private Position position; //posicion del avatar

    private State state; //state of the player (idle, walk, jump, dead)

    private int frame; //current frame
    private int size;

    private boolean isDead;

    //keys state
    private boolean rightPressed; //ir hacia la derecha esta siendo presionada
    private boolean leftPressed; //ir hacia la izquierda esta siendo presionada
    private boolean upPressed; //ir hacia arriba esta siendo presionada
    private boolean downPressed; //ir hacia abajo esta siendo presionada
    private boolean spacePressed; //space esta siendo presionada

    /**
     * Creates a Bomberman object with the specified canvas and header.
     *
     * @param canvas The canvas on which the player is rendered.
     * @param header The canvas for displaying additional information (e.g., lives).
     */
    public Bomberman(Canvas canvas, Canvas header){ //solo recibe el canvas
        super(canvas);
        this.canvas = canvas;
        this.header = header;
        this.graphicsContext=this.canvas.getGraphicsContext2D();
        this.gcHeader=this.header.getGraphicsContext2D();
        this.state=State.IDLE;
        this.size=50;


        this.frame=0; //cada vez que se corre, hay que cambiar el frame

        this.idles=new ArrayList<>();
        this.walksDown=new ArrayList<>();
        this.walksUp=new ArrayList<>();
        this.walksRight=new ArrayList<>();
        this.walksLeft=new ArrayList<>();
        this.jumpsDown=new ArrayList<>();
        this.jumpsUp=new ArrayList<>();
        this.jumpsRight=new ArrayList<>();
        this.jumpsLeft=new ArrayList<>();
        this.dead=new ArrayList<>();


        this.position=new Position(70,70);

        for (int i = 1; i <= 2; i++) { //idle
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_IDLE + i + ".png")), 40, 40, false, false);
            this.idles.add(image);
        }

        for(int i=1;i<=2;i++){ //jump down
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_JUMPDOWN + i + ".png")), 40, 40, false, false);
            this.jumpsDown.add(image);
        }

        for (int i=1;i<=2;i++){ //jump left
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_JUMPLEFT + i + ".png")), 40, 40, false, false);
            this.jumpsLeft.add(image);
        }

        for(int i=1;i<=2;i++){ //jump right
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_JUMPRIGHT + i + ".png")), 40, 40, false, false);
            this.jumpsRight.add(image);
        }

        for(int i=1;i<=3;i++){ //jump up
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_JUMPUP + i + ".png")), 40, 40, false, false);
            this.jumpsUp.add(image);
        }

        for(int i=1;i<=7;i++){ //move down
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_WALKDOWN + i + ".png")), 40, 40, false, false);
            this.walksDown.add(image);
        }

        for(int i=1;i<=3;i++){ //move left
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_WALKLEFT + i + ".png")), 40, 40, false, false);
            this.walksLeft.add(image);
        }

        for(int i=1;i<=3;i++){ //MOVE RIGHT
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_WALKRIGHT + i + ".png")), 40, 40, false, false);
            this.walksRight.add(image);
        }

        for(int i=1;i<=8;i++){ //move up
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_WALKUP + i + ".png")), 40, 40, false, false);
            this.walksUp.add(image);
        }

        for(int i=1;i<=6;i++){ //dead
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PATH_EXPLODE + i + ".png")), 40, 40, false, false);
            this.dead.add(image);
        }

        this.vidas=new ArrayList<>();
        this.vidas.add(new Life(this.header,new Position(120,10)));
        this.vidas.add(new Life(this.header,new Position(70,10)));
        this.vidas.add(new Life(this.header,new Position(20,10)));

        this.isDead = false;

    }

    /**
     * Stops the player at the edges of the game area and handles collisions with walls.
     */
    public void stop(){
        //calcular limites del muñeco
        if(rightPressed && position.getX()>=600){
            this.position.setX(600);
            this.rightPressed=false;
        }
        if(leftPressed && position.getX() <= 55.0){
            this.position.setX(55);
            this.leftPressed=false;
        }
        if(upPressed && position.getY() <= 55.0){
            this.position.setY(70);
            this.downPressed=false;
        }
        if((position.getX()<230 || position.getX()>410) && downPressed && position.getY()>=415){
            this.position.setY(415);
            this.upPressed=false;
        }
        
        //calcular colisiones con paredes
       if(downPressed &&
                ((position.getY()>=70 && position.getY()<=150) ||
                        (position.getY()>=250 && position.getY()<=310)) &&
                ((position.getX()>70 && position.getX()<170) ||
                        (position.getX()>250 && position.getX()<415) ||
                        (position.getX()>490 && position.getX()<595))){

            this.position.setY(this.position.getY());
            this.downPressed=false;
       }

       if(upPressed && ((position.getY()>=110 && position.getY()<=180) ||
                (position.getY()<=250 && position.getY()>=355)) &&
                ((position.getX()>70 && position.getX()<170) ||
                        (position.getX()>250 && position.getX()<415) ||
                        (position.getX()>490 && position.getX()<595))){
            this.position.setY(this.position.getY());
            this.upPressed=false;
       }

       if(rightPressed && ((position.getX()>=70 && position.getX()<=170) ||
                (position.getX()>=250 && position.getX()<=415) ||
                (position.getX()>=490 && position.getX()<=590)) &&
                ((position.getY()>70 && position.getY()<150) ||
                        (position.getY()>255 && position.getY()<350) )){
            this.position.setX(this.position.getX());
            this.rightPressed=false;
       }

       if(leftPressed && ((position.getX()>=110 && position.getX()<=175) ||
                (position.getX()>=310 && position.getX()<=415) ||
                (position.getX()>=490 && position.getX()<=595)) &&
                ((position.getY()>70 && position.getY()<150) ||
                        (position.getY()>255 && position.getY()<350) )){
            this.position.setX(this.position.getX());
            this.leftPressed=false;
       }


    }


    /**
     * Paints the player character on the canvas along with other game elements.
     */
    public void paint(){

       // System.out.println(this.vidas.size());
        stop();
        onMove();

        //System.out.println(this.position.getX() + " " + this.position.getY());

        if (spacePressed) {
            throwBomb();
            spacePressed = false;  // Reset the flag
        }

        if (this.bomb != null) {
            this.bomb.paint();
        }


        switch (state) {
            case IDLE: // idle 2
                this.graphicsContext.drawImage(idles.get(frame % 2), this.position.getX(), this.position.getY());

                break;
            case WALKD: // move down 7
                this.graphicsContext.drawImage(walksDown.get(frame % 7), this.position.getX(), this.position.getY());
                break;
            case WALKL: // move left 3
                this.graphicsContext.drawImage(walksLeft.get(frame % 3), this.position.getX(), this.position.getY());
                break;
            case WALKR: // move right 3
                this.graphicsContext.drawImage(walksRight.get(frame % 3), this.position.getX(), this.position.getY());
                break;
            case WALKU: // move up 8
                this.graphicsContext.drawImage(walksUp.get(frame % 8), this.position.getX(), this.position.getY());
                break;
            case JUMPD: // jump down 2
                this.graphicsContext.drawImage(jumpsDown.get(frame % 2), this.position.getX(), this.position.getY());
                break;
            case JUMPL: // jump left 2
                this.graphicsContext.drawImage(jumpsLeft.get(frame % 2), this.position.getX(), this.position.getY());
                break;
            case JUMPR: // jump right 2
                this.graphicsContext.drawImage(jumpsRight.get(frame % 2), this.position.getX(), this.position.getY());
                break;
            case JUMPU: // jump up 3
                this.graphicsContext.drawImage(jumpsUp.get(frame % 2), this.position.getX(), this.position.getY());
                break;
            case DEAD: // dead son 6
                this.graphicsContext.drawImage(dead.get(frame % 6), this.position.getX(), this.position.getY());
                break;
        }

        for(Life actual: vidas){
            actual.paint();

        }

        this.frame++;


    }

    /**
     * Sets the corresponding key as pressed and updates the player state accordingly.
     *
     * @param event The KeyEvent representing the pressed key.
     */
    public void setOnKeyPressed(KeyEvent event){ //recibe evento del teclado
        switch (event.getCode()){
            case UP:
                this.state = State.WALKU;
                this.upPressed = true;
                break;
            case DOWN:
                this.state = State.WALKD;
                this.downPressed = true;
                break;

            case LEFT:
                this.state = State.WALKL;
                this.leftPressed = true;
                break;

            case RIGHT:
                this.state = State.WALKR;
                this.rightPressed = true;
                break;
            case SPACE:
                this.state = State.IDLE;
                this.spacePressed = true;

                // Check if there's no existing bomb
                if (this.bomb == null || !this.bomb.isActive()) {
                    throwBomb();
                }
                break;

        }
    }

    /**
     * Throws a bomb when the space key is pressed and initiates bomb-related animations.
     */
    public void throwBomb() {

        if (this.bomb == null || !this.bomb.isActive()) {
            // Set the bomb at the current position of the player
            this.bomb = new Bomb(this.canvas, new Position(this.position.getX(), this.position.getY()));

           //EL ANIMATION TIMER ES RE UTIL PARA MANEJAR CON PRECISION EL TIEMPO DE ANIMACION
            new AnimationTimer() {
                private long explosionStartTime = -1;
                private boolean bombExploded = false;

                @Override
                public void handle(long now) {
                    if (explosionStartTime < 0) {
                        explosionStartTime = now;
                        bombExploded = false;
                    }

                    long elapsedMillis = (now - explosionStartTime) / 1_000_000;

                    // Explode after 2 seconds
                    if (elapsedMillis > 2000 && !bombExploded) {
                        bomb.explode();
                        bombExploded = true;
                    }

                    // Remove bomb after 3 seconds
                    if (elapsedMillis > 3000) {
                        bomb = null;
                        this.stop(); // Stop the AnimationTimer
                    }

                    // Check if the avatar is in the blast radius
                    if (bomb != null && bomb.isActive() && bomb.isAvatarInBlastRadius(Bomberman.this)) {
                        flop();
                        bombExploded = true;  // Stop decrementing lives after the first hit
                    }
                }
            }.start();
        }
    }

    /**
     * Sets the corresponding key as released and updates the player state accordingly.
     *
     * @param event The KeyEvent representing the released key.
     */
    public void setOnKeyReleased(KeyEvent event){
        switch (event.getCode()){
            case UP:
                this.state=State.IDLE;
                this.upPressed = false;
                break;

            case DOWN:
                this.state = State.IDLE;
                this.downPressed = false;
                break;

            case LEFT:
                this.state = State.IDLE;
                this.leftPressed = false;
                break;

            case RIGHT:
                this.state = State.IDLE;
                this.rightPressed = false;
                break;

            case SPACE:
                this.state = State.IDLE;
                this.spacePressed = false;
                break;
        }
    }

    /**
     * Handles player movement based on the pressed keys and updates the player's position.
     */
    public void onMove(){
        int step = 15;

        if (rightPressed && !leftPressed) {
            this.position.setX(this.position.getX() + step);
        } else if (leftPressed && !rightPressed) {
            this.position.setX(this.position.getX() - step);
        }

        if (upPressed && !downPressed) {
            this.position.setY(this.position.getY() - step);
        } else if (downPressed && !upPressed) {
            this.position.setY(this.position.getY() + step);
        }

    }

    public Position getPosition() {
        return position;
    }

    public Position getPositionBomb(){
        return this.bomb.getPosition();
    }

    /**
     * Decreases the lives of the player when the avatar is hit by a bomb explosion or enemy.
     * Displays "You lose" after losing all lives.
     */
    public void flop() {
        // Synchronize access to the vidas list
        synchronized (vidas) {
            if (this.vidas.size() == 1) {
                this.state = State.DEAD;

                // Schedule a task to display "You lose" after 2 seconds
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (!vidas.isEmpty()) {
                                vidas.remove(0);
                            }
                            isDead = true;
                            System.out.println("Aww you lose, at least you tried :)");
                        });
                    }
                }, 500);
            } else {
                position.setX(70);
                position.setY(70);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (!vidas.isEmpty()) {
                                vidas.remove(0);
                                isDead = false;
                            }
                        });
                    }
                }, 800);
            }
            //System.out.println("lives: " + this.vidas.size());
        }
    }

    /**
     * Checks if the player is in the door area.
     *
     * @return True if the player is in the door area, false otherwise.
     */
    public boolean isInDoor(){
        return this.position.getX()>=290 && this.position.getX()<=470 && this.position.getY()>=480;
    }

    /**
     * Checks if the player is dead.
     *
     * @return True if the player is dead, false otherwise.
     */
    public boolean isDead(){
        return this.isDead;
    }

    /**
     * Gets the size of the player character.
     *
     * @return The size of the player character.
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Checks if the bomb slays an enemy or destroys a brick based on the bomb's position.
     *
     * @param other The game element to check for slaying.
     * @return True if the bomb slays the enemy or destroys the brick, false otherwise.
     */
    public boolean slay(Element other){
        if(this.bomb!=null && bomb.isActive()){
            if(other instanceof Enemy || other instanceof Brick){
                if(getPositionBomb().calculateDistance(other.getPosition())<100){
                    if(other instanceof Brick){
                        ((Brick) other).setActive(false);
                    } else {
                        ((Enemy) other).setActive(false);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks for collisions with bricks and handles the player's position when colliding with a brick.
     *
     * @param bricks The list of bricks in the game.
     */
    public void checkBrickCollisions(ArrayList<Brick> bricks) {
        for (Brick brick : bricks) {
            if (this.intersects(brick) && brick.isActive()) {
                this.handleCollisionWithBrick(brick);

            }
        }
    }

    /**
     * Checks if the player's bounding box intersects with the bounding box of another game element.
     *
     * @param brick The game element to check for intersection.
     * @return True if the player's bounding box intersects with the specified element, false otherwise.
     */
    private boolean intersects(Element brick) {
        // Check if the bounding boxes of this and brick intersect
        return this.position.getX() < brick.getPosition().getX() + brick.getSize() &&
                this.position.getX() + this.getSize() > brick.getPosition().getX() &&
                this.position.getY() < brick.getPosition().getY() + brick.getSize() &&
                this.position.getY() + this.getSize() > brick.getPosition().getY();
    }

    /**
     * Handles player collision with a brick by adjusting the player's position based on the direction of movement.
     * If the player is moving right, adjust the player's X-coordinate to the left of the brick.
     * If the player is moving left, adjust the player's X-coordinate to the right of the brick.
     * If the player is moving up, adjust the player's Y-coordinate below the brick.
     * If the player is moving down, adjust the player's Y-coordinate above the brick.
     *
     * @param brick The brick with which the player collided.
     */
    private void handleCollisionWithBrick(Brick brick) {

        if (rightPressed) {
            this.position.setX(brick.getPosition().getX() - this.getSize());
            this.rightPressed = false;

        } else if (leftPressed) {
            this.position.setX(brick.getPosition().getX() + brick.getSize());
            this.leftPressed = false;
        }

        if (upPressed) {
            this.position.setY(brick.getPosition().getY() + brick.getSize());
            this.upPressed = false;

        } else if (downPressed) {
            this.position.setY(brick.getPosition().getY() - this.getSize());
            this.downPressed = false;

        }
    }

    public ArrayList<Life> getVidas(){
        return this.vidas;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }
}

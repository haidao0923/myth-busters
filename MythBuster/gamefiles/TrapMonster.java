package gamefiles;

import java.util.Random;

import controller.Controller;
import javafx.animation.AnimationTimer;

public class TrapMonster extends Monster {

    public TrapMonster() {
        super("Trap Monster", 100, 5, 2, "sprites/Medusa.png", 100, 100);
    }

    public TrapMonster(double health, double movementSpeed, double attackSpeed, String spritePath, double width, double height) {
        super("Trap Monster", health, movementSpeed, attackSpeed, spritePath, width, height);
    }

    @Override
    public void act() {
        new AnimationTimer() {
            double targetPositionX = Math.random() * (Controller.getW() - width);
            double targetPositionY = Math.random() * (Controller.getH() - height);
            public void handle(long currentNanoTime) {
                // game logic
                checkDeath();
                redrawHealthBar();
                if (isDead) {
                    stop();
                }
                // move
                double offsetX = targetPositionX - positionX;
                double offsetY = targetPositionY - positionY;
                double magnitude = Math.sqrt(offsetX*offsetX + offsetY*offsetY);
                if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
                    moveRelative(movementSpeed * offsetX/magnitude, movementSpeed * offsetY/magnitude);
                } else {
                    targetPositionX = Math.random() * (Controller.getW() - width);
                    targetPositionY = Math.random() * (Controller.getH() - height);
                }
            }
        }.start();
    }
}

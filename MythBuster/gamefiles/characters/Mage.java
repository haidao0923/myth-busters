package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.animation.AnimationTimer;

public class Mage extends Monster {

    public Mage() {
        super("Mage Enemy", 100, 5, "sprites/FF1Mage.png", 100, 100);
    }

    public Mage(double health, double movementSpeed, String spritePath, double width, double height) {
        super("Mage Enemy", health, movementSpeed, spritePath, width, height);

    }

    int damageCooldown = 15;
    double targetPositionX = Math.random() * (Controller.getW() - width);
    double targetPositionY = Math.random() * (Controller.getH() - height);


    public void update() {
        // game logic
        checkDeath();
        redrawHealthBar();
        // move
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        double magnitude = Math.sqrt(offsetX*offsetX + offsetY*offsetY);
        if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            moveRelative(movementSpeed * offsetX/magnitude, movementSpeed * offsetY/magnitude);
        } else {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                //Shoot a fireball.

                Fireball fireball = new Fireball(positionX, positionY);
                Controller.getGameScreen().getBoard().getChildren().add(fireball.getGroup());
                GameLoop.monsters.add(fireball);

                damageCooldown = 100;
                targetPositionX = Math.random() * (Controller.getW() - width);
                targetPositionY = Math.random() * (Controller.getH() - height);
            }
        }


    }
}

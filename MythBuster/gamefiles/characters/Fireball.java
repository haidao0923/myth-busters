package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.animation.AnimationTimer;

public class Fireball extends Monster {


    public Fireball(double posX, double posY){
        super("Fireball", 100, 5, "sprites/SuperMarioFireball.png", 50, 50);
        moveAbsolute(posX, posY);
    }

    int damage = 100;
    int timer = 500;

    public void update() {
        double targetPositionX = Controller.getPlayer().getPositionX();
        double targetPositionY = Controller.getPlayer().getPositionY()-30;

        // game logic
        checkDeath();
        redrawHealthBar();
        // move
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Controller.getPlayer().intersects(this)) {
            Controller.getPlayer().takeDamage(damage);
            System.out.println("Fireballed! Health: " + Controller.getPlayer().getCurrentHealth());
            currentHealth = 0;
            checkDeath();
        } else if (Math.abs(offsetX) > 1 || Math.abs(offsetY) > 1) {
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        }

        timer--;
        if (timer == 0) {
            currentHealth = 0;
        }

    }
}
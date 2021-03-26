package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.animation.AnimationTimer;

public class TrapMonster extends Monster {

    public TrapMonster() {
        super("Trap Monster", 100, 5, "sprites/Medusa.png", 100, 100);
    }

    public TrapMonster(double health, double movementSpeed, String spritePath, double width, double height) {
        super("Trap Monster", health, movementSpeed, spritePath, width, height);
    }
    int damage = 50;
    int damageCooldown = 0;
    int spawnTrapCooldown = 100;
    double targetPositionX = Math.random() * (Controller.getW() - width);
    double targetPositionY = Math.random() * (Controller.getH() - height);


    public void update(){
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
            targetPositionX = Math.random() * (Controller.getW() - width);
            targetPositionY = Math.random() * (Controller.getH() - height);
        }
        if (Controller.getPlayer().intersects(this)) {
            if (damageCooldown > 0) {
                damageCooldown--;
            } else {
                damageCooldown = 20;
                Controller.getPlayer().takeDamage(damage);
                System.out.println("Collided with Trap Monster! Health: " + Controller.getPlayer().getCurrentHealth());
            }
        }
        if (spawnTrapCooldown > 0) {
            spawnTrapCooldown--;
        } else {
            spawnTrapCooldown = 100;
            Trap trap = new Trap(positionX, positionY);
            Controller.getGameScreen().getBoard().getChildren().add(trap.getGroup());
            GameLoop.monsters.add(trap);
            Controller.getCurrentRoom().getMonsters().add(trap);
        }
    };
}

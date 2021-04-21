package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.application.Platform;

public class TrapMonster extends Monster {

    public TrapMonster() {
        super("Trap Monster", 100, 5, "sprites/Medusa.png", 100, 100);
    }

    public TrapMonster(double health, double movementSpeed, String spritePath,
                        double width, double height) {
        super("Trap Monster", health, movementSpeed, spritePath, width, height);
    }
    // private int damage = 50;
    // private int damageCooldown = 0;
    private int spawnTrapCooldown = 100;
    private double targetPositionX = Math.random() * (Controller.getW() - width);
    private double targetPositionY = Math.random() * (Controller.getH() - height);


    public void update() {
        // game logic
        checkDeath();
        redrawHealthBar();
        // move
        double offsetX = targetPositionX - positionX;
        double offsetY = targetPositionY - positionY;
        double magnitude = Math.sqrt(offsetX * offsetX + offsetY * offsetY);
        if (Math.abs(offsetX) > 10 || Math.abs(offsetY) > 10) {
            moveRelative(movementSpeed * offsetX / magnitude, movementSpeed * offsetY / magnitude);
        } else {
            targetPositionX = Math.random() * (Controller.getW() - width);
            targetPositionY = Math.random() * (Controller.getH() - height);
        }
        if (spawnTrapCooldown > 0) {
            spawnTrapCooldown--;
        } else {
            spawnTrapCooldown = 100;
            Trap trap = new Trap(positionX, positionY);
            GameLoop.getMonsters().add(trap);
            Platform.runLater(() -> {
                Controller.getGameScreen().getBoard().getChildren().add(trap.getGroup());
                Controller.getCurrentRoom().getMonsters().add(trap);
            });
        }
    }
}

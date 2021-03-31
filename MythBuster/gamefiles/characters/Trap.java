package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;
import javafx.animation.AnimationTimer;

public class Trap extends Monster {


    public Trap(double posX, double posY){
        super("Trap", 100, 0, "sprites/Medusa.png", 50, 50);
        moveAbsolute(posX, posY);
        trapCount++;
    }

    public static int trapCount;

    int damage = 25;

    public void update() {
        // game logic
        System.out.println(GameLoop.monsters.size() - trapCount);
        checkDeath();
        redrawHealthBar();
        if (Controller.getPlayer().intersects(this)) {
            Controller.getPlayer().takeDamage(damage);
            //System.out.println("Triggered Trap! Health: " + Controller.getPlayer().getCurrentHealth());
            currentHealth = 0;
            checkDeath();
            trapCount--;
        }
        if (GameLoop.monsters.size() - trapCount <= 0) {
            currentHealth = 0;
            checkDeath();
            trapCount--;
        }
    }
}

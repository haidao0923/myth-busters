package gamefiles.characters;

import controller.Controller;
import controller.GameLoop;

public class Trap extends Monster {


    public Trap(double posX, double posY) {
        super("Trap", 10, 0, "sprites/Medusa.png", 50, 50);
        moveAbsolute(posX, posY);
        trapCount++;
    }

    private static int trapCount;

    private int damage = 25;

    public void update() {
        // game logic
        checkDeath();
        redrawHealthBar();
        if (Controller.getPlayer().intersects(this)) {
            Controller.getPlayer().takeDamage(damage);
            //System.out.println("Triggered Trap! Health: "
            //                  + Controller.getPlayer().getCurrentHealth());
            currentHealth = 0;
            checkDeath();
        }
        if (GameLoop.getMonsters().size() - trapCount == 0) {
            currentHealth = 0;
            checkDeath();
        }
        /*if (currentHealth <= 0) {
            trapCount--;
        }*/
    }

    public static void setTrapCount(int amount) {
        trapCount = amount;
    }
    public static void decrementTrapCount(int amount) {
        trapCount -= amount;
    }
    public static int getTrapCount() {
        return trapCount;
    }
}

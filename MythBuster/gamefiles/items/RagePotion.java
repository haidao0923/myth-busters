package gamefiles.items;

import controller.Controller;
import gamefiles.characters.Player;

public class RagePotion extends Consumable {

    // String name = "Rage Potion";
    // String description = "Doubles your attack output for 10 seconds.";
    // int quantity = 0;
    // long duration = 600;
    private boolean doubled;
    private double oldDamage;

    public RagePotion(int id, String name, String description, 
        int quantity, boolean active, long duration) {
        super(id, name, description, quantity, active, duration);
        updateImageView("sprites/itemAssets/ragePotion.png");
    }

    public void effect(long currentNanoTime) {
        use();


        if (!doubled) {
            Player player = Controller.getPlayer();
            oldDamage = player.getDamageStat();
            player.addDamageStat(oldDamage);
            doubled = true;
            //updateImageView("sprites/itemAssets/ragePotionConsumed.png");
            player.updateHotbarImages();
            System.out.println("First test");
        }

        boolean updated = update();

        if (!updated) {
            Player player = Controller.getPlayer();
            player.subtractDamageStat(oldDamage);
            doubled = false;
        }
    }
}

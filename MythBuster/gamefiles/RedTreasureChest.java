package gamefiles;

import java.util.ArrayList;

import controller.Controller;
import gamefiles.characters.Player;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.WeaponDatabase;

public class RedTreasureChest extends TreasureChest {
    public RedTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestRed.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        int random = (int) (Math.random() * 6);
        switch(random) {
        case 0:
        case 1:
        case 2:
            if (Controller.getPlayer().getCurrentHealth() > 100) {
                Controller.getPlayer().takeDamage(100);
            } else {
                Controller.getPlayer().setHealth(50);
            }
            break;
        case 3:
            Controller.getPlayer().addCoins((int) (Math.random() * 9 + 4) * 5);
            break;
        case 4:
            Controller.getPlayer().addMaximumHealth(100);
            break;
        case 5:
            int amountToAdd = (int)(Math.random() * 3 + 3);
            ArrayList<Item> itemsToAdd = new ArrayList<Item>();
            for (int i = 0; i < amountToAdd; i++) {
                addRandomPotion(itemsToAdd);
            }
            Controller.getPlayer().updateHotbar(null, itemsToAdd);
            break;
        }

        opened = true;
    }

    private void addRandomPotion(ArrayList<Item> itemsToAdd) {
        int random = (int) (Math.random() * 2);
        switch (random) {
        case 0:
            Item healthPotion = ItemDatabase.getItem(0);
            healthPotion.addQuantity(1);
            itemsToAdd.add(healthPotion);
            break;
        case 1:
            Item ragePotion = ItemDatabase.getItem(1);
            ragePotion.addQuantity(1);
            itemsToAdd.add(ragePotion);
            break;
        }
    }
}

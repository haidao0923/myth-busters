package gamefiles;

import java.util.ArrayList;

import controller.Controller;
import gamefiles.items.Item;
import gamefiles.items.ItemDatabase;
import gamefiles.weapons.WeaponDatabase;

public class BlueTreasureChest extends TreasureChest {
    public BlueTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestBlue.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);

        int random = (int) (Math.random() * 2);
        switch (random) {
            case 0:
                addPotion();
                break;
            case 1:
                Inventory.addToInventory(WeaponDatabase.getWeapon((int) (Math.random() * 3)));
                break;
        }

        opened = true;
    }

    private void addPotion() {
        int amountToAdd = (int)(Math.random() * 3 + 2);
        ArrayList<Item> itemsToAdd = new ArrayList<Item>();
        for (int i = 0; i < amountToAdd; i++) {
            addRandomPotion(itemsToAdd);
        }
        Controller.getPlayer().updateHotbar(null, itemsToAdd);
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

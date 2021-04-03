package gamefiles.items;

import java.util.HashMap;
import java.util.Map;

public class ItemDatabase {
    private static Map<Integer, Item> items = new HashMap<>();

    /**
     * Create an item database.
     */
    public static void initialize() {
        items.put(0, new HealthPotion(0, "Health Potion", "Restores 2 hearts.", 0, false, 0));
        items.put(1, new RagePotion(0, "Rage Potion", "Doubles your attack output for 10 seconds.", 0, false, 600));
        // weapons.put(0, new Spear(1, "Spear", 2, 2));
        // weapons.put(1, new Sword(0, "Sword", 1, 3));
        // weapons.put(2, new Bow(2, "Bow", 2, 1));
    }

    /**
     * Get an item with an id from the database.
     *
     * @param key the id of the item to get
     * @return the item from the passed in id
     */
    public static Item getItem(int key) {
        return items.get(key);
    }
}

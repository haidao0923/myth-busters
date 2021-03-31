package gamefiles.weapons;

import gamefiles.weapons.Bow;
import gamefiles.weapons.Spear;
import gamefiles.weapons.Sword;
import gamefiles.weapons.Weapon;

import java.util.HashMap;
import java.util.Map;

public class WeaponDatabase {
    private static Map<Integer, Weapon> weapons = new HashMap<>();

    /**
     * Create a weapon database.
     */
    public static void initialize() {
        weapons.put(0, new Spear(1, "Spear", 2, 2));
        weapons.put(1, new Sword(0, "Sword", 1, 3));
        weapons.put(2, new Bow(2, "Bow", 2, 1));
    }

    /**
     * Get a weapon with an id from the database.
     *
     * @param key the id of the item to get
     * @return the weapon from the passed in id
     */
    public static Weapon getWeapon(int key) {
        return weapons.get(key);
    }
}

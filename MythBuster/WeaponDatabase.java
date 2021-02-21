import java.util.HashMap;
import java.util.Map;

public class WeaponDatabase {
    private static Map<Integer, Weapon> weapons = new HashMap<>();

    /**
     * Create a weapon database.
     */
    public static void initialize() {
        weapons.put(0, new Weapon(0, "Sword", 1, 1));
        weapons.put(1, new Weapon(1, "Shield", 1, 1));
        weapons.put(2, new Weapon(2, "Gun", 1, 1));
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

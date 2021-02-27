package gamefiles;

public class Weapon {
    private int id;
    private String name;
    private double speed;
    private double damage;

    /**
     * Create new weapon object with initial name, speed, and damage.
     * @param id     the id of the weapon
     * @param name   the name of the weapon
     * @param speed  the speed of the weapon
     * @param damage the damage of the weapon
     */
    public Weapon(int id, String name, double speed, double damage) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.damage = damage;
    }

    /**
     * Get the name of the weapon.
     *
     * @return the name of the weapon
     */
    public String getName() {
        return name;
    }
}

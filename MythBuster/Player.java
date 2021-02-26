public class Player {
    public Player(int coins) {
        coins = 0;
    }

    private int coins;
    private Weapon weapon;


    public int getCoins() {
        return coins;
    }
    public void setCoins(int amount) {
        coins = coins + amount;
    }

    public void setWeapon(Weapon w) {
        weapon = w;
    }
    public Weapon getWeapon() {
        return weapon;
    }
}

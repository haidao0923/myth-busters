package gamefiles;

import controller.Controller;

public class RedTreasureChest extends TreasureChest {
    public RedTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestRed.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);
        Controller.getPlayer().addHealth(100);
        opened = true;
    }
}

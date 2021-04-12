package gamefiles;

import controller.Controller;

public class GreenTreasureChest extends TreasureChest {
    public GreenTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestGreen.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);
        Controller.getPlayer().addHealth(500);
        opened = true;
    }
}

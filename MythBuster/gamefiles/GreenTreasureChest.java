package gamefiles;

import controller.Controller;

public class GreenTreasureChest extends TreasureChest {
    public GreenTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestGreen.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);
        Controller.getPlayer().setHealth(Controller.getPlayer().getMaximumHealth());
        opened = true;
    }
}

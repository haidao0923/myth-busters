package gamefiles;

import controller.Controller;

public class YellowTreasureChest extends TreasureChest {
    public YellowTreasureChest(double positionX, double positionY, int cost) {
        super(positionX, positionY, cost, "sprites/ChestYellow.png");
    }

    @Override
    public void open() {
        Controller.getPlayer().subtractCoins(cost);
        Controller.getPlayer().addCoins((int) (Math.random() * 9 + 2) * 5);
        opened = true;
    }
}
